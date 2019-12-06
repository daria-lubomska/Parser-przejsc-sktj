package sktj.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sktj.parser.service.CaveAchievementsProcessor;
import sktj.parser.service.DataProcessor;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

  private final CaveAchievementsProcessor caveAchievementsProcessor;
  private final DataProcessor dataProcessor;

  @Autowired
  public ParserApplication(CaveAchievementsProcessor caveAchievementsProcessor,
      DataProcessor dataProcessor) {
    this.caveAchievementsProcessor = caveAchievementsProcessor;
    this.dataProcessor = dataProcessor;
  }

  public static void main(String[] args) {
    SpringApplication.run(ParserApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    dataProcessor.saveDataToDB();
    caveAchievementsProcessor.saveDataToDB();
  }
}
