package database;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    InFileDatabase inFileDatabase = new InFileDatabase();
    inFileDatabase.saveInvoice("this is a test");
    System.out.println(inFileDatabase.getInvoices());
  }
}
