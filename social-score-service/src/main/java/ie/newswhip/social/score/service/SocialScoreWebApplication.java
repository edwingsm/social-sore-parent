package ie.newswhip.social.score.service;

import ie.newswhip.social.score.service.mongodb.SocialScoreRepository;
import ie.newswhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SocialScoreWebApplication implements CommandLineRunner {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(SocialScoreWebApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {

        System.out.println("App ready to serve core");

    }
}
