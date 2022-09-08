package com.example.demo.repository;

import com.example.demo.domain.BuildEntity;
import com.example.demo.model.BuildStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildRepository extends JpaRepository<BuildEntity, Long> {
    List<BuildEntity> findByStatus(BuildStatus status);

    List<BuildEntity> findByNameAndTag(String name,String tag, Sort creationDate);
}
