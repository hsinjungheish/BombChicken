/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import java.awt.Graphics;

/**
 *
 * @author User
 */
public class Elevator extends GameObject {

    private PopsAnimator animator;
    private PopsAnimator.State state;
    private Delay elevatorDelay;//搭電梯動畫
    private Delay disappearDelay; //雞可以消失
    private boolean inElevator; //電梯動畫播放到了雞進電梯時

    public Elevator(int x, int y, int width, int height) {
        super(x, y, width, height);
        animator = new PopsAnimator(PopsAnimator.State.ELEVATORSTOP);
        this.state = PopsAnimator.State.ELEVATORSTOP;
        this.collider().setLeft(this.collider().left() + 42);
        this.collider().setRight(this.collider().right() - 42);
        elevatorDelay = new Delay(PopsAnimator.State.ELEVATORWORK.getFPS());
        elevatorDelay.loop();
        disappearDelay = new Delay(60);
        disappearDelay.loop();
        inElevator = false;
    }

    public void setState(PopsAnimator.State state) {
        this.state = state;
        animator.setState(state);
    }

    public PopsAnimator.State getState() {
        return this.state;
    }

    public boolean inElevator() {
        return inElevator;
    }
    
    public void setinElevator(boolean inElevator){
        this.inElevator = inElevator;
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
        if (state == PopsAnimator.State.ELEVATORWORK) {
            elevatorDelay.play();
            disappearDelay.play();
        }
        if (state == PopsAnimator.State.ELEVATORWORK && disappearDelay.count()) {
            inElevator = true;
        }
        if (elevatorDelay.count()) {
            setState(PopsAnimator.State.ELEVATORSTOP);
            disappearDelay.stop();
            elevatorDelay.stop();
            inElevator = false;
        }

    }
}
