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
        
public void killPods() {
try (KubernetesClient client = new KubernetesClientBuilder().build()) {
    
  Logger logger = LoggerFactory.getLogger(ChaosMonkeyPod.class); 
  
  String killenv = System.getenv("KILL_BYLABEL");
  String namespace = "testing";

  PodList pods = client.pods().inNamespace(namespace).list();
  PodList labelpods = client.pods().inNamespace(namespace).withLabel("key").list();
  
  List<Pod> targets = new ArrayList<>();

  Pod randomPod = null;

     if (pods != null && killenv.equals("true")){
      List<Pod> podList = labelpods.getItems();
      for (Pod pod : podList) {
           targets.add(pod);            
         }
          if (targets.size() > 0) {
             randomPod = pickRandom(targets);
              String podName = randomPod.getMetadata().getName();
              client.pods().inNamespace(namespace).withName(podName).delete();               
              logger.info("Chaos Monkey deleted labeled pod: " + podName);             
         }
    } else {
      List<Pod> podList = pods.getItems();
      for (Pod pod : podList) {
            targets.add(pod);            
       }                                 
          if (targets.size() > 0) {
            randomPod = pickRandom(targets);
            String podName = randomPod.getMetadata().getName();
            client.pods().inNamespace(namespace).withName(podName).delete();               
            logger.info("Chaos Monkey deleted random pod: "+ podName);                
         }                          
        }
      }
    }
}
