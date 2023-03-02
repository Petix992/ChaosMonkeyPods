package com.myproject;

import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myproject.ChaosMonkeyPod;

public class App 
{
    public static void main( String[] args ){
        
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {


            client.pods().inNamespace("testing").delete();
            
        }
    }
}
