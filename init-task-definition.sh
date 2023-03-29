


accounId=$(aws sts get-caller-identity --query Account --output text)
region=$(aws configure get region)
taskDefinitionArn="arn:aws:ecs:$region:$accounId:task-definition/poker-analyzer-service-task-definition:1"
containerName="poker-analyzer-service-container"

./generate_appspec_yaml.sh $taskDefinitionArn $containerName
aws ecr get-login-password --region $region | docker login --username AWS --password-stdin 361494667617.dkr.ecr.us-east-1.amazonaws.com/amazoncorretto:8  
mvn clean package



aws cloudformation delete-stack --region $region --stack-name poker-analyzer-service-repository-stack
aws cloudformation wait stack-delete-complete --region $region --stack-name poker-analyzer-service-repository-stack
aws cloudformation create-stack --region $region --stack-name poker-analyzer-service-repository-stack --template-body file://./ecs-ecr-repository.yaml  --parameters ParameterKey=RepositoryName,ParameterValue=poker-analyzer-service-repository --capabilities CAPABILITY_NAMED_IAM
aws cloudformation wait stack-create-complete --region $region --stack-name poker-analyzer-service-repository-stack
repositoryUri=$(aws cloudformation describe-stacks --region $region --stack-name poker-analyzer-service-repository-stack | jq -r '.Stacks[0].Outputs[0].OutputValue')


# Delete ECR Repository
#aws ecr delete-repository --force --repository-name poker-analyzer-service --region $region
# Create ECR Repository
#repositoryUri=$(aws ecr create-repository --repository-name poker-analyzer-service --region $region | jq -r '.repository.repositoryUri')


aws ecr get-login-password --region $region | docker login --username AWS --password-stdin $repositoryUri
docker tag claudiocordova/poker-hand-analyzer-microservice:0.0.1-SNAPSHOT $repositoryUri:0.0.1-SNAPSHOT
docker push $repositoryUri:0.0.1-SNAPSHOT 


aws cloudformation delete-stack --region $region --stack-name ecs-task-definition-stack
aws cloudformation wait stack-delete-complete --region $region --stack-name ecs-task-definition-stack
aws cloudformation create-stack --region $region --stack-name ecs-task-definition-stack --template-body file://./ecs-task-definition.yaml --parameters ParameterKey=Image,ParameterValue=$repositoryUri:0.0.1-SNAPSHOT   --capabilities CAPABILITY_NAMED_IAM
aws cloudformation wait stack-create-complete --region $region --stack-name ecs-task-definition-stack
