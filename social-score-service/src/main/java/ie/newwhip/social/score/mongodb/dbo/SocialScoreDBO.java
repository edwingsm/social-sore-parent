package ie.newwhip.social.score.mongodb.dbo;

import java.io.Serializable;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document (collection = "socialscore")
@Data
@NoArgsConstructor
public class SocialScoreDBO implements Serializable {

  @Id
  private String id;
  @Indexed
  private String domainName;
  private long score;
  @Indexed(unique=true)
  private String url;

}
