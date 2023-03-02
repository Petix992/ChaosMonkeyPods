#!/bin/bash

mvn clean package ../pom.xml
docker build -t ChaosMonkey:1.0 .
docker push  <local_registry>/ChaosMonkey:1.0
