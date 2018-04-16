package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public class InFileDatabase implements Database {

  private String fileName = "src/main/resources/file/newFile.txt";
  private FileHelper fileHelper = new FileHelper();

  public InFileDatabase(String fileName) {
    this.fileName = fileName;
  }

  InFileDatabase() {

  }

  public void saveInvoice(String invoice) throws IOException {

    fileHelper.writeFile(fileName, invoice);
  }

  public Collection<String> getInvoices() throws FileNotFoundException {
    return fileHelper.readLinesFromFile(fileName);
  }

  public void updateInvoice(Invoice invoice) {

//    fileHelper.writeFile(String.valueOf(getInvoices()), String.valueOf(invoice));

  }

  public void removeInvoiceById(int id) {

//    fileHelper.remove(id);

  }
}
