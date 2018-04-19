package database;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    InFileDatabase inFileDatabase = new InFileDatabase();
    Invoice invoice = new Invoice();
    invoice.setName("lukas");
    invoice.setAge(12);
    inFileDatabase.saveInvoice(invoice);
  }
}
