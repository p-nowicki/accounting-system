package pl.coderstrust.accounting.database.impl.multifile;

import java.time.LocalDate;

public class InvoiceForMultifile {

  private int id;
  private LocalDate issueDate;

  public InvoiceForMultifile() {
  }

  public InvoiceForMultifile(LocalDate issueDate) {
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

