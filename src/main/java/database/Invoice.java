package database;

public class Invoice {

  private String name;
  private int id;

  public Invoice(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public Invoice() {

  }

  private Invoice(String id) {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
