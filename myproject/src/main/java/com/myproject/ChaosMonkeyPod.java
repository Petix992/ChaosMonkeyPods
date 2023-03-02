package com.myproject;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChaosMonkeyPod {

  String namespace = "testing";


  private Pod pickRandom(List<Pod> targetList) {

        Random rand = new Random();
        
        Pod randomPod = targetList.get(rand.nextInt(targetList.size()));

        return randomPod;
    }
        
private List<Pod> getPods() {
  try (KubernetesClient client = new KubernetesClientBuilder().build()) {
  
  
  String killenv = System.getenv("DELETE_BY_LABEL");
  String targetLabel = System.getenv("LABEL_TO_DELETE");

  PodList pods = client.pods().inNamespace(namespace).list();
  PodList labelPods = client.pods().inNamespace(namespace).withLabel(targetLabel).list();
  
  List<Pod> targets = new ArrayList<>();


  if (pods != null && killenv.equals("true")){
    List<Pod> podList = labelPods.getItems();
      for (Pod pod : podList) {
        targets.add(pod);           
           return targets;
        }
      }
  else {
    List<Pod> podList = pods.getItems();
      for (Pod pod : podList) {
        targets.add(pod);
          return targets;            
        }
       }
    }
  return null; 
}

public void killPod() {
 try (KubernetesClient client = new KubernetesClientBuilder().build()) {

  Logger logger = LoggerFactory.getLogger(ChaosMonkeyPod.class);
  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); 
  Pod randomPod = null;

       if (getPods().size() > 0) {
             randomPod = pickRandom(getPods());
              String podName = randomPod.getMetadata().getName();
              try{
              client.pods().inNamespace(namespace).withName(podName).delete();               
              logger.info(timeStamp + ": Chaos Monkey deleted pod: " + podName);
            }catch (Exception e) {
              logger.error(timeStamp + ": Chaos Monkey deleted pod: " + podName + " due to: " + e);
            }            
        }
        else {
          logger.info(timeStamp + ": No pod to delete");
        }
     } 
  }    
}
