package com.Kuba2412.git_repoo.retryer;

import com.Kuba2412.git_repoo.decoder.CustomDecoder;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GitHubClientConfiguration {

    @Bean
    public Retryer retryer(){
        return new Retryer.Default(1000, 1000, 5);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomDecoder();
    }
}