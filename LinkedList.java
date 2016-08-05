package snake;

import java.util.*;

public class LinkedList{
  private Node first;
  private Node last;
  public int dx = 10;
  
  /*constructor*/
  public LinkedList(BodyNode one, BodyNode two, BodyNode three){
    first = new Node(one);
    first.setNext(new Node(two));
    first.getNext().setPrev(first);
    first.getNext().setNext(new Node(three));
    first.getNext().getNext().setPrev(first.getNext());
    last = first.getNext().getNext();     
  }
  
  public Node getFirst(){
    return first; 
  }
  
  public Node getNext(Node prev){
    return prev.getNext(); 
  }
  
  /* returns the coordinates of all of the nodes in the snake */
  public ArrayList<Coordinate> getCoord(){
    Node itrNode = first;
    ArrayList<Coordinate> coord = new ArrayList<Coordinate>();
    while (itrNode != null){
      coord.add(itrNode.getValue().getCoordinate());
      itrNode = itrNode.next;
    }
    return coord;
  }

public Node getLast(){
  return last; 
}

/*when the snake eats a node must be added */
public void addNode(BodyNode a){
  last.setNext(new Node(a));
  last.getNext().setPrev(last);
  last = last.getNext();
}

public Node returnList(){
  return first; 
}

/*moves all nodes of the snake */
public void moveAll(int dir){
  Node nodeChange = last;
  while(nodeChange.prev != null){
    nodeChange.getValue().getCoordinate().setCoord(nodeChange.prev.getValue().getCoordinate().getX(), nodeChange.prev.getValue().getCoordinate().getY());
    nodeChange = nodeChange.prev;
  }
  if(dir == 0){
   first.getValue().getCoordinate().setY(first.getValue().getCoordinate().getY()+dx); 
  } else if ( dir == 1){
    first.getValue().getCoordinate().setX(first.getValue().getCoordinate().getX()+dx); 
  } else if (dir == 2){
    first.getValue().getCoordinate().setY(first.getValue().getCoordinate().getY()-dx); 
  } else {
    first.getValue().getCoordinate().setX(first.getValue().getCoordinate().getX()-dx); 
  }
}

/* a private class to look after the nodes */
public class Node{
  private BodyNode b;
  private Node prev;
  private Node next;
  
  public Node(BodyNode val){
    b = val; 
  }
  
  public BodyNode getValue(){
    return b;
  }
  
  public Node getPrev(){
    return prev;
  }
  
  public Node getNext(){
    return next;
  }
  
  public void setValue(BodyNode b){
    this.b = b;
  }
  
  public void setPrev(Node prev){
    this.prev = prev;
  }
  
  public void setNext(Node next){
    this.next = next;
  }
}
}