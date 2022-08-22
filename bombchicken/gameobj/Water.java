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
public class Water extends GameObject{

    private PopsAnimator animator;
    private PopsAnimator.State state;
    private boolean canUse; //復活點是空的

    public Water(int x, int y, int width, int height, PopsAnimator.State state) {
        super(x, y, width, height);
        animator = new PopsAnimator(state);
        this.state = state;
        canUse=true;
    }

    public void setCanUse(boolean canUse){
        this.canUse = canUse;
    }
    
    public boolean canUse(){
        return this.canUse;
    }
    
    public void move(){
        
    }
    
    public void setState(PopsAnimator.State state){
        this.state = state;
        animator.setState(state);
    }
    
    public PopsAnimator.State  getState(){
        return this.state;
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
