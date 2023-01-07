package com.dimotim.ovpn.config;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class S3Config {
    String accessKey;
    String secretAccessKey;
    String endpoint;
    String region;
    String bucket;
    String fileKey;
}
