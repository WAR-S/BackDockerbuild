package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildResponse {
    private String name;
    private String tag;
    private BuildStatus status;
}
