package omniaTRS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LastMarks implements subalgorithm {

  private Subresult result;
  private ArrayList<Object[]> list;
  private ArrayList<Calendar> dates;
  private ArrayList<Integer> marks;
  private long ID;
  private DataBaseConnector base;
  private SimpleDateFormat parser;
  
  
  public LastMarks()
  {
    result = new Subresult("Last Marks", 1);
    dates = new ArrayList<Calendar>();
    marks = new ArrayList<Integer>();
    parser = new SimpleDateFormat("yyyy-MM-dd");
  }
  
  
  @Override
  public Subresult getResult() {
    return result;
  }

  @Override
  public void Compute() {
   
    Subresult[] t = new Subresult[3];
    t[0] = calculeT();
    t[1] = calculeM();
    t[2] = calculeM2();
    
    
   int score = 0;
   result.setScore(score, "\r\n Brak wystarczaj¹cej próbki \r\n");
   for(int i=2; i>=0; i--)
   {
     if(t[i].getScore()==3)
     {
       result.setScore(t[i].getScore(), t[i].getComment());
     }
     else if(score<3 && t[i].getScore()==2)
     {
        result.setScore(t[i].getScore(), t[i].getComment());
     } else if(score<2 && t[i].getScore()==1)
     {
       result.setScore(t[i].getScore(), t[i].getComment());
     }
   }
   

    

  }

  private Subresult calculeM2() {
    Calendar tmp = Calendar.getInstance();
    String date = parser.format(tmp.getTime());
    double[] results = new double[6]; 
    int pos, neg, neu; 
    
    for(int i=0; i<3; i++)
    {
        pos = base.getDateMark(ID, "MM", 120-60*i, 240-60*i, 'p');
        neg = base.getDateMark(ID, "MM", 120-60*i, 240-60*i, 'n');
        neu = base.getDateMark(ID, "MM", 120-60*i, 240-60*i, 'o');
        if((neg+neu+pos)!=0)
        {
          results[i]=(pos/(neg+neu+pos));
        } else {results[i] = -1;};
       
    } 
    
    double sum = 0;     
    int i= 0;
    for(int j=0; j<2; j++)
    {
      if(results[j]>=0)
      {
        i++;
        sum+=results[j];
      }
    }
    Subresult sub = new Subresult("Dwumiesiêczne", 0);
    if(i>1 && results[2]>=0)
    {
    double average = sum/i;
    System.out.println("Œrednia œredni: "+average);
    
    if(results[5]<(average*0.60))
        {
          String tmpS = "\r\n Œredni wynik w ostatnich miesi¹cach wynosi³: "+average+". Natomiast w ostatnich 2 miesi¹cach: " + results[5]+ ". To mo¿e œwiadczy o spadku jakoœci us³ug w ostatnim czasie. \r\n ";
          sub.setScore(3, tmpS);
        } else if (results[5]<(average*0.85))
        {
          String tmpS = "\r\n Œredni wynik w ostatnich miesi¹cach wynosi³: "+average+". Natomiast w ostatnich 2 miesi¹cach: " + results[5]+ ". To mo¿e œwiadczy o spadku jakoœci us³ug w ostatnim czasie. \r\n ";
          sub.setScore(2, tmpS);
        }
        else {
          sub.setScore(1, "\r\n Nie ma podstaw do podejrzeñ. \r\n");   
          
        }
    } else
    {
      sub.setScore(0, "\r\n Brak wystarczaj¹cej próbki \r\n");
    }
   return sub;
    
  }


  private Subresult calculeM() {
    Calendar tmp = Calendar.getInstance();
    String date = parser.format(tmp.getTime());
    double[] results = new double[6]; 
    int pos, neg, neu; 
    
    for(int i=0; i<6; i++)
    {
        pos = base.getDateMark(ID, "DD", 150-i*30, 180-i*30, 'p');
        neg = base.getDateMark(ID, "DD", 150-i*30, 180-i*30, 'n');
        neu = base.getDateMark(ID, "DD", 150-i*30, 180-i*30, 'o');
        if((neg+neu+pos)!=0)
        {
          results[i]=(pos/(neg+neu+pos));
        } else {results[i] = -1;};
       
    } 
    
    double sum = 0;     
    int i= 0;
    for(int j=0; j<5; j++)
    {
      if(results[j]>=0)
      {
        i++;
        sum+=results[j];
      }
    }
    
    Subresult sub = new Subresult("Miesiêczne", 0);
    
    if(i>2 && results[5]>=0)
    {
      double average = sum/i;
      System.out.println("Œrednia œredni: "+average);
    
    if(results[5]<(average*0.60))
        {
          String tmpS = "\r\n Œredni wynik w ostatnich miesi¹cach wynosi³: "+average+". Natomiast w ostatnim miesi¹cu: " + results[5]+ ". To mo¿e œwiadczy o spadku jakoœci us³ug w ostatnim czasie. \r\n ";
          sub.setScore(3, tmpS);
        } else if (results[5]<(average*0.85))
        {
          String tmpS = "\r\n Œredni wynik w ostatnich miesi¹cach wynosi³: "+average+". Natomiast w ostatnim miesi¹cu: " + results[5]+ ". To mo¿e œwiadczy o spadku jakoœci us³ug w ostatnim czasie. \r\n ";
          sub.setScore(2, tmpS);
        }
        else {
          sub.setScore(1, "\r\n Nie ma podstaw do podejrzeñ. \r\n");   
          
        }
    } else
    {
      sub.setScore(0, "\r\n Brak wystarczaj¹cej próbki \r\n");
    }
   return sub;
  }


  private Subresult calculeT() {
      Calendar tmp = Calendar.getInstance();
      System.out.println("PODAJE CZAS: " + tmp);
     String date = parser.format(tmp.getTime());
     double[] results = new double[6]; 
     double pos, neg, neu; 
     
     for(int i=0; i<6; i++)
     {
         pos = base.getDateMark(ID, "DD", 42-(i*7), 35-(i*7), 'p');
         System.out.println("F: "+pos+" i"+i);
         neg = base.getDateMark(ID, "DD", 42-(i*7), 35-(i*7), 'n');
         System.out.println("FFF: "+neg+" i"+i);
         neu = base.getDateMark(ID, "DD", 42-(i*7), 35-(i*7), 'o');
         System.out.println("FFF: "+neu+" "+i);
         if((neg+neu+pos)!=0) //TODO zastanowi siê, czy nie powinno siê rozpatrywa dopiero, gdy liczba komentarzy przekracza jakieœ minimum
         {
           results[i]=(pos/(neg+neu+pos));
           System.out.println("FFF: "+i+"; "+results[i]);
         } else {results[i] = -1;};
     } 
     
     double sum = 0;     
     int i= 0;
     for(int j=0; j<5; j++)
     {
       if(results[j]>=0)
       {
         i++;
         sum+=results[j];
       }
     }  
     Subresult sub = new Subresult("Tygodniowe", 0);
     if(i>2 && results[5]>=0)
     {
       
     
       double average = sum/i;
       System.out.println("Œrednia œredni: "+average + " "+results[5]);
       
       if(results[5]<(average*0.60))
           {
             String tmpS = "\r\n Œredni wynik w ostatnich tygodniach wynosi³: "+average+". Natomiast w ostatnim tygodniu: " + results[5]+ ". To mo¿e œwiadczy o spadku jakoœci us³ug w ostatnim czasie. \r\n ";
             sub.setScore(3, tmpS);
             System.out.println("Œrednia œredni: "+average + " "+results[5]);
           } else if (results[5]<(average*0.85))
           {
             String tmpS = "\r\n Œredni wynik w ostatnich tygodniach wynosi³: "+average+". Natomiast w ostatnim tygodniu: " + results[5]+ ". To mo¿e œwiadczy o spadku jakoœci us³ug w ostatnim czasie. \r\n ";
             sub.setScore(2, tmpS);
           } else {
             sub.setScore(1, "\r\n Nie ma podstaw do podejrzeñ. \r\n");   
             
           }
         
            
        } else
        {
          sub.setScore(0, "\r\n Brak wystarczaj¹cej próbki \r\n");
        }
    
     System.out.println("SUB: "+sub.getComment());
    return sub;
  }


  @Override
  public void setDataBase(DataBaseConnector base) {
    this.base = base;

  }

  @Override
  public void setCustomerID(long ID) {
    this.ID = ID;

  }

}
