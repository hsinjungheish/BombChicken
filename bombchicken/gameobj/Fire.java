package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import static bombchicken.animator.PopsAnimator.State.*;
import bombchicken.utils.Delay;
import bombchicken.utils.Global.Direction;
import java.awt.Graphics;

public class Fire extends GameObject {

    private PopsAnimator animator;
    private PopsAnimator.State state;
    private Delay downDelay; //下去時間
    private Delay upDelay;//上來時間
    private int speed;//火球飛行速度
    private boolean needToRemove;

    public Fire(int x, int y, int width, int height, PopsAnimator.State state) {
        super(x, y, width, height * 3 / 4);
        animator = new PopsAnimator(state);
        this.state = state;
        this.speed=8;

    }
    
    public PopsAnimator.State getState(){
        return this.state;
    }

    public void move() {
        switch (state) {
            case FIREFLYRIGHT:      
                translateX(speed);
                break;
            case FIREFLYLEFT:
                 translateX(-speed);
                break;
        }
    }
    
    public void setRemove(boolean remove){
        needToRemove = remove;
    }
    
    public boolean getRemove(){
        return needToRemove;
    }
            
    public void setSpeed(int speed){
        this.speed = speed;
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
        move() ;
    }

}
