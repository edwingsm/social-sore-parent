package ie.newwhip.social.socre.client.cli.execution;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

@Data
@AllArgsConstructor
public class AddExecution implements Runnable {

  private RestTemplate restTemplate;
  private String[] inputArray;


  @Override
  public void run() {

  }
}
