package com.example.demo.service;

import com.example.demo.model.BuildRequest;
import com.example.demo.model.BuildResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface DockerBuildService {
    BuildResponse registerBuild(BuildRequest buildRequest);
    void buildJob();

    BuildResponse getStatus(String name, String tag);
}
