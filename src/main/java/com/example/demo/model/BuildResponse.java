package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BuildResponse {
    private String name;
    private String tag;
    private BuildStatus status;
}
