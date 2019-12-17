package com.sktj.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("properties")
public class AppProperties {
  private String superAdminEmail = "sktjhero@gamail.com";

}
