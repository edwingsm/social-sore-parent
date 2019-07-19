package ie.newship.social.sore.client.cli;


import ie.newship.social.score.core.SocialScoreReport;
import ie.newship.social.sore.client.model.Command;

import java.util.List;

public interface SocialScoreCliExecutor extends CliExecutor<Command> {

  String saveSocialScore(Command score) throws Exception;

  boolean removeSocialScore(Command input);

  List<SocialScoreReport> generateReport(Command input);

}
