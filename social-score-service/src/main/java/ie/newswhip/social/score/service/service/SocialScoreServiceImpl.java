package ie.newswhip.social.score.service.service;

import ie.newswhip.social.score.service.util.ServiceUtil;
import ie.newswhip.social.score.core.model.SearchKey;
import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.core.model.SocialScoreReport;
import ie.newswhip.social.score.service.exception.FailurePoint;
import ie.newswhip.social.score.service.exception.SocialScoreException;
import ie.newswhip.social.score.service.mongodb.SocialScoreRepository;
import ie.newswhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SocialScoreServiceImpl implements SocialScoreService {

  @Autowired
  private SocialScoreRepository socialScoreRepository;


  @Override
  public SocialScore save(SocialScore socialScore) throws SocialScoreException {
    try {
      SocialScoreDBO socialScoreDBO = new SocialScoreDBO();
      URL myUrl = new URL(socialScore.getUrl());
      socialScoreDBO.setDomainName(myUrl.getHost());
      socialScoreDBO.setUrl(socialScore.getUrl());
      socialScoreDBO.setScore(socialScore.getScore());
      SocialScoreDBO saved = socialScoreRepository.save(socialScoreDBO);
      return ServiceUtil.builder().socialScoreDBO(saved).build();
    } catch (Exception e) {
      log.error("Failed to save {} , due to {}",socialScore,e.getMessage());
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoint.SAVE);
    }
  }

  @Override
  public void deleteSocialScore(String key, SearchKey keyType) throws SocialScoreException {
    try {
      switch (keyType) {
        case ID:
          socialScoreRepository.deleteById(key);
          break;
        case URL:
          deleteByUrl(key);
          break;
        default:
          throw new IllegalArgumentException("Invalid Key used to delete the social score");
      }
    } catch (Exception e) {
      log.error("Failed to delete social score with key {}, value {} due to {}",keyType,key,e.getMessage());
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoint.DELETE);
    }

  }


  private void deleteByUrl(String url) {
    final String decodedUrl = decodeUrl(url);
    SocialScore socialScoreDBO = findByUrl(decodedUrl);
    socialScoreRepository.deleteById(socialScoreDBO.getId());
  }

  @Override
  public List<SocialScore> searchSocialScores(SearchKey key, String value) {
    List<SocialScore> socialScores = new ArrayList<>();
    try {
      switch (key) {
        case ID:
          socialScores.add(findById(value));
          break;
        case URL:
          socialScores.add(findByUrl(value));
          break;
        case DOMAIN:
          socialScores.addAll(findByDomainName(value));
          break;
      }
    } catch (Exception e) {
      log.error("Failed to find social score with key {}, value {} due to {}",key,value,e.getMessage());
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoint.LOOKUP);
    }
    return socialScores;
  }


  @SneakyThrows(Exception.class)
  private SocialScore findById(String id) {
    Optional<SocialScoreDBO> socialScoreDBO = socialScoreRepository.findById(id);
    socialScoreDBO
        .orElseThrow(() -> new RuntimeException("Couldn't find Social updatedScore for the given Id"));
    return ServiceUtil.builder().socialScoreDBO(socialScoreDBO.get()).build();
  }

  @SneakyThrows(Exception.class)
  private SocialScore findByUrl(String url) {
    final String decodedUrl = decodeUrl(url);
    Optional<SocialScoreDBO> socialScoreDBO = socialScoreRepository.findByUrl(decodedUrl);
    socialScoreDBO
        .orElseThrow(() -> new RuntimeException("Couldn't find Social updatedScore for the given url "));
    return ServiceUtil.builder().socialScoreDBO(socialScoreDBO.get()).build();
  }


  private List<SocialScore> findByDomainName(String author) {
    Page<SocialScoreDBO> socialScoreDBOS = socialScoreRepository
        .findByDomainName(author, Pageable.unpaged());
    return socialScoreDBOS.getContent()
        .stream()
        .map(dbo -> ServiceUtil.builder().socialScoreDBO(dbo).build())
        .collect(Collectors.toList());
  }

  @Override
  public void updateSocialScore(SearchKey keyType, String key, long score)
      throws SocialScoreException {
    try {
      switch (keyType) {
        case ID:
          updateSocialScoreById(key, score);
          break;
        case URL:
          updateSocialScoreByUrl(key, score);
          break;
        default:
          throw new IllegalArgumentException("Invalid Key used to update the social score");

      }
    } catch (Exception e) {
      log.error("Failed to update social score with key {}, value {} due to {}",keyType,key,e.getMessage());
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoint.UPDATED);
    }
  }


  private void updateSocialScoreById(String id, long score) {
    socialScoreRepository.updateSocialScore("id", id, score);
  }

  private void updateSocialScoreByUrl(String url, long score) {
    socialScoreRepository.updateSocialScore("url", decodeUrl(url), score);

  }

  @SneakyThrows(UnsupportedEncodingException.class)
  private String decodeUrl(String encodedUrl) {
    final String url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name());
    return url;
  }

  @Override
  public List<SocialScoreReport> generateReport() {
    return socialScoreRepository.aggregateSocialScoreByDomainName();
  }

}
