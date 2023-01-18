HashMap<Character, Boolean> keys = new HashMap<Character, Boolean>();
Player player = new Player();

World world; 

PVector vel;

final float accel = 20;
final float maxVel = 30;

boolean interact = false;

final float friction = 0.7;

void setup(){
    keys.put('w', false);
    keys.put('a', false);
    keys.put('s', false);
    keys.put('d', false);
    keys.put(' ', false);
    size(720, 600);
    vel = new PVector(0, 0);

    PImage level = loadImage("bigimage.jpeg");
    world = new World(level);
    world.addWall(100, 100, 2000, 100);
    world.addWall(100, 100, 100, 1000);
    world.addWall(100, 1000, 2000, 100);
    world.addWall(2000, 100, 100, 500);
}

void draw(){
    background(0);
    if(!interact){
        if(keys.get('w')){
        vel.y -= accel;
        }
        if(keys.get('a')){
            vel.x -= accel;
        }
        if(keys.get('s')){
            vel.y += accel;
        }
        if(keys.get('d')){
            vel.x += accel;
        }
        vel.limit(maxVel);
        vel.mult(friction);
    }
    
    
    world.updatePos();
    world.display();
    player.display();
    for(int i = 0; i < world.walls.size(); i++){
        Wall curr = world.walls.get(i);
        //left side
        
        if(curr.x > player.x - player.rad && curr.x < player.x + player.rad && curr.y > player.y - player.rad && curr.y < player.y + player.rad){
            if(curr.x > player.x){
                world.x = curr.x - player.rad;
            }else{
                world.x = curr.x + player.rad;
            }
            vel.x = 0;
        }
    }

    text("velx: " + round(vel.x * 100) / 100 + "             vely: " + round(vel.y * 100) / 100, 10, 10);
}
void keyPressed(){
    keys.put(key, true);
}
void keyReleased(){
    keys.put(key, false);
}
