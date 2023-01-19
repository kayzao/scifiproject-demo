class Player{
    int size = 25;
    final int x = width / 2;
    final int y = height /2;
    Player(){
        
    }
  
    void display(){
        fill(255);
        strokeWeight(0);
        circle(x, y, size * 2);
    }
  
}