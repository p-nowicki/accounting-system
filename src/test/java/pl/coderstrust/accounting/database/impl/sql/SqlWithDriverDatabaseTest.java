package pl.coderstrust.accounting.database.impl.sql;

import static pl.coderstrust.accounting.database.impl.sql.TestQueryBuilder.ADD_TABLES_QUERY;
import static pl.coderstrust.accounting.database.impl.sql.TestQueryBuilder.DROP_TABLES_QUERY;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlWithDriverDatabaseTest extends DatabaseTest {

  private Database database;

  @Override
  public Database getDatabase() throws SQLException {
    return new SqlWithDriverDatabase();
  }

  @Before
  public void setup() throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/coders", "postgres",
        "postgres");

    Statement statement = connection.createStatement();
    statement.executeUpdate(DROP_TABLES_QUERY);

    PreparedStatement statementAddTables = connection.prepareStatement(ADD_TABLES_QUERY);
    statementAddTables.execute();
  }
}