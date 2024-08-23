package com.data2.defense.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
public class IpConfiguration {
    @Value("${ip}")
    private String ip;
}
