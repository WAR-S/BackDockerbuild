package com.example.demo.service;

import com.example.demo.model.BuildRequest;
import com.example.demo.model.BuildResponse;
import org.springframework.stereotype.Service;

@Service
public interface DockerBuildService {
    BuildResponse registerBuild(BuildRequest buildRequest);

    void buildJob();

    BuildResponse getStatus(String name, String tag);

    byte[] getFile(String name, String tag);
}
