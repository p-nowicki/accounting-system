package pl.coderstrust.accounting.database.impl.multifile;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;
import pl.coderstrust.accounting.database.impl.helpers.FileHelper;
import pl.coderstrust.accounting.database.impl.helpers.FileInvoiceHelper;
import pl.coderstrust.accounting.database.impl.helpers.IdGenerator;
import pl.coderstrust.accounting.database.impl.helpers.ObjectMapperHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MultiFileDatabaseTest extends DatabaseTest {

  private static final String FILES_PATH = "src/test/resources/MultiFileDatabase/";
  private FileInvoiceHelper fileInvoiceHelper;
  private IdGenerator idGenerator;
  private PathHelper pathHelper;

  public MultiFileDatabaseTest() {
  }

  @Override
  public Database getDatabase() throws IOException {
    return new MultiFileDatabase(FILES_PATH, fileInvoiceHelper, pathHelper, idGenerator);
  }

  @Before
  public void setup() throws IOException {
    File file = new File(FILES_PATH);
    FileUtils.deleteDirectory(file);
    Files.createDirectories(file.toPath());

    pathHelper = new PathHelper(FILES_PATH);
    fileInvoiceHelper = new FileInvoiceHelper(new FileHelper(), new ObjectMapperHelper());
    idGenerator = new IdGenerator(new File(FILES_PATH), fileInvoiceHelper, pathHelper);
  }
}