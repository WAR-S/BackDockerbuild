package com.example.demo.domain;

import com.example.demo.model.BuildStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "build",
        indexes = {
                @Index(columnList = "name,tag", name = "Index_build"),
                @Index(columnList = "status", name = "Index_status")
        })
@Builder
public class BuildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String tag;

    @Enumerated(EnumType.STRING)
    private BuildStatus status;
    private Timestamp creationDate;
}
