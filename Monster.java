package snake;
import java.util.Random;

public class Monster{
  private Coordinate position;
  private Random rand = new Random();
  private Body s;
  private String difficulty = "e";
  
  public Coordinate getPosition(){
    return position; 
  } 
  
  public Monster(Body s, String diff){
    this.difficulty = diff;
    Random rand = new Random();
    if(diff.equals("m")){
      if(rand.nextDouble() < 0.5){
        diff = "e"; 
      }
    }
    this.s = s;
    position = new Coordinate((int) Math.round(rand.nextDouble()*s.getStartPoint()*0.2)*10, (int) Math.round(rand.nextDouble()*s.getStartPoint()*0.2)*10);
  }
  
  /*moving the monster in a random direction */
  public void move(){
    double prob = rand.nextDouble();
    if(difficulty.equals("e")){
      if(prob <= 0.25){
        if(position.getY() <= s.getStartPoint()*2 ){
          position.setY(position.getY()+10);
        } else {
          position.setY(position.getY()-10);
        }
      } else if (prob <= 0.5){
        if(position.getX() <= s.getStartPoint()*2){
          position.setX(position.getX()+10);
        } else {
          position.setX(position.getX()-10);
        }
      } else if(prob <= 0.75){
        if(position.getY() >= 0){
          position.setY(position.getY()-10);
        } else {
          position.setY(position.getY()+10);
        }
      } else {
        if(position.getX() >= 0){
          position.setX(position.getX()-10);
        } else {
          position.setX(position.getX()+10);
        }
      }
    } else if(difficulty.equals("m") || difficulty.equals("h")){
      if(prob < 0.5){
        if(s.getNodes().getFirst().getValue().getCoordinate().getX() < position.getX()){
          position.setX(position.getX() - s.dx); 
        } else {
          position.setX(position.getX() + s.dx); 
        }
      } else {
        if(s.getNodes().getFirst().getValue().getCoordinate().getY() < position.getY()){
          position.setY(position.getY() - s.dx); 
        } else {
          position.setY(position.getY() + s.dx); 
        }
      }
    }
  }
}