package com.myproject;

import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Client;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChaosMonkeyPod {

    private Pod pickRandom(List<Pod> targets) {

        Random rand = new Random();
        
        Pod randomPod = targets.get(rand.nextInt(targets.size()));

        return randomPod;
    }
        
public void killPod() {
try (KubernetesClient client = new KubernetesClientBuilder().build()) {
    
  String namespace = "testing";

  PodList pods = client.pods().inNamespace(namespace).list();
    
  List<Pod> targets = new ArrayList<>();

    if (pods != null) {
        List<Pod> podList = pods.getItems();
          for (Pod pod : podList) {
              targets.add(pod);            
           }
           
           String message = null;
           Pod pod = null;
           
           if (targets.size() > 0) {
             pod = pickRandom(targets);
           }
         
        }
     }
   }
}
