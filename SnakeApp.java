package snake;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.applet.Applet;
import java.util.*;
import java.io.*;

public class SnakeApp extends Applet implements Runnable, KeyListener{
  
  private Body snakeBody = new Body(100);
  private ArrayList<Food> food = new ArrayList<Food>();
  private ArrayList<Poison> poison = new ArrayList<Poison>();
  private ArrayList<Monster> monster = new ArrayList<Monster>();
  private int direction = 1;
  private double period = 400;
  private int foodTimer = 5;
  private int poisonTimer = 15;
  private int radius;
  private int score = 0;
  private Thread thread;
  private Scanner fileScan;
  private String name;

  /* for thread */
  public void init(){
    setSize(snakeBody.getStartPoint()*2,snakeBody.getStartPoint()*2);
    addKeyListener(this);
  }
  
  
  // empty methods needed to implement runnable interface lol
  public void start(){
    setSize(snakeBody.getStartPoint()*2,snakeBody.getStartPoint()*2);
    thread = new Thread(this);
    thread.start();
  }
  
  /*default thread methods*/
  public void stop(){
    
  }
  
  public void destroy(){
  }
  
  /*draws all monsters, snakes, poison, food, and any others*/
  public void paint(Graphics g){
    g.setColor(Color.red);
    for(Coordinate pos : snakeBody.getPositions()){
      g.fillOval(pos.getX()-radius,pos.getY()-radius, radius, radius);
    }
    g.setColor(Color.green);
    for(Food c : food){
      g.fillOval(c.getPosition().getX() - radius, c.getPosition().getY()-radius, radius, radius); 
    }
    g.setColor(new Color(0x9B30FF));
    for(Poison p : poison){
      g.fillOval(p.getPosition().getX() - radius, p.getPosition().getY()-radius, radius, radius); 
    }
    for(Monster m : monster){
      g.fillOval(m.getPosition().getX() - radius, m.getPosition().getY()-radius, radius, radius); 
    }
    g.setColor(Color.black);
    g.drawLine(0,snakeBody.getStartPoint()*2, snakeBody.getStartPoint()*2,snakeBody.getStartPoint()*2);
    g.drawLine(snakeBody.getStartPoint()*2, 0, snakeBody.getStartPoint()*2,snakeBody.getStartPoint()*2);
    g.drawLine(0,0,0,snakeBody.getStartPoint()*2);
    g.drawLine(0,0, snakeBody.getStartPoint()*2,0);
  }
  
  /*the bulk of the code, calls method to see if snake has died */
  public void run(){
    String[] scores = new String[3];
    Scanner scan = new Scanner(System.in);
    System.out.println("What is your name?");
    name = scan.next();
    System.out.println("Grid size?"); // gets grid size
    int grid = Integer.parseInt(scan.next());
    System.out.println("Difficulty? Easy, Medium Hard? (E/M/H)");
    String diff = scan.next().toLowerCase();
    int numMon;
    int foodTimer;
    int poisonTimer;
    int monSpeed;
    if(diff.toLowerCase().equals("e")){
      monSpeed = 4;
      numMon = 1;
      foodTimer = 5;
      poisonTimer = 15;
    } else if(diff.toLowerCase().equals("m")){
      monSpeed = 3;
      numMon = 2;
      foodTimer = 6;
      poisonTimer = 12;
    } else {
      monSpeed = 2;
      numMon = 3;
      foodTimer = 8;
      poisonTimer = 9;
    }
    snakeBody = new Body(grid);
    radius = snakeBody.dx;
    for(int i = 0; i < numMon; i++){
      monster.add(new Monster(snakeBody, diff.toLowerCase())); 
    }
    boolean again = true;
    try{
      Thread.sleep(1000);
    } catch(InterruptedException e){
      e.printStackTrace(); 
    }
    while(again){
      again = false;
      Boolean alive = true;
      long time = System.currentTimeMillis();
      while(alive){ // still going
        if(System.currentTimeMillis() - period > time){
          snakeBody.move(direction);
          for(Monster m : monster){
            if(time%monSpeed == 0){
              m.move(); 
            }
          }
          alive = snakeBody.checkWalls();
          time = System.currentTimeMillis();
          if (time%foodTimer == 0){
            food.add(new Food(snakeBody)); 
          }
          if(time%poisonTimer == 0){
            poison.add(new Poison(snakeBody));
          }
          alive = snakeBody.checkWalls() && snakeBody.checkDeath() && checkPoison() && checkMonster();
          eatFood(); // nom nom
          if((score+1)%6 == 0){
            score++;
            period*=0.75; 
          }
        }
        repaint();
        try{
          Thread.sleep(17);
        } catch(InterruptedException e){
          e.printStackTrace(); 
        }
      }
      System.out.println("You have died :("); // so sad
      System.out.println("Would you like to play again? (Y/N)");
      if(scan.next().toLowerCase().equals("y")){
        snakeBody = new Body(grid);
        food = new ArrayList<Food>();
        again = true;
      }
      try{
       fileScan= new Scanner(new File("snake/hi-scores.txt"));
       int i = 0;
       while(fileScan.hasNextLine()){
         scores[i] = fileScan.nextLine();
         i++;
       }
       String[] lineid;
       if(diff.equals("e")){
          lineid = scores[0].split(" ");
          if(Integer.parseInt(lineid[2]) < score){
            System.out.println("Congratulations! You have the new High Score!!");
            lineid[2] = Integer.toString(score);
            lineid[1] = name;
            scores[0] = lineid[0] + " " + lineid[1] + " " + lineid[2];
          }
       } else if (diff.equals("m")){
        lineid = scores[1].split(" ");
          if(Integer.parseInt(lineid[2]) < score){
            System.out.println("Congratulations! You have the new High Score!!");
            lineid[2] = Integer.toString(score);
            lineid[1] = name;
            scores[1] = lineid[0] + " " + lineid[1] + " " + lineid[2];
          } 
       } else {
        lineid = scores[2].split(" ");
          if(Integer.parseInt(lineid[2]) < score){
            System.out.println("Congratulations! You have the new High Score!!");
            lineid[2] = Integer.toString(score);
            lineid[1] = name;
            scores[2] = lineid[0] + " " + lineid[1] + " " + lineid[2];
          } 
       }
       PrintWriter writer = new PrintWriter("snake/hi-scores.txt", "UTF-8");
       writer.println(scores[0]);
       writer.println(scores[1]);
       writer.println(scores[2]);
       writer.close();
      } catch(IOException e){
         e.printStackTrace(); 
       }
    }
    System.out.println("Your score was " + score);
    System.out.println();
    System.out.println("High score board");
    System.out.println(scores[0]);
    System.out.println(scores[1]);
    System.out.println(scores[2]);
    try{
      Thread.sleep(10000);
    } catch(InterruptedException e){
      e.printStackTrace(); 
    }
    System.exit(0);
  }
  
  /*have we eaten poison?*/
  public boolean checkPoison(){
    for(Poison p : poison){
      for(Coordinate c : snakeBody.getPositions()){
        if(p.getPosition().equals(c)){
          return false; 
        }
      }
    }
    return true;
  }
  
  /*did the monster get us?*/
  public boolean checkMonster(){
    for(Monster m : monster){
      for(Coordinate c : snakeBody.getPositions()){
        if(m.getPosition().equals(c)){
          return false; 
        }
      }
    }
    return true;
  }
  
  /*nom nom*/
  public void eatFood(){
    for(int i = 0; i < food.size(); i++){
      if(food.get(i).getPosition().equals(snakeBody.getNodes().getFirst().getValue().getCoordinate())){
        score++;
        food.remove(i);
        snakeBody.addNode();
      }
    }
  }
  
  
  
  
  
  public void keyPressed(KeyEvent e){
    switch(e.getKeyCode()){
      //arrow keys
      case KeyEvent.VK_LEFT:
        if(direction != 1){
        direction = 3; 
      }
        break;
      case KeyEvent.VK_RIGHT:
        if(direction != 3){
        direction = 1;
      }
        break;
        case KeyEvent.VK_UP:
        if(direction != 0){
        direction = 2;
      }
        break;
        case KeyEvent.VK_DOWN:
        if(direction != 2){
        direction = 0;
      }
        break;
              case KeyEvent.VK_A:
        if(direction != 1){
        direction = 3; 
      }
        break;
      case KeyEvent.VK_D:
        if(direction != 3){
        direction = 1;
      }
        break;
        case KeyEvent.VK_W:
        if(direction != 0){
        direction = 2;
      }
        break;
        case KeyEvent.VK_S:
        if(direction != 2){
        direction = 0;
      }
        break;
      default:
        System.out.println("other");
        break;
    }
  }
  
  public void keyReleased(KeyEvent e){
    
  }
  
  public void keyTyped(KeyEvent e){
    
  }
    
    
  
}