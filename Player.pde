class Player{
    int rad = 50;
    final int x = 360;
    final int y = 240;
    Player(){
        
    }
  
    void display(){
        fill(255);
        strokeWeight(0);
        circle(x, y, rad);
    }
  
}