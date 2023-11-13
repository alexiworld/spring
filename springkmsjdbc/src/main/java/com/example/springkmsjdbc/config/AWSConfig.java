package com.example.springkmsjdbc.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Bean
    public AWSKMS createAWSKMSBean(){
        //TODO: update the region if different
        AWSKMS awskms = AWSKMSClientBuilder.standard().withCredentials(localCredentialProver()).withRegion(Regions.US_WEST_2).build();
        return awskms;
    }

    private AWSCredentialsProvider localCredentialProver() {
        String profile = System.getProperty("AWS_PROFILE");
        return new ProfileCredentialsProvider(profile);
    }
}