#!/bin/bash


if [ -z "$1" ]; then
      MODE=EKS_FARGATE
elif [ "$1" == "EKS_EC2" ]; then
      MODE=$1
elif [ "$1" == "EKS_FARGATE" ]; then
      MODE=$1
else
    echo "Wrong parameter 1 MODE: "$1
    exit 1 
fi




accounId=$(aws sts get-caller-identity --query Account --output text)
REGION=$(aws configure get region)
containerName="poker-analyzer-service-container"


aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin 361494667617.dkr.ecr.us-west-2.amazonaws.com/amazoncorretto


mvn clean package




aws cloudformation delete-stack --region $REGION --stack-name eks-ecr-repository-stack
aws cloudformation wait stack-delete-complete --region $REGION --stack-name eks-ecr-repository-stack
aws cloudformation create-stack --region $REGION --stack-name eks-ecr-repository-stack --template-body file://./ecr-repository.yaml  --parameters ParameterKey=RepositoryName,ParameterValue=poker-analyzer-service-repository --capabilities CAPABILITY_NAMED_IAM
result=$?

if [ $result -eq 254 ] || [ $result -eq 255 ]; then
  echo "eks-ecr-repository-stack already exists"
elif [ $result -ne 0 ]; then
  echo "eks-ecr-repository-stack failed to create " $result
  exit 1
fi


aws cloudformation wait stack-create-complete --region $REGION --stack-name eks-ecr-repository-stack
repositoryUri=$(aws cloudformation describe-stacks --region $REGION --stack-name eks-ecr-repository-stack | jq -r '.Stacks[0].Outputs[0].OutputValue')


echo $repositoryUri


aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $repositoryUri
docker tag claudiocordova/poker-hand-analyzer-microservice:0.0.1-SNAPSHOT $repositoryUri:0.0.1-SNAPSHOT

echo $repositoryUri:0.0.1-SNAPSHOT

docker push $repositoryUri:0.0.1-SNAPSHOT 



