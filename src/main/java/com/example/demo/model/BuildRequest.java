package com.example.demo.model;

import lombok.Data;

@Data
public class BuildRequest {
    String name;
    String tag;
    String dockerManifest;
}
