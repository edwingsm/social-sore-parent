package ie.newswhip.social.sore.client.cli.console;

import java.util.Scanner;

public class ScannerCommandReader implements CommandReader {

  private Scanner scanner;

  public ScannerCommandReader() {
    scanner = new Scanner(System.in);
  }

  @Override
  public String readCommand() {
    return this.scanner.nextLine();
  }
}
