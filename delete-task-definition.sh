region=$(aws configure get region)

aws cloudformation delete-stack --region $region --stack-name poker-analyzer-service-repository-stack
aws cloudformation wait stack-delete-complete --region $region --stack-name poker-analyzer-service-repository-stack
aws cloudformation delete-stack --region $region --stack-name ecs-task-definition-stack
aws cloudformation wait stack-delete-complete --region $region --stack-name ecs-task-definition-stack

