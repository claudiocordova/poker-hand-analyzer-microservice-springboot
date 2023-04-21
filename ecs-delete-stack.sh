#!/bin/bash

region=$(aws configure get region)

aws cloudformation delete-stack --region $region --stack-name ecs-ecr-repository-stack

if [ $? -ne 0 ]; then
  echo "Failed to delete ecs-ecr-repository-stack"
  exit 1
fi

aws cloudformation wait stack-delete-complete --region $region --stack-name ecs-ecr-repository-stack
aws cloudformation delete-stack --region $region --stack-name ecs-task-definition-stack

if [ $? -ne 0 ]; then
  echo "Failed to delete ecs-task-definition-stack"
  exit 1
fi


aws cloudformation wait stack-delete-complete --region $region --stack-name ecs-task-definition-stack

