/* autogenerated by Processing revision 1282 on 2023-01-19 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class scifiprojectdemo extends PApplet {

HashMap<Character, Boolean> keys = new HashMap<Character, Boolean>();
boolean shuftPressed;
Player player;

World world; 

PVector vel;

final float accel = 7;
final float maxVel = 30;

boolean interact = false;

final float friction = 0.7f;

 public void setup(){
    keys.put('w', false);
    keys.put('a', false);
    keys.put('s', false);
    keys.put('d', false);
    keys.put('W', false);
    keys.put('A', false);
    keys.put('S', false);
    keys.put('D', false);
    keys.put(' ', false);

    /* size commented out by preprocessor */;
    vel = new PVector(0, 0);

    PImage level = loadImage("test.jpg");
    world = new World(level);
    world.addWall(100, 100, 2000, 100);
    world.addWall(100, 100, 100, 1000);
    world.addWall(100, 1000, 2000, 100);
    world.addWall(2000, 100, 100, 500);

    player = new Player();
}

 public void draw(){
    background(0);
    if(!interact){
        if(keys.get('W')){
            vel.y -= accel / 3;
        } else if(keys.get('w')){
            vel.y -= accel;
        }
        if(keys.get('A')){
            vel.x -= accel / 3;
        } else if(keys.get('a')){
            vel.x -= accel;
        }
        if(keys.get('S')){
            vel.y += accel / 3;
        } else if(keys.get('s')){
            vel.y += accel;
        }
        if(keys.get('D')){
            vel.x += accel / 3;
        } else if(keys.get('d')){
            vel.x += accel;
        }
        vel.mult(friction);
        if(vel.mag() < 0.1f) vel.set(0, 0);
    }
    
    for(int i = 0; i < world.walls.size(); i++){
        Wall curr = world.walls.get(i);
        text(curr.x + " " + curr.y, curr.x - world.x, curr.y - world.y);
        int playerX = world.x + width / 2 + round(vel.x);
        int playerY = world.y + height / 2 + round(vel.y);
        
        //left side
        if(playerX >= curr.x - player.size && playerX <= curr.x && playerY >= curr.y && playerY <= curr.y + curr.h){
            if(vel.x > 0){
                vel.x = 0;
                world.x = curr.x - player.size - width / 2;
            }
        }
        //right side
        if(playerX >= curr.x + curr.w && playerX <= curr.x + curr.w + player.size && playerY >= curr.y && playerY <= curr.y + curr.h){
            if(vel.x < 0){
                vel.x = 0;
                world.x = curr.x + curr.w + player.size - width / 2;
            }
        }
    }

    world.updatePos();
    world.display();
    player.display();
    
    text("velx: " + round(vel.x * 1000) / 1000.f + "       vely: " + round(vel.y * 1000) / 1000.f, 10, 10);
}
 public void keyPressed(){
    if(keys.containsKey(key)){
        keys.put(key, true);
    }
    
}
 public void keyReleased(){
    if(keys.containsKey(key)){
        keys.put(key, false);
    }
}
class Player{
    int size = 25;
    final int x = width / 2;
    final int y = height /2;
    Player(){
        
    }
  
     public void display(){
        fill(255);
        strokeWeight(0);
        circle(x, y, size * 2);
    }
  
}
class Wall{
    int x, y, w, h;
    int fill;
    Wall(int x, int y, int w, int h){
        this(x, y, w, h, color(150));
    }
    Wall(int x, int y, int w, int h, int c){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        fill = c;
    }
     public void display(int worldx, int worldy){
        strokeWeight(0);
        fill(fill);
        rect(x + worldx, y + worldy, w, h);
    }
}
class World{
    int x, y;
    PImage level;
    ArrayList<Wall> walls = new ArrayList<Wall>();
    World(PImage level){
        this.level = level;
    }
     public void updatePos(){
        x += vel.x;
        y += vel.y;
        
    }
     public void addWall(int x, int y, int w, int h){
        walls.add(new Wall(x, y, w, h));
    }
     public void display(){
        image(level, -x, -y, level.width, level.height);
        for(int i = 0; i < walls.size(); i++){
            walls.get(i).display(-x, -y);
        }
        fill(255);
        text("World pos: " + x + ", " + y, 10, 30);
    }
}


  public void settings() { size(720, 600); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "scifiprojectdemo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
