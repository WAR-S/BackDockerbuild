package com.example.demo.service;

import com.example.demo.model.BuildRequest;
import org.springframework.stereotype.Service;

@Service
public interface DockerBuildService {
    byte[] build(BuildRequest buildRequest);
}
