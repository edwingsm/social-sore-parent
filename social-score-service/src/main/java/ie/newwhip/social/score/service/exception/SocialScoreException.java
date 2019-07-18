package ie.newwhip.social.score.service.exception;

import lombok.Getter;

public class SocialScoreException extends RuntimeException {

  @Getter
  private FailurePoint failure = FailurePoint.UNKNOWN;



  public SocialScoreException(String message, FailurePoint failure){
    super(message);
    this.failure=failure;
  }

  public SocialScoreException(String message, Throwable throwable , FailurePoint failure){
    super(message,throwable);
    this.failure= failure;
  }


}
