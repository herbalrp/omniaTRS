package omniaTRS;

public class Subresult {
  
  private String name;
  private int priority;
  private int score;
  private String comment;
  
  public Subresult(String name, int priority)
  {
    this.name = name;
    this.priority = priority;   
  }
  
  public void setScore(int score, String comment)
  {
    this.score = score;
    this.comment = comment;
  }
  
  public String getName()
  {
    return name;
  }
  
  public int getPriority()
  {
    return priority;
  }
  
  public int getScore()
  {
    return score;
  }
  
  public String getComment()
  {
    return comment;
  }

}
