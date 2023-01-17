class Wall{
    int x, y, w, h;
    color fill;
    Wall(int x, int y, int w, int h){
        this(x, y, w, h, color(150));
    }
    Wall(int x, int y, int w, int h, color c){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        fill = c;
    }
    void display(){
        strokeWeight(0);
        fill(fill);
        rect(x, y, w, h);
    }
}