#!/usr/bin/env bash

TASK_DEFINITION_ARN=$1
CONTAINER_NAME=$2

printf 'version: 1.0\n' > appspec.yaml
printf 'Resources:\n' >> appspec.yaml
printf '  - TargetService:\n' >> appspec.yaml
printf '      Type: AWS::ECS::Service\n' >> appspec.yaml
printf '      Properties:\n' >> appspec.yaml
printf '        TastDefinition: "%s"\n' $1 >> appspec.yaml
printf '        LoadBalancerInfo:\n'  >> appspec.yaml
printf '          ContainerName: "%s"\n' $2 >> appspec.yaml
printf '          ContainerPort: "8080"\n'>> appspec.yaml
printf '        PlatformVersion: "LATEST"\n'>> appspec.yaml



