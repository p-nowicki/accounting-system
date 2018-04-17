package pl.coderstrust.database.impl.multifile;

import java.time.LocalDate;

public class Invoice {

  private int id;
  private LocalDate issueDate;

  public Invoice() {
  }

  public Invoice(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }


}

