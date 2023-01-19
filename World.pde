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

    void updatePos(){
        x += vel.x;
        y += vel.y;
        
    }

    void addWall(int x, int y, int w, int h){
        walls.add(new Wall(x, y, w, h));
    }

    void display(){
        image(level, -x, -y, level.width, level.height);
        for(int i = 0; i < walls.size(); i++){
            walls.get(i).display(-x, -y);
        }
        fill(255);

        text("World pos: " + x + ", " + y, 10, 30);
    }
}