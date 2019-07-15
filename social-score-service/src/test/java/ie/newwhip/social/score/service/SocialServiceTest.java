package ie.newwhip.social.score.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import ie.newwhip.social.score.exception.SocialScoreException;
import ie.newwhip.social.score.model.KeyName;
import ie.newwhip.social.score.model.SocialScore;
import ie.newwhip.social.score.model.SocialScoreReport;
import ie.newwhip.social.score.mongodb.SocialScoreRepository;
import ie.newwhip.social.score.mongodb.dbo.SocialScoreDBO;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext(
    classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Create new beans for each test
public class SocialServiceTest {


  @Mock
  Page<SocialScoreDBO> mockPage;
  @Mock
  SocialScoreDBO mockDBO;
  @Mock
  SocialScoreReport mockScoreReport;
  @Autowired
  SocialScoreService socialScoreService;
  @MockBean
  private SocialScoreRepository socialScoreRepository;
  @MockBean
  private MongoTemplate mongoTemplate;

  @Before
  public void genericSetUp() {
    //Mock DBO
    when(mockDBO.getUrl()).thenReturn("http://www.rte.ie/news-at-9");
    when(mockDBO.getId()).thenReturn("144423c1-dcd1-4c27-9d57-99386590a7da");
    when(mockDBO.getScore()).thenReturn(10l);
    when(mockDBO.getDomainName()).thenReturn("www.rte.ie");

    //Mock Report
    when(mockScoreReport.getScore()).thenReturn(10l);
    when(mockScoreReport.getDomain()).thenReturn("www.rte.ie");
    when(mockScoreReport.getUrlCount()).thenReturn(1);

    //Mock Page
    when(mockPage.getContent()).thenReturn(Arrays.asList(mockDBO));

    //Mock Repo
    when(socialScoreRepository.save(any(SocialScoreDBO.class))).thenReturn(
        mockDBO);
    when(socialScoreRepository.findByUrl(anyString()))
        .thenReturn(Optional.of(
            mockDBO));
    when(socialScoreRepository.findById(anyString()))
        .thenReturn(Optional.of(
            mockDBO));
    when(
        socialScoreRepository.findByDomainName(anyString(), any(
            Pageable.class))).thenReturn(mockPage);
    Mockito.doNothing().when(socialScoreRepository).deleteById(anyString());
    //Custom Repo
    when(socialScoreRepository.aggregateSocialScoreByDomainName())
        .thenReturn(Arrays.asList(mockScoreReport));
    when(socialScoreRepository
        .updateSocialScore(anyString(), anyString(),
            anyLong())).thenReturn(
        mockDBO);
  }

  @Test
  public void testDeleteUsingId() {
    assertThatCode(() ->
        socialScoreService.deleteSocialScore("144423c1-dcd1-4c27-9d57-99386590a7da", KeyName.ID)
    ).doesNotThrowAnyException();

  }

  @Test
  public void testDeleteUsingUrl() {
    assertThatCode(() ->
        socialScoreService.deleteSocialScore("http://www.rte.ie/news-at-9", KeyName.URL)
    ).doesNotThrowAnyException();
  }

  @Test
  public void testDeleteUsingIdWillFailForNonExistingId() {
    doThrow(new RuntimeException("Some Exception in deep down layers"))
        .when(socialScoreRepository).deleteById(anyString());
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.deleteSocialScore("144423c1-dcd1-4c27-9d57-99386590a7da", KeyName.ID)
        ).withMessage("Some Exception in deep down layers");

  }

  @Test
  public void testDeleteUsingUrlWillFailForNonExistingId() {

    doThrow(new RuntimeException("Some Exception in deep down layers"))
        .when(socialScoreRepository).findByUrl(anyString());
    //
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.deleteSocialScore("http://www.rte.ie/news-at-9", KeyName.URL)
        ).withMessage("Some Exception in deep down layers");
  }

  @Test
  public void testDeleteUsingUrlWillFailForNonExistingUrl() {

    doThrow(new RuntimeException("Some Exception in deep down layers"))
        .when(socialScoreRepository).findByUrl(anyString());
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.deleteSocialScore("http://www.rte.ie/news-at-9", KeyName.URL)
        ).withMessage("Some Exception in deep down layers");
  }

  @Test
  public void testSave() {
    SocialScore socialScore = new SocialScore();
    socialScore.setScore(10);
    socialScore.setUrl("http://www.rte.ie/news-at-9");
    SocialScore fromDB = socialScoreService.save(socialScore);
    assertThat(fromDB.getId()).isNotNull();
  }

  @Test
  public void testSaveFailure() {
    when(socialScoreRepository.save(any(SocialScoreDBO.class)))
        .thenThrow(new RuntimeException("Some Exception in deep down layers"));
    SocialScore socialScore = new SocialScore();
    socialScore.setScore(10);
    socialScore.setUrl("http://www.rte.ie/news-at-9");
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.save(socialScore)
        ).withMessage("Some Exception in deep down layers");
  }

  @Test
  public void testUpdateUsingUrl() {
    assertThatCode(() ->
        socialScoreService.updateSocialScore(KeyName.URL, "http://www.rte.ie/news-at-9", 10l)
    ).doesNotThrowAnyException();
  }

  @Test
  public void testUpdateUsingID() {
    assertThatCode(() ->
        socialScoreService.updateSocialScore(KeyName.ID, "144423c1-dcd1-4c27-9d57-99386590a7da", 10l)
    ).doesNotThrowAnyException();
  }

  @Test
  public void testUpdateFailUsingUrl() {
    when(socialScoreRepository
        .updateSocialScore(anyString(), anyString(),
            anyLong()))
        .thenThrow(new RuntimeException("Some Exception in deep down layers"));
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.updateSocialScore(KeyName.URL, "http://www.rte.ie/news-at-9", 10l)
        ).withMessage("Some Exception in deep down layers");
  }

  @Test
  public void testUpdateFailUsingID() {
    when(socialScoreRepository
        .updateSocialScore(anyString(), anyString(),
            anyLong()))
        .thenThrow(new RuntimeException("Some Exception in deep down layers"));
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService
                .updateSocialScore(KeyName.ID, "144423c1-dcd1-4c27-9d57-99386590a7da", 10l))
        .withMessage("Some Exception in deep down layers");
  }

  @Test
  public void testSearchByID() {
    assertThatCode(() ->
        socialScoreService.searchSocialScores(KeyName.ID, "144423c1-dcd1-4c27-9d57-99386590a7da")
    ).doesNotThrowAnyException();
  }


  @Test
  public void testSearchByUrl() {
    assertThatCode(() ->
        socialScoreService.searchSocialScores(KeyName.URL, "http://www.rte.ie/news-at-9")
    ).doesNotThrowAnyException();
  }


  @Test
  public void testSearchByDomain() {
    assertThatCode(() ->
        socialScoreService.searchSocialScores(KeyName.DOMAIN, "www.rte.ie")
    ).doesNotThrowAnyException();
  }

  @Test
  public void testSearchByIDFailed() {
    when(socialScoreRepository
        .findById(anyString()))
        .thenThrow(new RuntimeException("Some Exception in deep down layers"));
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService
                .searchSocialScores(KeyName.ID, "144423c1-dcd1-4c27-9d57-99386590a7da"))
        .withMessage("Some Exception in deep down layers");
  }


  @Test
  public void testSearchByUrlFailed() {
    when(socialScoreRepository
        .findByUrl(anyString()))
        .thenThrow(new RuntimeException("Some Exception in deep down layers"));
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.searchSocialScores(KeyName.URL, "http://www.rte.ie/news-at-9")
        ).withMessage("Some Exception in deep down layers");
  }


  @Test
  public void testSearchByDomainFailed() {
    when(socialScoreRepository
        .findByDomainName(anyString(), any(Pageable.class)))
        .thenThrow(new RuntimeException("Some Exception in deep down layers"));
    assertThatExceptionOfType(SocialScoreException.class)
        .isThrownBy(() ->
            socialScoreService.searchSocialScores(KeyName.DOMAIN, "www.rte.ie")
        ).withMessage("Some Exception in deep down layers");
  }


  @Test
  public void testGeneRateReport() {
    assertThatCode(() ->
        socialScoreService.generateReport()
    ).doesNotThrowAnyException();
  }

  @Test
  public void testGeneRateReportFailed() {
    doThrow(new RuntimeException("Some Exception in deep down layers"))
        .when(socialScoreRepository).aggregateSocialScoreByDomainName();

    assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(() ->
            socialScoreService.generateReport()
        ).withMessage("Some Exception in deep down layers");
  }


  @TestConfiguration
  static class BoardingServiceTestContextConfiguration {

    @Bean
    public SocialScoreService socialScoreService() {
      return new SocialScoreServiceImpl();
    }
  }
}
