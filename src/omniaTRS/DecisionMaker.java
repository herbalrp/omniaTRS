package omniaTRS;

import java.util.ArrayList;

public class DecisionMaker {
  
  private int decision;
  private ArrayList<Subresult> listOfResults;
  private long ID;
  private DataBaseConnector base;
  
  public DecisionMaker(long ID, DataBaseConnector base)
  {
    this.ID=ID;
    this.base = base;
    this.listOfResults = new ArrayList<Subresult>();
  }
  
  public int getDecision()
  {
    
    return decision;
  }
  
  public ArrayList<Subresult> getResults()
  {
    return listOfResults;
  }
  
  public void compute()
  {
    this.listOfResults = new ArrayList<Subresult>();
    ValueImbalance s1 = new ValueImbalance();
    s1.setDataBase(base);
    s1.setCustomerID(ID);
    s1.Compute();
    listOfResults.add(s1.getResult());
    
    LastMarks s2 = new LastMarks();
    s2.setDataBase(base);
    s2.setCustomerID(ID);
    s2.Compute();
    listOfResults.add(s2.getResult());
    
    ordinaryAlg s3 = new ordinaryAlg();
    s3.setDataBase(base);
    s3.setCustomerID(ID);
    s3.Compute();
    listOfResults.add(s3.getResult());
    
    decision = check3P();
  }
  
  private int check3P()
  {
   
    int numberOf23 = 0;
    int numberOf22 = 0;
    int numberOf13 = 0;
    int numberOf12 = 0;
    for(Subresult r: listOfResults)
    {
      if(r.getPriority()==2 & r.getScore()==3)
      {
        numberOf23 ++;
        
      } else if(r.getPriority()==2 & r.getScore()==2)
      {
        numberOf22 ++;
      } else if(r.getPriority()==1 & r.getScore()==3)
      {
        numberOf13 ++;
      } else if(r.getPriority()==1 & r.getScore()==2)
      {
        numberOf12 ++;
      }
    }
    
    if(numberOf23>0){return 2;};
    if(numberOf22>1){return 2;};    
    if(numberOf13>1){return 2;};
    if(numberOf22>0){return 1;};
    if(numberOf13>0){return 1;};
    if(numberOf12>0){return 1;};
    
    
    return 0;
  }

}
