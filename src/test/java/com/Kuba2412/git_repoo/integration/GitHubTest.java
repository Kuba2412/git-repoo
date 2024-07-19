package com.Kuba2412.git_repoo.integration;

import com.Kuba2412.git_repoo.client.GitHubClient;
import com.Kuba2412.git_repoo.repository.GitHubRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import feign.RetryableException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GitHubTest {
    private static WireMockServer wireMockServer;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081);
    }

    @AfterAll
    public static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetRepositoryDetails_Success() throws Exception {
        // Given
        GitHubRepo repo = new GitHubRepo();
        repo.setFullName("Kuba2412/MedicalClinic");
        repo.setDescription("Description");
        repo.setCloneUrl("https://github.com/Kuba2412/MedicalClinic.git");
        repo.setStars(10);
        repo.setCreatedAt(LocalDateTime.now());

        wireMockServer.stubFor(get(urlPathEqualTo("/repos/Kuba2412/MedicalClinic"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repo))));

        // When
        GitHubRepo responseRepo = gitHubClient.getRepository("Kuba2412", "MedicalClinic");

        // Then
        assertEquals(repo.getFullName(), responseRepo.getFullName());
        assertEquals(repo.getDescription(), responseRepo.getDescription());
        assertEquals(repo.getCloneUrl(), responseRepo.getCloneUrl());
        assertEquals(repo.getStars(), responseRepo.getStars());
        assertEquals(repo.getCreatedAt(), responseRepo.getCreatedAt());
    }

    @Test
    public void testGetRepositoryDetails_ServiceUnavailable() throws Exception {
        // Given
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/Kuba2412/AnyRepo"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Service Unavailable\"}")));

        // When & Then
        RetryableException thrown = assertThrows(RetryableException.class, () -> {
            gitHubClient.getRepository("Kuba2412", "AnyRepo");
        });
        assertEquals(503, thrown.getMessage());
    }
}