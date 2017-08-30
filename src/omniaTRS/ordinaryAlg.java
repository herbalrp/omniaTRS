package omniaTRS;

public class ordinaryAlg implements subalgorithm {

  private DataBaseConnector base;  
  Subresult result;
  private long ID;
  
  public ordinaryAlg()
  {
    result = new Subresult("Standard Evaluation", 2);
    
  }

  @Override
  public Subresult getResult() {
    return result;
  }

  @Override
  public void Compute() {
   double[] marks = base.getMarks(ID);
  System.out.println("OCENA:"+marks[0]);
  System.out.println("OCENA:"+marks[1]);
  System.out.println("OCENA:"+marks[2]);
   
   if((marks[0]+marks[1]+marks[2])>0)
   {
  double average =  (marks[2]/(marks[0]+marks[1]+marks[2]));
   if(average<0.7)
   {
     result.setScore(3, "\r\n Oceny pozytywne stanowi¹ " + (average*100) + "% wszystkich ocen \r\n ");
   } else if (average<0.85)
   {
     result.setScore(2, "\r\n Oceny pozytywne stanowi¹ " + (average*100) + "% wszystkich ocen \r\n ");
   }  else
   {
     result.setScore(1, "\r\n Oceny pozytywne stanowi¹ " + (average*100) + "% wszystkich ocen \r\n ");
   }
   } else
   {
     System.out.println("TUTTAJ");
     result.setScore(0, "\r\n Brak wystarczaj¹cej próbki \r\n");
   }
   
  }

  @Override
  public void setDataBase(DataBaseConnector base) {
    this.base=base;
    
  }

  @Override
  public void setCustomerID(long ID) {
   this.ID=ID;
    
  }

  

}
