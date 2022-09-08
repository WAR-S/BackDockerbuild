package com.example.demo.controller;

import com.example.demo.model.BuildRequest;
import com.example.demo.model.BuildResponse;
import com.example.demo.service.DockerBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping(value = "/build", produces = MediaType.APPLICATION_JSON_VALUE)
    public BuildResponse build(@RequestBody BuildRequest buildRequest) {
        return dockerBuildService.registerBuild(buildRequest);
    }

    @GetMapping(value = "/status/{name}/{tag}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BuildResponse status(@PathVariable String name, @PathVariable String tag) {
        return dockerBuildService.getStatus(name, tag);
    }

    @GetMapping(value = "/get/{name}/{tag}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile(@PathVariable String name, @PathVariable String tag) {
        return dockerBuildService.getFile(name, tag);
    }
}
