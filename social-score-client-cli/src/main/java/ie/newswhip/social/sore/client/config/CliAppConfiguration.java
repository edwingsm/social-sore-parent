package ie.newswhip.social.sore.client.config;

import ie.newswhip.social.sore.client.cli.console.CommandReader;
import ie.newswhip.social.sore.client.cli.console.ScannerCommandReader;
import ie.newswhip.social.sore.client.cli.listener.DefaultCliExecutorListener;
import ie.newswhip.social.sore.client.cli.listener.CliExecutorListener;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CliAppConfiguration {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
            //TODO Move to properties
        .setConnectTimeout(Duration.ofMillis(3000))
        .setReadTimeout(Duration.ofMillis(3000))
        .build();
  }

  @Bean
  public CommandReader scanner() {
    return new ScannerCommandReader();
  }

  @Bean
  public CliExecutorListener executorListener(){
    return new DefaultCliExecutorListener();
  }

}
