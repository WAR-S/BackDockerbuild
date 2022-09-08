package com.example.demo.service;

import com.example.demo.client.DockerAPIClient;
import com.example.demo.domain.BuildEntity;
import com.example.demo.model.BuildRequest;
import com.example.demo.model.BuildResponse;
import com.example.demo.model.BuildStatus;
import com.example.demo.repository.BuildRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class DockerBuildServiceImpl implements DockerBuildService {
    @Autowired
    DockerAPIClient dockerAPIClient;
    @Autowired
    BuildRepository buildRepository;

    @Override
    public BuildResponse registerBuild(BuildRequest buildRequest) {

        try {
            writeToDisk(buildRequest.getName() + "__" + buildRequest.getTag() + ".dockerfile", buildRequest.getDockerManifest());
            buildRepository.save(new BuildEntity(null, buildRequest.getName(), buildRequest.getTag(), BuildStatus.REGISTER, Timestamp.valueOf(LocalDateTime.now())));
            return BuildResponse.builder()
                    .name(buildRequest.getName())
                    .tag(buildRequest.getTag())
                    .status(BuildStatus.REGISTER)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return BuildResponse.builder()
                    .name(buildRequest.getName())
                    .tag(buildRequest.getTag())
                    .status(BuildStatus.FAILED)
                    .build();
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Override
    public void buildJob() {
        log.info("build job started: find registered records to build");
        List<BuildEntity> started = buildRepository.findByStatus(BuildStatus.REGISTER);
        log.info("found {} registered builds", started.size());
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        started.forEach(item -> executorService.submit(() -> {
            try {
                String imageId = dockerAPIClient.build(item.getName(), item.getTag(), new File(item.getName() + "__" + item.getTag() + ".dockerfile"));
                item.setStatus(BuildStatus.SUCCESS);
                log.info("build successful {}:{}. ImageId: {}", item.getName(), item.getTag(), imageId);
            } catch (Exception e) {
                item.setStatus(BuildStatus.FAILED);
                log.error("build failed {}:{}. Message: {}", item.getName(), item.getTag(), e.getMessage());
            }
            buildRepository.save(item);
        }));
        executorService.shutdown();
        log.info("build job finished");
    }

    @Override
    public BuildResponse getStatus(String name, String tag) {
        BuildEntity buildEntity = buildRepository.findByNameAndTag(name,tag,Sort.by(Sort.Direction.DESC, "creationDate")).get(0);
        return BuildResponse.builder()
                .name(buildEntity.getName())
                .tag(buildEntity.getTag())
                .status(buildEntity.getStatus())
                .build();
    }

    @Override
    public byte[] getFile(String name, String tag) {
        try {
            return dockerAPIClient.saveAndGetImage(name, tag);
        } catch (IOException e) {
            log.error("Error while getting image file with name: {} and tag: {} : {}", name, tag, e.getMessage());
            return null;
        }
    }


    public void writeToDisk(String name, String dockerManifest) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            writer.write(dockerManifest);
        }
    }
}
