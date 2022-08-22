package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import bombchicken.controllers.SceneController;
import bombchicken.utils.Delay;
import bombchicken.utils.Path;
import static bombchicken.animator.PopsAnimator.State.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Transition extends GameObject {  
    private PopsAnimator animator;
    private PopsAnimator.State state;
    
    public enum Type {
    		PIPE,
    		FLAP;
    }
    
    private Type type;
    private Delay delay;
    private int originX;
    private int originY;
    
    private void initState() {
        switch (type) {
            case PIPE:
                animator = new PopsAnimator(TRANSITIONOFF);
                setState(TRANSITIONOFF);
                break;
            case FLAP:
                animator = new PopsAnimator(FLAP);
                setState(FLAP);
                break;
        }
    }

    public Transition(int x, int y, int width, int height, Type type) {
        super(x, y, width, height);
        this.type=type;
        initState();
        originX =   this.collider().centerX();
        originY = this.collider().centerY();
//        delay = new Delay(animator.getState().getFPS());
//        delay.loop();
    }
    
    public void setState(PopsAnimator.State state) {
        this.state = state;
        animator.setState(state);
        delay = new Delay(animator.getState().getFPS());
    }
    
    public Type getType() {
        return this.type;
    }

    @Override
    public void paintComponent(Graphics g) {
        animator.paint(
                painter().left(), painter().top(),
                painter().right(), painter().bottom(), g);
    }
    

    @Override
    public void update() {
              animator.update();
              
              if (state == FLAPPING) { // 開始計時
                  delay.play();
                  this.collider().setCenter(originX, originY -200);
              }
              
              if (state == FLAPPING && delay.count()) { // 時間到
                  this.collider().setCenter(originX, originY);
                  setState(FLAP);
                  delay.stop();
              }
   }

}
