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

final float friction = 0.7;

void setup(){
    keys.put('w', false);
    keys.put('a', false);
    keys.put('s', false);
    keys.put('d', false);
    keys.put('W', false);
    keys.put('A', false);
    keys.put('S', false);
    keys.put('D', false);
    keys.put(' ', false);

    size(720, 600);
    vel = new PVector(0, 0);

    PImage level = loadImage("bigimage.jpeg");
    world = new World(level, 4300, 1000);
    world.addWall(3900, 900, 2000, 100);
    world.addWall(3900, 900, 100, 1000);
    world.addWall(3900, 1900, 2000, 100);
    world.addWall(5900, 900, 100, 500);
    player = new Player();
}

void draw(){
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
        if(vel.mag() < 0.1) vel.set(0, 0);
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
        text("velx: " + round(vel.x * 1000) / 1000. + "       vely: " + round(vel.y * 1000) / 1000., 10, 10);
        text("World pos: " + world.x + ", " + world.y, 10, 30);
    }
    
}
void keyPressed(){
    if(keys.containsKey(key)){
        keys.put(key, true);
    }
    
}
void keyReleased(){
    if(keys.containsKey(key)){
        keys.put(key, false);
    }
    if(key == '`'){
        println("debug mode " + (!debugMode ? "off" : "on"));
        debugMode = !debugMode;
    }
}
