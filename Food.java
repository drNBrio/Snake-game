package snake;
import java.util.Random;

public class Food{
  private Coordinate position;
  
  public Coordinate getPosition(){
    return position; 
  }
  
  /*constructor */
  public Food(Body s){
    Random rand = new Random();
    position = new Coordinate((int) Math.round(rand.nextDouble()*s.getStartPoint()*0.2)*10, (int) Math.round(rand.nextDouble()*s.getStartPoint()*0.2)*10); 
  }
}