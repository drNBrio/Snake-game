package snake;
import java.util.*;

public class Body{
  private LinkedList nodes;
  private ArrayList<Coordinate> allPositions;
  private int lastDir = 0;
  private int startPoint =100;
  public int dx = 10;
  
  public int getStartPoint(){
    return startPoint; 
  }
    
  /* checks to see if snake has hit self */
    public boolean checkDeath(){
      for(int i = 0; i < allPositions.size(); i++){
        for(int j = 0; j < allPositions.size(); j++){
          if(i != j){
            if(allPositions.get(i).equals(allPositions.get(j))){
              return false; 
            }
          }
        }
      }
      return true;
  }
    
    /* checks to see if snake has hit wall */
    public boolean checkWalls(){
      for(Coordinate c : allPositions){
        if(c.getX() >= 2*startPoint || c.getX() <=0 || c.getY() >= 2*startPoint || c.getY() <= 0){
          return false; 
        }
      }
      return true;
    }
  
  
  
  /*constructor */
  public Body(int gridSize){
    startPoint = gridSize;
    nodes = new LinkedList(new BodyNode(new Coordinate(startPoint,startPoint)), new BodyNode(new Coordinate(startPoint,startPoint+dx)), new BodyNode(new Coordinate(startPoint,startPoint+2*dx)));
    allPositions = new ArrayList<Coordinate>();
    allPositions.add(nodes.getFirst().getValue().getCoordinate());
    allPositions.add(nodes.getFirst().getNext().getValue().getCoordinate());
    allPositions.add(nodes.getLast().getValue().getCoordinate());
     
  }
  
  /** if eaten food add a node*/
  public void addNode(){
    BodyNode newNode;
    if(lastDir == 0){
    newNode = new BodyNode(new Coordinate(nodes.getLast().getValue().getCoordinate().getX(), nodes.getLast().getValue().getCoordinate().getY()-dx));
    } else if(lastDir == 1){
      newNode = new BodyNode(new Coordinate(nodes.getLast().getValue().getCoordinate().getX()-dx, nodes.getLast().getValue().getCoordinate().getY()));
    } else if(lastDir == 2){
      newNode = new BodyNode(new Coordinate(nodes.getLast().getValue().getCoordinate().getX(), nodes.getLast().getValue().getCoordinate().getY()+dx));
    } else {
      newNode = new BodyNode(new Coordinate(nodes.getLast().getValue().getCoordinate().getX()+dx, nodes.getLast().getValue().getCoordinate().getY()));
    }
    nodes.addNode(newNode);
    allPositions.add(newNode.getCoordinate());
  }
  
  /*calls to move nodes */
  public void move(int dir){
    lastDir = dir;
    nodes.moveAll(dir);      
  }
  
  public LinkedList getNodes(){
    return nodes; 
  }
  
  public ArrayList<Coordinate> getPositions(){
    return allPositions; 
  }
}