package by.svyat.redisdemo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring")
@Data
public class CacheConfiguration {

    private List<CacheProp> caches;

    @Data
    public static class CacheProp {
        private String cacheName;
        private Duration ttl;
    }
}
