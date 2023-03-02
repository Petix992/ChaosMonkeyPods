#!/bin/bash

if kubectl get namespace | grep -q "testing"; then  kubectl apply -f Namespace.yaml
for f in *Deployment.yaml; do kubectl apply -f; done