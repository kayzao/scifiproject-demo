HashMap<Character, Boolean> keys = new HashMap<Character, Boolean>();
boolean shuftPressed;
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

    PImage level = loadImage("test.jpg");
    world = new World(level);
    world.addWall(100, 100, 2000, 100);
    world.addWall(100, 100, 100, 1000);
    world.addWall(100, 1000, 2000, 100);
    world.addWall(2000, 100, 100, 500);

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
    }

    world.updatePos();
    world.display();
    player.display();
    
    text("velx: " + round(vel.x * 1000) / 1000. + "       vely: " + round(vel.y * 1000) / 1000., 10, 10);
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
}
