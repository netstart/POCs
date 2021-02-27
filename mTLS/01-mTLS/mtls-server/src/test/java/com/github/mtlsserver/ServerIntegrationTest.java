package com.github.mtlsserver;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("default")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {MtlsServerApplication.class})
public class ServerIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    private String serverUrl = "https://localhost:8222/client/";

    @Test
    public void testGet() {
        ResponseEntity<String> getResponse = restTemplate.getForEntity(serverUrl, String.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Client successfully called!", getResponse.getBody());
        System.out.println(getResponse.getBody());
    }
}