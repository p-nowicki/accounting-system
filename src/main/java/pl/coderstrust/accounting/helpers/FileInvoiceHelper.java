package pl.coderstrust.accounting.database.impl.multifile;

import pl.coderstrust.accounting.database.ObjectMapperHelper;
import pl.coderstrust.accounting.logic.FileHelper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileInvoiceHelper {

  private FileHelper fileHelper;
  private ObjectMapperHelper objectMapperHelper;

  public FileInvoiceHelper(FileHelper fileHelper, ObjectMapperHelper objectMapperHelper) {
    this.fileHelper = fileHelper;
  }

  public void saveInvoiceToFile(Invoice invoice, File file) {
    fileHelper.writeFile(file, objectMapperHelper.convertInvoiceToJson(invoice));
  }

  public List<Invoice> readInvoicesFromFile(File file) {
    return fileHelper.readLinesFromFile(file)
        .stream()
        .map(objectMapperHelper::convertJsonToInvoice)
        .collect(Collectors.toList());
  }
}
