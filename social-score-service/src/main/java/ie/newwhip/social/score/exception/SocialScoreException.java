package ie.newwhip.social.score.exception;

import lombok.Getter;

public class SocialScoreException extends RuntimeException {

  @Getter
  private FailurePoints failure = FailurePoints.UNKNOWN;



  public SocialScoreException(String message, FailurePoints failure){
    super(message);
    this.failure=failure;
  }

  public SocialScoreException(String message, Throwable throwable , FailurePoints failure){
    super(message,throwable);
    this.failure= failure;
  }


}
