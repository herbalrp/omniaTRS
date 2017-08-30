package omniaTRS;

public interface subalgorithm {
  public Subresult getResult();
  public void Compute();
  public void setDataBase(DataBaseConnector base);
  public void setCustomerID(long ID);
}
