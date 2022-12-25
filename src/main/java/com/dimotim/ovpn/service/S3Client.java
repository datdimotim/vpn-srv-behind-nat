package com.dimotim.ovpn.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.dimotim.ovpn.config.S3Config;
import com.dimotim.ovpn.util.FunctionE;
import com.dimotim.ovpn.util.GsonFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class S3Client {
    S3Config config;

    private AmazonS3 createClient(S3Config config) {
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        config.getEndpoint(),
                        config.getRegion()
                ))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        config.getAccessKey(),
                        config.getSecretAccessKey()
                )))
                .build();
    }

    private <T> T withClient(FunctionE<AmazonS3, T> action) throws Exception {
        AmazonS3 client = null;

        try {
            client = createClient(config);
            return action.apply(client);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }

    public <T> void writeJson(T json) throws Exception {
        String serialized =  GsonFactory.getGson().toJson(json);
        withClient(client -> client.putObject(config.getBucket(), config.getFileKey(), serialized));
    }

    public <T> T readJson(Class<T> type) throws Exception {
        byte[] content = withClient(client -> {
            try (S3Object object = client.getObject(config.getBucket(), config.getFileKey())) {
                return object.getObjectContent().readAllBytes();
            }
        });

        try (InputStream is = new ByteArrayInputStream(content); Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return GsonFactory.getGson().fromJson(r, type);
        }
    }
}
