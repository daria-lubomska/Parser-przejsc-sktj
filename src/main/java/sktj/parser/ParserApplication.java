package sktj.parser;

import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sktj.parser.service.parser.CaveAchievementsProcessor;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner, WebMvcConfigurer {

  private final CaveAchievementsProcessor caveAchievementsProcessor;

  @Autowired
  public ParserApplication(CaveAchievementsProcessor caveAchievementsProcessor) {
    this.caveAchievementsProcessor = caveAchievementsProcessor;
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
    caveAchievementsProcessor.saveDataToDB();
  }
}
