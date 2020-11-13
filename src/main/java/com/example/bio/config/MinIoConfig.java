package com.example.bio.config;

import com.example.bio.common.domain.MinIoProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuqi
 * @date 2020/11/13
 */
@Configuration
public class MinIoConfig {
    @Autowired
    private MinIoProperties minIo;

    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .endpoint(minIo.getEndpoint())
                .credentials(minIo.getAccessKey(),
                        minIo.getSecretKey()).build();
    }
}
