package omniaTRS;

import java.util.ArrayList;
import java.util.Comparator;

public class ValueImbalance implements subalgorithm {

  private ArrayList<double[]> priceMark;
  private long ID;
  private DataBaseConnector base;
  private final double RATE_F = 0.3;
  private final int MIN_NUM = 5;
  private Subresult result;
  
  public ValueImbalance()
  {
    result = new Subresult("Value Imbalance Attack", 2);
  }
  
  @Override
  public Subresult getResult() {
    return result;
  }

  @Override
  public void Compute() {
   base.OpenConnection();
   priceMark = base.getPriceMark(ID);
   changeMarks();
   result.setScore(1, "\r\n Nie ma podstaw do podejrzeñ. \r\n" );
   if(priceMark.size()<MIN_NUM)
   {
     result.setScore(0, "\r\n Brak wystarczaj¹cej próbki \r\n");
   } else if(ifLeft()){
     regression();
     
   } else
   {
     result.setScore(1, "\r\n Nie ma podstaw do podejrzeñ. \r\n" );
   }
   
   
  }
  
  private void regression() {
    double[][] marks = new double[priceMark.size()][2];
    double[][] price = new double[priceMark.size()][2];
    double[] xrange = new double[2];
    double[] n = new double[priceMark.size()];
    double max = 0;
    double min =0;
    for(int i=0; i<priceMark.size(); i++)
    {
      marks[i][0] = priceMark.get(i)[0];
      price[i][0] = 1;
      price[i][1] = priceMark.get(i)[1];
      n[i]=1;
      if(price[i][1]<min){min=price[i][1];};
      if(price[i][1]>max){max=price[i][1];};
    }
    xrange[0]=0;
    xrange[1]=max-min;
    LogisticRegression regression = new LogisticRegression(2, priceMark.size(), 1, marks, price, n, xrange);
    regression.compute();
    double exp = Math.exp(regression.getBeta()[0]+regression.getBeta()[1]*((max-min)/2));
    float avarageRisk = (float) (exp/(1+exp));
    float oddsRatio = (float) Math.exp(regression.getBeta()[1]*((max-min)/2));

    if(regression.getR_2()>0.8)
    {
      if(regression.getBeta()[1]>0)
      {
       result.setScore(3, "\r\n Agent z du¿ym prawdopodobieñstwem (" + (float) regression.getR_2()+") stosuje atak typu Value Imbalance. To oznacza, ¿e uczciwie sprzedaje wiele tanich produktów, "+
      "ale oszukuje na drogich. Prawdopodobieñstwo oszukania dla towaru o wartoœci "+(max-min)/2+" wynosi " + avarageRisk+" i wzrasta o "+ oddsRatio+" razy z ka¿d¹ kolejn¹ z³otówk¹. \r\n" );
      }
    } else if(regression.getR_2()>0.6)
    {
      if(regression.getBeta()[1]>0)
      {
        result.setScore(2, "\r\n Agent z pewnym prawdopodobieñstwem (" + (float) regression.getR_2()+") stosuje atak typu Value Imbalance. To oznacza, ¿e uczciwie sprzedaje wiele tanich produktów, "+
            "ale oszukuje na drogich. Prawdopodobieñstwo oszukania dla towaru o wartoœci "+(max-min)/2+" wynosi " + avarageRisk+" i wzrasta o "+ oddsRatio+" razy z ka¿d¹ kolejn¹ z³otówk¹. \r\n" );
      }
    } else 
    {
      result.setScore(1, "\r\n Nie ma podstaw do podejrzeñ. \r\n" );
   
    };
    
    
  }

  private boolean ifLeft() {
    double avarage = 0;
    int number_0 =0;
    for(int i=0; i<priceMark.size(); i++)
    {
      avarage+=priceMark.get(i)[1]*(priceMark.get(i)[0]+1);
      number_0+=priceMark.get(i)[0]+1;
    }
    avarage=avarage/number_0;
    
    
    
    Comparator<? super double[]> c = new Comparator<double[]>() {
     
      @Override
      public int compare(double[] o1, double[] o2) {
       if(o1[1]>o2[1]){
         return 1;
       } else if(o1[1]<o2[1]) { 
         return -1;
       } else
       {
         return 0;
       }
      }
  };
  
  
    priceMark.sort(c);
    int x = (int) (priceMark.size()/2) - 1;
    if (x<0){x=0;};
    double median = priceMark.get(x)[1];
    
    double rate = (avarage-median)/median;
    System.out.println("Srednia: " + avarage +" mediana: "+median+" wspolczynnik: "+rate);
    if(rate>RATE_F){
      return true;
    } else {return false;}   
    
   
  }

  private void changeMarks()
  {
    for(int i=0; i<priceMark.size(); i++)
    {
      switch ((int) priceMark.get(i)[0])
      {
        case -1: priceMark.get(i)[0]=1; break;
        case 0: priceMark.get(i)[0]=1; break;
        case 1: priceMark.get(i)[0]=0; break;
        default: priceMark.get(i)[0]=0;
      }
    }
  }

  

  @Override
  public void setDataBase(DataBaseConnector base) {
    this.base = base;
    
  }

  @Override
  public void setCustomerID(long ID) {
   this.ID=ID;
    
  }

}
