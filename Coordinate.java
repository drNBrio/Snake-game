package snake;

public class Coordinate{
  private int x,y;
  
  
  public Coordinate(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public int getY(){
    return y;
  }
  
  public int getX(){
    return x;
  }
  
  public void setY(int y){
    this.y = y; 
  }
  
  public void setX(int x){
    this.x = x; 
  }
  
  public void setCoord(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public String toString(){
    return "(" + x + "," + y + ")";
  }
  
  public boolean equals(Coordinate c){
    if(this.x == c.x && this.y == c.y){
      return true; 
    }
    return false;
  }
}