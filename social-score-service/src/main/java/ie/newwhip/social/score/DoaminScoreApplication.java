package ie.newwhip.social.score;

import ie.newwhip.social.score.mongodb.SocialScoreRepository;
import ie.newwhip.social.score.mongodb.dbo.SocialScoreDBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.URL;

@SpringBootApplication
@EnableMongoRepositories
public class DoaminScoreApplication implements CommandLineRunner {



    public static void main(String[] args) throws Exception{
      //  File file = ResourceUtils.getFile("classpath:config/sample.txt");
      //  String content = new String(Files.readAllBytes(file.toPath()));
//        final EmbeddedElastic embeddedElastic = EmbeddedElastic.builder()
//                .withElasticVersion("5.0.0")
//                .withSetting(PopularProperties.TRANSPORT_TCP_PORT, 9350)
//                .withSetting(PopularProperties.CLUSTER_NAME, "app_cluster")
//               // .withIndex("social_score", IndexSettings.builder().withType("socialscore", content).build())
//                .build()
//                .start();
//        embeddedElastic.start();
        SpringApplication.run(DoaminScoreApplication.class, args);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SocialScoreRepository socialScoreRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("App ready to serve client");
//        while (true) {
//            Runnable task2 = () -> {
//                System.out.println("Task #2 is running");
//            };
//            // start the thread
//            new Thread(task2).start();
//        }
//		while(true){
//			Scanner scanner = new Scanner(System.in);
//			System.out.print("Enter Command: ");
//			String command = scanner.next();
//			System.out.println("Command "+command);
//			SocialScoreDBO domainScore =domainService.save(command);
//			System.out.println(domainScore);
//			//https://picocli.info/man/3.x/quick-guide.html
//		}

        String add = "ADD https://www.google.com/search?query=soemthing 30";

        String[] params = add.split(" ");


        switch (params[0]){
            case "ADD":
                System.out.println("ADD");
                URL myUrl = new URL(params[1]);
                System.out.println(myUrl.getHost());
                System.out.println(myUrl.toString());
                System.out.println(Integer.parseInt(params[2]));
            case "REMOVE":
                System.out.println("REMOVE");
            case "EXPORT":
                System.out.println("EXPORT");
        }

        SocialScoreDBO google = new SocialScoreDBO();
        google.setUrl("http://www.google.com/search?query=something");
        google.setDomainName("www.google.com");
        google.setScore(20);
        //google.setId(UUID.randomUUID().toString());
        mongoTemplate.save(google,"socialscore");


        SocialScoreDBO google2 = new SocialScoreDBO();
        google2.setUrl("http://www.google.com/search?query=somethingElse");
        google2.setDomainName("www.google.com");
        google2.setScore(15);
      //  google2.setId(UUID.randomUUID().toString());
        mongoTemplate.save(google2,"socialscore");

        SocialScoreDBO fb = new SocialScoreDBO();
        fb.setUrl("http://www.fb.com/search?query=myfriends");
        fb.setDomainName("www.fb.com");
        fb.setScore(5);
    //    fb.setId(UUID.randomUUID().toString());
        mongoTemplate.save(fb,"socialscore");

        SocialScoreDBO ret = new SocialScoreDBO();
        ret.setUrl("http://www.rte.ie/news-at-9");
        ret.setDomainName("www.rte.ie");
        ret.setScore(10);
       // ret.setId(UUID.randomUUID().toString());
        mongoTemplate.save(ret,"socialscore");

        Page<SocialScoreDBO> socialScoreDBOPage =socialScoreRepository.findByDomainName("www.google.com", Pageable
            .unpaged());
        socialScoreDBOPage.forEach(s->System.out.println(s.toString()));
    }
}
