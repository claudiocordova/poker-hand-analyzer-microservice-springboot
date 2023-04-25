#!/bin/bash


if [ -z "$1" ]; then
      MODE=ECS_FARGATE
elif [ "$1" == "ECS_EC2" ]; then
      MODE=$1
elif [ "$1" == "ECS_FARGATE" ]; then
      MODE=$1
else
    echo "Wrong parameter 1 MODE: "$1
    exit 1 
fi


docker --version
docker-compose --version
#brew install docker-machine docker
docker-machine --version


accounId=$(aws sts get-caller-identity --query Account --output text)
REGION=$(aws configure get region)
taskDefinitionArn="arn:aws:ecs:$REGION:$accounId:task-definition/poker-analyzer-service-task-definition:1"
containerName="poker-analyzer-service-container"

./generate_appspec_yaml.sh $taskDefinitionArn $containerName
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin 361494667617.dkr.ecr.us-west-2.amazonaws.com/amazoncorretto
cp ecs-buildspec.yaml buildspec.yaml

mvn clean package

rm appspec.yaml
rm buildspec.yaml


aws cloudformation delete-stack --region $REGION --stack-name ecs-ecr-repository-stack
aws cloudformation wait stack-delete-complete --region $REGION --stack-name ecs-ecr-repository-stack
aws cloudformation create-stack --region $REGION --stack-name ecs-ecr-repository-stack --template-body file://./ecr-repository.yaml  --parameters ParameterKey=RepositoryName,ParameterValue=poker-analyzer-service-repository --capabilities CAPABILITY_NAMED_IAM
result=$?

if [ $result -eq 254 ] || [ $result -eq 255 ]; then
  echo "ecs-ecr-repository-stack already exists"
  #exit 0
elif [ $result -ne 0 ]; then
  echo "ecs-ecr-repository-stack failed to create " $result
  exit 1
fi


aws cloudformation wait stack-create-complete --region $REGION --stack-name ecs-ecr-repository-stack
repositoryUri=$(aws cloudformation describe-stacks --region $REGION --stack-name ecs-ecr-repository-stack | jq -r '.Stacks[0].Outputs[0].OutputValue')


# Delete ECR Repository
#aws ecr delete-repository --force --repository-name poker-analyzer-service --region $region
# Create ECR Repository
#repositoryUri=$(aws ecr create-repository --repository-name poker-analyzer-service --region $region | jq -r '.repository.repositoryUri')


aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $repositoryUri
docker tag claudiocordova/poker-hand-analyzer-microservice:0.0.1-SNAPSHOT $repositoryUri:0.0.1-SNAPSHOT
docker push $repositoryUri:0.0.1-SNAPSHOT 


aws cloudformation delete-stack --region $REGION --stack-name ecs-task-definition-stack
aws cloudformation wait stack-delete-complete --region $REGION --stack-name ecs-task-definition-stack
if [ "$MODE" == "ECS_FARGATE" ]; then
  aws cloudformation create-stack --region $REGION --stack-name ecs-task-definition-stack --template-body file://./ecs-fargate-task-definition.yaml --parameters ParameterKey=Image,ParameterValue=$repositoryUri:0.0.1-SNAPSHOT   --capabilities CAPABILITY_NAMED_IAM
elif [ "$MODE" == "ECS_EC2" ]; then
  aws cloudformation create-stack --region $REGION --stack-name ecs-task-definition-stack --template-body file://./ecs-ec2-task-definition.yaml --parameters ParameterKey=Image,ParameterValue=$repositoryUri:0.0.1-SNAPSHOT   --capabilities CAPABILITY_NAMED_IAM
fi

result=$?

if [ $result -eq 254 ] || [ $result -eq 255 ]; then
  echo "ecs-task-definition-stack already exists"
  #exit 0
elif [ $result -ne 0 ]; then
  echo "ecs-task-definition-stack failed to create " $result
  exit 1
fi


aws cloudformation wait stack-create-complete --region $REGION --stack-name ecs-task-definition-stack
