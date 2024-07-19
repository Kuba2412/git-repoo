package com.Kuba2412.git_repoo.client;


import com.Kuba2412.git_repoo.fallback.GitHubClientFallBack;
import com.Kuba2412.git_repoo.repository.GitHubRepo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", fallback = GitHubClientFallBack.class)
public interface GitHubClient {

    @GetMapping("/repos/{owner}/{repo}")
    GitHubRepo getRepository(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}