package com.myproject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.server.mock.EnableKubernetesMockClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesMockServer;
import io.fabric8.kubernetes.client.server.mock.KubernetesServer;


@EnableKubernetesMockClient
 class ChaosMonkeyPodTest {
    
    KubernetesMockServer server;
    private KubernetesClient client;

    @BeforeEach
    void setUp() {
      client = server.createClient().inNamespace("testing");
    }
     
    @Test    
    void testList()
    {
        PodList podList = client.pods().list();
        assertNotNull(podList);
        assertEquals(0, podList.getItems().size());
    
        podList = client.pods().inNamespace("ns1").list();
        assertNotNull(podList);
        assertEquals(2, podList.getItems().size());
    
        podList = client.pods().inAnyNamespace().list();
        assertNotNull(podList);
        assertEquals(3, podList.getItems().size());
    
    }

    @Test
    void testListWithLabels() {
        PodList podList = client.pods()
        .withLabel("key1", "value1")
        .withLabel("key2", "value2")
        .withLabel("key3", "value3")
        .list();
        assertNotNull(podList);
        assertEquals(0, podList.getItems().size());
    
        podList = client.pods()
            .withLabel("key1", "value1")
            .withLabel("key2", "value2")
            .list();
    
        assertNotNull(podList);
        assertEquals(3, podList.getItems().size());
    
    }

    @Test
    void testDeleteMulti() {
      Pod pod1 = new PodBuilder().withNewMetadata().withName("pod1").withNamespace("test").and().build();
      Pod pod2 = new PodBuilder().withNewMetadata().withName("pod2").withNamespace("ns1").and().build();
      Pod pod3 = new PodBuilder().withNewMetadata().withName("pod3").withNamespace("any").and().build();
  
      server.expect().withPath("/api/v1/namespaces/test/pods/pod1").andReturn(200, pod1).once();
      server.expect().withPath("/api/v1/namespaces/ns1/pods/pod2").andReturn(200, pod2).once();
  
      Boolean deleted = client.pods().inAnyNamespace().delete(pod1, pod2);
      assertTrue(deleted);
  
      deleted = client.pods().inAnyNamespace().delete(pod3).size() == 1;
      assertFalse(deleted);
    }
  
}