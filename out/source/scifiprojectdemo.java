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

/*  Author/s: Kelvin Zhao
 *
 *  A rought demo of the top-down RPG that I discussed in my Sci-Fi project.
 *  Controls:
 *      WASD - move
 *      Space - interact
 *      Shift - walk
 *
 *  Debug mode can be entered by pressing "`"
 *
 *  TODO: Add documentation of the other files (including this one), and implement debug mode using the "`" key.
 */

HashMap<Character, Boolean> keys = new HashMap<Character, Boolean>();
boolean shiftPressed, debugMode;
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

    PImage level = loadImage("bigimage.jpeg");
    world = new World(level, 4300, 1000);
    world.addWall(3900, 900, 2000, 100);
    world.addWall(3900, 900, 100, 1000);
    world.addWall(3900, 1900, 2000, 100);
    world.addWall(5900, 900, 100, 500);
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
        //top side
        if(playerY >= curr.y - player.size && playerY <= curr.y && playerX >= curr.x && playerX <= curr.x + curr.w){
            if(vel.y > 0){
                vel.y = 0;
                world.y = curr.y - player.size - height / 2;
            }
        }
        //bottom side
        if(playerY >= curr.y + curr.h && playerY <= curr.y + curr.h + player.size && playerX >= curr.x && playerX <= curr.x + curr.w){
            if(vel.y < 0){
                vel.y = 0;
                world.y = curr.y + curr.h + player.size - height / 2;
            }
        }
    }

    world.updatePos();
    world.display();
    player.display();
    if(debugMode){
        text("velx: " + round(vel.x * 1000) / 1000.f + "       vely: " + round(vel.y * 1000) / 1000.f, 10, 10);
        text("World pos: " + world.x + ", " + world.y, 10, 30);
    }
    
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
    if(key == '`'){
        println("debug mode " + (!debugMode ? "off" : "on"));
        debugMode = !debugMode;
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
/* 
 * The World class is used to display the background imagery of the game and control the camera.
 * It also contains the walls of the level, which are stored with the ArrayList "walls".
 *
 * Integers "x" and "y" are used to store the position of the top-left corner of the window, relative to the level.
 * PImage "level" is used to store the background image. Eventually, background should be optimized to use smaller images.
 */

class World{
    int x, y;
    PImage level;
    ArrayList<Wall> walls = new ArrayList<Wall>();

    World(PImage level, int startX, int startY){
        this.level = level;
        x = startX;
        y = startY;
    }
    World(PImage level){
        this(level, 0, 0);
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
