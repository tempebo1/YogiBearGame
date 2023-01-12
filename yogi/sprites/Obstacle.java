package yogi.sprites;

import java.awt.*;
import java.util.Random;

public class Obstacle extends Sprite {
    public Obstacle(int x, int y) {
        super(x, y,null);
        Random rand = new Random();
        setImage(rand.nextInt(2)==0?"src/resources/rock.png":"src/resources/tree.png");
 
    }

    public Rectangle getBound(int i) {
        if (i == 0) return new Rectangle(this.x+1000, this.y, this.width+45, this.height+45);
        return null;
    }
}
