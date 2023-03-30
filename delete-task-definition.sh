region=$(aws configure get region)

aws cloudformation delete-stack --region $region --stack-name ecs-ecr-repository-stack
aws cloudformation wait stack-delete-complete --region $region --stack-name ecs-ecr-repository-stack
aws cloudformation delete-stack --region $region --stack-name ecs-task-definition-stack
aws cloudformation wait stack-delete-complete --region $region --stack-name ecs-task-definition-stack

