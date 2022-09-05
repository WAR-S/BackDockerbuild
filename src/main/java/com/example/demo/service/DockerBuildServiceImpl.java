package com.example.demo.service;

import com.example.demo.client.DockerAPIClient;
import com.example.demo.model.BuildRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Service
@Slf4j
public class DockerBuildServiceImpl implements DockerBuildService {
    @Autowired
    DockerAPIClient dockerAPIClient;

    @Override
    public byte[] build(BuildRequest buildRequest) {
        //write file
        //get file name
        byte[] data = null;


        try {
            writeToDisk(buildRequest.getName(),buildRequest.getDockerManifest());
            data = dockerAPIClient.build(buildRequest.getName(),buildRequest.getTag(),new File(buildRequest.getName()));
            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            return data;
        }
    }



    public void writeToDisk(String name,String dockerManifest) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));
        writer.write(dockerManifest);
        writer.close();
    }
}
