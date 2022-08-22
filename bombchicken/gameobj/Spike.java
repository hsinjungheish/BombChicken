/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import java.awt.Graphics;

/**
 *
 * @author User
 */
public class Spike extends GameObject {  //尖刺

    private PopsAnimator animator;
    private PopsAnimator.State state;

    public Spike(int x, int y, int width, int height) {
        super(x, y, width, height);
        animator = new PopsAnimator(PopsAnimator.State.SHINE);
        setState(PopsAnimator.State.SHINE);
        setCollider();

    }
        private void setCollider(){ //讓尖刺的碰撞框小點
        int n = 10;
        this.collider().setLeft(this.painter().left() + n);
        this.collider().setRight(this.painter().right() - n);
        this.collider().setTop(this.painter().top() + n);
        this.collider().setBottom(this.painter().bottom() - n);
    }

    public void setState(PopsAnimator.State state) {
        this.state = state;
        animator.setState(state);
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
   }

}
