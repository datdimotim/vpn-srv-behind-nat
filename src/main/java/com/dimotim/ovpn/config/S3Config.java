package com.dimotim.ovpn.config;

import lombok.Value;

@Value
public class S3Config {
    String accessKey;
    String secretAccessKey;
    String endpoint;
    String region;
    String bucket;
    String fileKey;
}
