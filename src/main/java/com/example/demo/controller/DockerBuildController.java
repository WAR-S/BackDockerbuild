package com.example.demo.controller;

import com.example.demo.model.BuildRequest;
import com.example.demo.service.DockerBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@CrossOrigin(originPatterns = "*")
@Consumes(value = "application/json")
public class DockerBuildController {

    private final DockerBuildService dockerBuildService;

    @Autowired
    public DockerBuildController(DockerBuildService dockerBuildService) {
        this.dockerBuildService = dockerBuildService;
    }


    @PostMapping(value = "/build",produces = APPLICATION_OCTET_STREAM_VALUE)
    public byte[] buildResponse(@RequestBody BuildRequest buildRequest) {
         return dockerBuildService.build(buildRequest);
    }
}
