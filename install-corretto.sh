#!/bin/bash

REGION=$(aws configure get region)


aws ecr create-repository --region $REGION --repository-name amazoncorretto --image-tag-mutability IMMUTABLE
docker pull amazoncorretto:17
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin 361494667617.dkr.ecr.us-west-2.amazonaws.com/amazoncorretto
docker tag amazoncorretto:17 361494667617.dkr.ecr.us-west-2.amazonaws.com/amazoncorretto:17
docker push 361494667617.dkr.ecr.us-west-2.amazonaws.com/amazoncorretto:17
