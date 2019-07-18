package ie.newwhip.social.socre.client.socre.client.cli;

import ie.newwhip.social.socre.client.score.core.SocialScoreReport;
import ie.newwhip.social.socre.client.socre.client.model.Command;
import java.util.List;

public interface SocialScoreCliExecutor extends CliExecutor<Command> {

  String saveSocialScore(Command score) throws Exception;

  boolean removeSocialScore(Command input);

  List<SocialScoreReport> generateReport(Command input);

}
