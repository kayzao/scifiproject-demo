class World{
    int x, y;
    PImage level;
    ArrayList<Wall> walls = new ArrayList<Wall>();
    World(PImage level){
        this.level = level;
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