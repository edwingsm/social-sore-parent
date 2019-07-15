package ie.newwhip.social.score.service;

import ie.newwhip.social.score.exception.FailurePoints;
import ie.newwhip.social.score.exception.SocialScoreException;
import ie.newwhip.social.score.model.KeyName;
import ie.newwhip.social.score.model.SocialScore;
import ie.newwhip.social.score.model.SocialScoreReport;
import ie.newwhip.social.score.mongodb.SocialScoreRepository;
import ie.newwhip.social.score.mongodb.dbo.SocialScoreDBO;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
      return SocialScore.builder().socialScoreDBO(saved).build();
    } catch (Exception e) {
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoints.SAVE);
    }
  }

  @Override
  public void deleteSocialScore(String key, KeyName keyType) throws SocialScoreException {
    try {
      switch (keyType) {
        case ID:
          socialScoreRepository.deleteById(key);
          break;
        case URL:
          deleteByUrl(key);
          break;
        default:
          break;
      }
    } catch (Exception e) {
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoints.DELETE);
    }

  }


  private void deleteByUrl(String url) {
    final String decodedUrl = decodeUrl(url);
    SocialScore socialScoreDBO = findByUrl(decodedUrl);
    socialScoreRepository.deleteById(socialScoreDBO.getId());
  }

  @Override
  public List<SocialScore> searchSocialScores(KeyName key, String value) {
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
        default:
          break;
      }
    } catch (Exception e) {
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoints.LOOKUP);
    }
    return socialScores;
  }


  private SocialScore findById(String id) {
    Optional<SocialScoreDBO> socialScoreDBO = socialScoreRepository.findById(id);
    socialScoreDBO
        .orElseThrow(() -> new RuntimeException("Couldn't find Social score for the given Id"));
    return SocialScore.builder().socialScoreDBO(socialScoreDBO.get()).build();
  }


  private SocialScore findByUrl(String url) {
    Optional<SocialScoreDBO> socialScoreDBO = socialScoreRepository.findByUrl(url);
    socialScoreDBO
        .orElseThrow(() -> new RuntimeException("Couldn't find Social score for the given url "));
    return SocialScore.builder().socialScoreDBO(socialScoreDBO.get()).build();
  }


  private List<SocialScore> findByDomainName(String author) {
    Page<SocialScoreDBO> socialScoreDBOS = socialScoreRepository
        .findByDomainName(author, Pageable.unpaged());
    return socialScoreDBOS.getContent()
        .stream()
        .map(dbo -> SocialScore.builder().socialScoreDBO(dbo).build())
        .collect(Collectors.toList());
  }

  @Override
  public void updateSocialScore(KeyName keyType, String key, long score)
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
          break;
      }
    } catch (Exception e) {
      throw new SocialScoreException(e.getMessage(), e.getCause(), FailurePoints.UPDATED);
    }
  }


  private void updateSocialScoreById(String id, long score) {
    socialScoreRepository.updateSocialScore("id", id, score);
  }

  private void updateSocialScoreByUrl(String url, long score) {
    socialScoreRepository.updateSocialScore("url", decodeUrl(url), score);

  }

  private String decodeUrl(String encodedUrl) {
    String url = encodedUrl;
    try {
      url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
    }
    return url;
  }

  @Override
  public List<SocialScoreReport> generateReport() {

    return socialScoreRepository.aggregateSocialScoreByDomainName();
  }

}
