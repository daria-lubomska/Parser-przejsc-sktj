package com.sktj;

import com.sktj.service.CaveAchievementParser;
import com.sktj.service.ClimbingAchievementParser;
import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner, WebMvcConfigurer {

  private final CaveAchievementParser caveParser;
  private final ClimbingAchievementParser climbingParser;

  @Autowired
  public ParserApplication(CaveAchievementParser caveParser,
      ClimbingAchievementParser climbingParser) {
    this.caveParser = caveParser;
    this.climbingParser = climbingParser;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new SpecificationArgumentResolver());
    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
  }


  public static void main(String[] args) {
    SpringApplication.run(ParserApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    caveParser.saveDataToDB();
    climbingParser.saveDataToDB();
  }
}
