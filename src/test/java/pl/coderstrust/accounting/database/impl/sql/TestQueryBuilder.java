package pl.coderstrust.accounting.database.impl.sql;

public class TestQueryBuilder {

  public static final String DROP_TABLES_QUERY = "DROP TABLE IF EXISTS \"Invoice_Entry\";"
      + "DROP TABLE IF EXISTS \"Invoice\";"
      + "DROP TABLE IF EXISTS \"Company\";";

  public static final String ADD_TABLES_QUERY = "CREATE TABLE \"Company\" ("
      + " name VARCHAR(255) NOT NULL UNIQUE,"
      + " street VARCHAR(255) NOT NULL,"
      + " postal_code VARCHAR(255) NOT NULL,"
      + " city VARCHAR(255) NOT NULL,"
      + " nip int NOT NULL UNIQUE,"
      + " PRIMARY KEY (nip));"
      + " CREATE TABLE \"Invoice\" ("
      + " id BIGSERIAL,"
      + " name VARCHAR(255),"
      + " number VARCHAR(255),"
      + " age int,"
      + " issue_date DATE NOT NULL,"
      + " nip_buyer int NOT NULL,"
      + " nip_seller int NOT NULL,"
      + " FOREIGN KEY (nip_buyer) REFERENCES \"Company\"(nip),"
      + " FOREIGN KEY (nip_seller) REFERENCES \"Company\"(nip),"
      + " PRIMARY KEY (id),"
      + " CHECK (nip_buyer != nip_seller));"
      + " CREATE TABLE \"Invoice_Entry\" ("
      + " id BIGSERIAL,"
      + " description VARCHAR (255) NOT NULL,"
      + " price DECIMAL NOT NULL,"
      + " invoice_id INT NOT NULL,"
      + " PRIMARY KEY (id),"
      + " FOREIGN KEY (invoice_id) REFERENCES \"Invoice\"(id),"
      + " CHECK(price >= 0));";

}
