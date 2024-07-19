package com.Kuba2412.git_repoo.fallback;

import com.Kuba2412.git_repoo.client.GitHubClient;
import com.Kuba2412.git_repoo.repository.GitHubRepo;
import org.springframework.stereotype.Component;

public class GitHubClientFallBack {

    @Component
    public class GitHubClientFallback implements GitHubClient {
        @Override
        public GitHubRepo getRepository(String owner, String repo) {
            GitHubRepo fallbackRepo = new GitHubRepo();
            fallbackRepo.setFullName("Fallback Repo");
            fallbackRepo.setDescription("Default description");
            return fallbackRepo;
        }
    }
}