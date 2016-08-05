package snake;
import java.awt.*;

public class BodyNode{
  private Color colour;
  private Coordinate position;
  
  public BodyNode(Coordinate c){
    position = c; 
  }
  
  public Coordinate getCoordinate(){
    return position; 
  }
  
  public void setCoordinate(Coordinate p){
    position = p; 
  }
}