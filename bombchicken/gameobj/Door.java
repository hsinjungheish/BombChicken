package bombchicken.gameobj;

import bombchicken.controllers.SceneController;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;
import javafx.scene.paint.Color;

public class Door extends GameObject {

    public enum Type {
    	BULKHEADSWITCH1,
    	BULKHEADSWITCH2,
    }
    
    private final Image img;
    private Type type;
    private Delay shortenDelay =new Delay(55);
    private boolean isOpen=false; 
    private boolean start; 
    private int width=160;
    private int moved=0;
    
    public Door(int x, int y, int width, int height, Type type) {
        super(x, y, width, height);
        this.type = type;
        img = setImage();
    }

    private Image setImage() {
        switch (type) {
            case BULKHEADSWITCH1:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().bulkheadSwitch1());
            case BULKHEADSWITCH2:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().bulkHeadSwitch2());
           
        }
        return null;
    }

    public Type getType() {
        return this.type;
    }

    public void move() {
    if (start&&(!isOpen)) {
        	switch (type) {
            case BULKHEADSWITCH1:
            		if (shortenDelay.getCount() != 0&&moved<=width) {
                        translateX(-40);
                        moved+=40;
                    }
            		if(moved>=width) {
            			isOpen=true;
            		}
            	break;
            case BULKHEADSWITCH2:
            	if (shortenDelay.getCount() != 0) {
                    translateY(-2);
                }
            	break;
        }
            
        }
     
    	
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        int n = 0;
        g.drawImage(img,
                this.painter().left() - n, this.painter().top() - n,
                this.painter().right() + n, this.painter().bottom() + n,
                0, 0,
                this.painter().width(), this.painter().height(), null);

    }

    @Override
    public void update() {
    	start=true;
    	move(); 
    	shortenDelay.play();
           if (shortenDelay.count()) {
               shortenDelay.stop();
               isOpen=true;
           }
    }

}
