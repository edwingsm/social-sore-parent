package ie.newship.social.sore.client.cli.console;

import java.util.Scanner;

public class ScannerConsoleReader implements ConsoleReader {

  private Scanner scanner;

  public ScannerConsoleReader() {
    scanner = new Scanner(System.in);
  }

  @Override
  public String readLine() {
    return this.scanner.nextLine();
  }
}
