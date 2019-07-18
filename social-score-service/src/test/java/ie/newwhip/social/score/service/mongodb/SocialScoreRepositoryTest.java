package ie.newwhip.social.score.service.mongodb;

import ie.newwhip.social.socre.client.score.core.SocialScoreReport;
import ie.newwhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RunWith(SpringRunner.class)
@DataMongoTest
public class SocialScoreRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private SocialScoreRepository socialScoreRepository;



    @Before
    public void setUp(){
        SocialScoreDBO google = new SocialScoreDBO();
        google.setUrl("http://www.google.com/search?query=something");
        google.setDomainName("www.google.com");
        google.setScore(20);
        google.setId(UUID.randomUUID().toString());
        mongoTemplate.save(google,"socialscore");


        SocialScoreDBO google2 = new SocialScoreDBO();
        google2.setUrl("http://www.google.com/search?query=somethingElse");
        google2.setDomainName("www.google.com");
        google2.setScore(15);
        google2.setId(UUID.randomUUID().toString());
        mongoTemplate.save(google2,"socialscore");

        SocialScoreDBO fb = new SocialScoreDBO();
        fb.setUrl("http://www.fb.com/search?query=myfriends");
        fb.setDomainName("www.fb.com");
        fb.setScore(5);
        fb.setId(UUID.randomUUID().toString());
        mongoTemplate.save(fb,"socialscore");

        SocialScoreDBO ret = new SocialScoreDBO();
        ret.setUrl("http://www.rte.ie/news-at-9");
        ret.setDomainName("www.rte.ie");
        ret.setScore(10);
        ret.setId(UUID.randomUUID().toString());
        mongoTemplate.save(ret,"socialscore");
    }

    @Test
    public void test(){
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        Page<SocialScoreDBO>  socialScoreDBOS = socialScoreRepository.findByDomainName("www.google.com",firstPageWithTwoElements);
        List<SocialScoreDBO> socialScoreDBOS1 =socialScoreDBOS.getContent();
        Assertions.assertThat(socialScoreDBOS1).isNotEmpty();
    }

    @Test
    public void test2(){
        Optional<SocialScoreDBO>  socialScoreDBOS = socialScoreRepository.findByUrl("http://www.google.com/search?query=somethingElse");
        Assertions.assertThat(socialScoreDBOS).isPresent();
    }

    @Test
    public void test3(){

        List<SocialScoreReport>  socialScoreDBOS = socialScoreRepository.aggregateSocialScoreByDomainName();
        Assertions.assertThat(socialScoreDBOS).isNotEmpty();
    }

    @After
    public void dropCollection(){
        mongoTemplate.dropCollection("socialscore");
    }



}
