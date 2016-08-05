package snake;
import java.awt.*;
import java.util.Random;

public class ColouredFood{
  private Color colour;
  private Coordinate position;
  
  
  public ColouredFood(Body s){
     Random rand = new Random();
     position = new Coordinate((int) Math.round(rand.nextDouble()*s.getStartPoint()*0.2)*10, (int) Math.round(rand.nextDouble()*s.getStartPoint()*0.2)*10);
     
  }
}