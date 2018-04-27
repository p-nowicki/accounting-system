package pl.coderstrust.accounting.database.memory;


import pl.coderstrust.accounting.database.DatabaseTest;
import pl.coderstrust.accounting.logic.Database;

public class InMemoryDatabaseTest extends DatabaseTest {

  @Override
  protected Database getDatabase() {
    return new InMemoryDatabase();
  }
}