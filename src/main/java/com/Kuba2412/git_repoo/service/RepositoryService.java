package com.Kuba2412.git_repoo.service;


import com.Kuba2412.git_repo1.client.GitHubClient;
import com.Kuba2412.git_repo1.model.RepositoryDetails;
import com.Kuba2412.git_repo1.repository.GitHubRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryService {

    private GitHubClient gitHubClient;

    public RepositoryDetails getRepositoryDetails(String owner, String repo) {
        GitHubRepo githubRepository = gitHubClient.getRepository(owner, repo);

        RepositoryDetails details = new RepositoryDetails();
        details.setFullName(githubRepository.getFullName());
        details.setDescription(githubRepository.getDescription());
        details.setCloneUrl(githubRepository.getCloneUrl());
        details.setStars(githubRepository.getStars());
        details.setCreatedAt(githubRepository.getCreatedAt().toString());

        return details;
    }
}