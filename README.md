# Chaos Monkey Pods
This project  has been created in order to test the robustness of a kubernetes cluster by deploying a java application that randomly kills pods inside a namespace in a given scheduled time (right now is set for 10 second) 

For this application has been used [fabric8io/kubernetes-client](https://github.com/fabric8io/kubernetes-client) in order to interact in very easy way with Kubernetes API

![_94ad819e-00c1-4265-bb0c-9398ef12f039](https://github.com/Petix992/ChaosMonkeyPods/assets/126627628/ecb4b0c2-9ed2-4cb2-9b2d-7f08d2d90692)

## How to

### Docker Image
To begin with, under the folder Docker there is a bash script that will build the the image and it'll push to your local registry

### Kubernetes Environment
Another script under the folder kubernetes will set up the cluster environment:

1. First it will create a namespace named "tetsing" if not present
2. Then it will deploy the ChaosMonkey application, that will kill pod randomly 
3. And in the end it will set up dummies workload inside the namespace, based on nginx image 

### Delete Random Pod With a Specfic Label

In order to delete pods with a specific label you can ativate this feature modifiyng **"ChaosMonkeyDeployment.yaml"**. 

Under the field **"env"** you'll find two environment variables **"DELETE_BY_LABEL"** and **"LABEL_TO_DELETE"**

If you want activate this feature you'll have to set these two variables in this way:

    env:
      - name: DELETE_BY_LABEL
        value: "true"
      - name: LABEL_TO_DELETE
        value: "target_label"

and re-dploy the application **kubectl apply -f ChaosMonkeyDeployment.yaml**. 
Alternatively you can set them at run time by using **kubectl edit deployment chaos-monkey**. 



