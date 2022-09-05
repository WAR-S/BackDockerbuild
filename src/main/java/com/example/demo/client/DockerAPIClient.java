package com.example.demo.client;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DockerAPIClient {
    private final DockerClient dockerClient = DockerClientBuilder.getInstance("unix:///var/run/docker.sock").build();

    public String build(String name, String tag, File dockerManifestFile) {
        String id = dockerClient.buildImageCmd().withBaseDirectory(new File(".")).withDockerfile(dockerManifestFile)
                .withTag(name + ":" + tag)
                .exec(new BuildImageResultCallback())
                .awaitImageId();
        dockerClient.tagImageCmd(id, name, tag).exec();
        return id;
//        return dockerClient.saveImageCmd(name+":"+tag).exec().readAllBytes();
    }
}
