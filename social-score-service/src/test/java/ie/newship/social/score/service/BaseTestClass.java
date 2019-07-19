package ie.newship.social.score.service;

import ie.newship.social.score.core.SearchKey;
import ie.newship.social.score.core.SocialScore;
import ie.newship.social.score.core.SocialScoreReport;
import ie.newship.social.score.service.controller.SocialScoreController;
import ie.newship.social.score.service.service.SocialScoreService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Arrays;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseTestClass {

  @MockBean private SocialScoreService socialScoreService;

  @Autowired private SocialScoreController socialScoreController;

  @Before
  public void setup() {

    SocialScore google = new SocialScore();
    google.setUrl("http://www.google.com/search?query=Something");
    google.setScore(10l);
    google.setId("144423c1-dcd1-4c27-9d57-99386590a7da");

    SocialScore faceBook = new SocialScore();
    faceBook.setUrl("http://www.facebook.com/search?query=Something");
    faceBook.setScore(10l);
    faceBook.setId("144423c1-dcd1-4c27-9d57-99386590a7db");

    SocialScoreReport googleReport = new SocialScoreReport();
    googleReport.setDomain("www.google.com");
    googleReport.setScore(20l);
    googleReport.setUrlCount(2);

    SocialScoreReport facebookReport = new SocialScoreReport();
    facebookReport.setDomain("www.facebook.com");
    facebookReport.setScore(10l);
    facebookReport.setUrlCount(1);

    Mockito.when(socialScoreService.save(ArgumentMatchers.any(SocialScore.class)))
        .thenReturn(google);
    Mockito.when(
            socialScoreService.searchSocialScores(
                ArgumentMatchers.any(SearchKey.class), ArgumentMatchers.anyString()))
        .thenReturn(Arrays.asList(google));
    Mockito.when(socialScoreService.generateReport())
        .thenReturn(Arrays.asList(googleReport, facebookReport));
    Mockito.doNothing()
        .when(socialScoreService)
        .deleteSocialScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(SearchKey.class));
    Mockito.doNothing()
        .when(socialScoreService)
        .updateSocialScore(
            ArgumentMatchers.any(SearchKey.class),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyLong());

    StandaloneMockMvcBuilder standaloneMockMvcBuilder =
        MockMvcBuilders.standaloneSetup(socialScoreController);
    RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
  }
}
