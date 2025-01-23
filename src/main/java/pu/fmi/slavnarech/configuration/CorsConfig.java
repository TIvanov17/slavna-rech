package pu.fmi.slavnarech.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  @Value("${web.cors.allowed-origins}")
  private List<String> allowedOrigins;

  @Value("${web.cors.allowed-methods}")
  private List<String> allowedMethods;

  @Value("${web.cors.allowed-headers}")
  private List<String> allowedHeaders;

  @Value("${web.cors.max-age}")
  private Long maxAge;

  @Value("${web.cors.exposed-headers}")
  private List<String> exposedHeaders;

  @Value("${web.cors.allow-credentials}")
  private boolean shouldAllowCredentials;

  @Bean
  @Primary
  public CorsConfigurationSource apiConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(allowedOrigins);
    corsConfiguration.setAllowedMethods(allowedMethods);
    corsConfiguration.setAllowedHeaders(allowedHeaders);
    corsConfiguration.setMaxAge(maxAge);
    corsConfiguration.setExposedHeaders(exposedHeaders);
    corsConfiguration.setAllowCredentials(shouldAllowCredentials);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }
}
