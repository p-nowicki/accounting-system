package pl.coderstrust.accounting.database.impl.file;

import org.junit.Before;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;
import pl.coderstrust.accounting.database.impl.helpers.FileHelper;
import pl.coderstrust.accounting.database.impl.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.database.impl.helpers.IdGenerator;
import pl.coderstrust.accounting.database.impl.helpers.ObjectMapperHelper;
import pl.coderstrust.accounting.database.impl.multifile.PathHelper;

import java.io.File;
import java.io.IOException;

public class InFileDatabaseTest extends DatabaseTest {

  private static final File file = new File("src/test/resources/newFile");
  private FileHelper fileHelper;
  private ObjectMapperHelper objectMapperHelper;
  private FileInvoiceHelper fileInvoiceHelper;
  private PathHelper pathHelper;
  private IdGenerator idGenerator;

  @Override
  protected Database getDatabase() throws IOException {
    return new InFileDatabase(file, fileInvoiceHelper, idGenerator);
  }

  @Before
  public void shouldRemoveFileBeforeTest() throws IOException {
    file.delete();
    file.createNewFile();

    fileHelper = new FileHelper();
    objectMapperHelper = new ObjectMapperHelper();
    fileInvoiceHelper = new FileInvoiceHelper(fileHelper, objectMapperHelper);
    pathHelper = new PathHelper(file);
    idGenerator = new IdGenerator(file, fileInvoiceHelper, pathHelper);
  }
}