package omniaTRS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class DataBaseConnector {

  private Connection con;
  
  public DataBaseConnector()
  {
    
  }
  
  
  public boolean OpenConnection(){
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection("jdbc:sqlserver://KOMPUTER\\SQLEXPRESS; dataBaseName=Allegro;", "sa", "Magisterka2018");
      return true;
    } catch (Exception e) {
      
      return false;
    }
   
  }
  
  public double[] getMarks(long iDAL)
  {
    try {
      double[] results = new double[3];
      Statement stmt = con.createStatement();
      
      String sql;
      sql = "SELECT IDSeller FROM Seller WHERE IDSellerAL = " + iDAL;
      ResultSet rs = stmt.executeQuery(sql);      
      rs.next();
      long iD = rs.getLong("IDSeller");      
      rs = stmt.executeQuery(sql);
      ResultSet rs_tmp;   
      sql = "SELECT count(*) AS 'N' FROM Comment a INNER JOIN Offer b ON a.IDOffer = b.IDOffer WHERE  Mark like 'p' AND b.IDSeller="+iD;
      rs_tmp = con.createStatement().executeQuery(sql);        
      rs_tmp.next();
      results[2] = rs_tmp.getInt("N");  
      System.out.println("OCENYYY: "+ results[2]);
      
      sql = "SELECT count(*) AS 'N' FROM Comment a INNER JOIN Offer b ON a.IDOffer = b.IDOffer WHERE  Mark like 'o' AND b.IDSeller="+iD;
      rs_tmp = con.createStatement().executeQuery(sql);        
      rs_tmp.next();
      results[1] = rs_tmp.getInt("N");   
      System.out.println("OCENYYY: "+ results[1]);
      
      sql = "SELECT count(*) AS 'N' FROM Comment a INNER JOIN Offer b ON a.IDOffer = b.IDOffer WHERE  Mark like 'n' AND b.IDSeller="+iD;
      rs_tmp = con.createStatement().executeQuery(sql);        
      rs_tmp.next();
      results[0] = rs_tmp.getInt("N");   
      System.out.println("OCENYYY: "+ results[0]);
      
      rs.close();
      stmt.close();
      return results;
      
    } catch (SQLException e) {
     
      System.out.println(e);
      return null;
    }
    
  }
  
  public int getDateMark(long iDAL, String datepart, int startdate, int enddate, char mark)
  {
    try {
      ArrayList<Object[]> list = new ArrayList(); 
      Statement stmt = con.createStatement();
      Object[] tab_tmp;
      
      String sql;
      sql = "SELECT IDSeller FROM Seller WHERE IDSellerAL = " + iDAL;
      System.out.println(sql);
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      long iD = rs.getLong("IDSeller");
      
      sql = "SELECT Count(*) AS 'Number' FROM Comment a INNER JOIN Offer b ON a.IDOffer=b.IDOffer WHERE b.IDSeller = " + iD +" AND a.Mark LIKE '"+mark+"' AND  DATEDIFF("+datepart+", a.Date, GETDATE()) BETWEEN "+enddate+" AND "+startdate;
      ResultSet rs_tmp;
      rs_tmp = con.createStatement().executeQuery(sql);
      rs_tmp.next(); 
        
      int result = rs_tmp.getInt("Number");
      
     
    
     
      rs.close();
      stmt.close();
      return result;
      
    } catch (Exception e) {
      System.out.println(e.toString());
      return -1;
    }
        
  }
  
  public ArrayList<double[]> getPriceMark(long iDAL) {
    try {
      ArrayList<double[]> list = new ArrayList(); 
      Statement stmt = con.createStatement();
      
      String sql;
      sql = "SELECT IDSeller FROM Seller WHERE IDSellerAL = " + iDAL;
      System.out.println(sql);
      ResultSet rs = stmt.executeQuery(sql);
      
      rs.next();
      long iD = rs.getLong("IDSeller");
      System.out.println(iD);
      sql = "SELECT IDOffer, Price FROM Offer WHERE IDSeller = "+iD;
      rs = stmt.executeQuery(sql);
      ResultSet rs_tmp;
      double[] tab_tmp;
      while(rs.next())
      {      
        double price = rs.getDouble("Price");
        System.out.println("cena: " +  price);
        sql ="SELECT Mark FROM Comment WHERE IDOffer = " + rs.getLong("IDOffer");
        rs_tmp = con.createStatement().executeQuery(sql);
        while(rs_tmp.next())
        {
          tab_tmp=new double[2];
          System.out.println(rs_tmp.getString("Mark"));
          tab_tmp[0] = changeToDouble(rs_tmp.getString("Mark"));  
          tab_tmp[1] = price;
          list.add(tab_tmp);
        }   
       
      }
      rs.close();
      stmt.close();
      return list;
      
    } catch (SQLException e) {
      System.out.println(e.toString());
      return null;
    }
        
    
  }


  private double changeToDouble(String string) {
    if(string.equals("n"))
    {
      return -1;
    } else if(string.equals("o"))
    {
      return 0;
    } else if(string.equals("p"))
    {
      return 1;
    } else
    {
      return 2;
    }
    
  }

}


