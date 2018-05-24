package pl.coderstrust.accounting;

public class TaxCalculator {
  private long id;
  private String content;

  public TaxCalculator(){}

  public TaxCalculator(long id, String content){
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }
  public String getContent(){
    return content;
  }
 // public void setId(int id){
   // this.id = id;
  //}
}
