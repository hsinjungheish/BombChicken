package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import bombchicken.controllers.SceneController;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Path;

import static bombchicken.animator.PopsAnimator.State.*;

import java.awt.Graphics;
import java.awt.Image;
import javafx.scene.paint.Color;

public class Pressbtn extends GameObject {

    public enum Type {
    	PRESSBTNB,
    	PRESSBTNR,
    	PRESSBTNU,
    	PRESSBTNL;
    }
    
    private Type type;
    private Delay delay;
    private PopsAnimator animator;
    private PopsAnimator.State state;
    private boolean isPressed;
    private boolean hasPressed;

    public Pressbtn(int x, int y, int width, int height, Type type) {
        super(x, y, width, height);
        this.type = type;
        this.isPressed=false;
        this.hasPressed=false;
        initState();
    }
    
    private void initState() {
        switch (type) {
            case PRESSBTNB:
                animator = new PopsAnimator(PRESSBTNBSTILL);
                setState(PRESSBTNBSTILL);
                break;
            case PRESSBTNR:
                animator = new PopsAnimator(PRESSBTNRSTILL);
                setState(PRESSBTNRSTILL);
                break;
            case PRESSBTNU:
                animator = new PopsAnimator(PRESSBTNUSTILL);
                setState(PRESSBTNUSTILL);
                break;
            case PRESSBTNL:
                animator = new PopsAnimator(PRESSBTNLSTILL);
                setState(PRESSBTNLSTILL);
                break;
        }
    }
    
    public void setState(PopsAnimator.State state) {
        this.state = state;
        animator.setState(state);
        delay = new Delay(animator.getState().getFPS());
    }

    public Type getType() {
        return this.type;
    }
    
    public void animatorStop () {
    	  if(isPressed) {
  		   	switch(this.type) {
  		   		case PRESSBTNB:
  		   		setState(PRESSBTNBPRESS);
              break;
  		   		case PRESSBTNR:
              setState(PRESSBTNRPRESS);
              break;
  		   		case PRESSBTNU:
              setState(PRESSBTNUPRESS);
              break;
  		   		case PRESSBTNL:
              setState(PRESSBTNLPRESS);	
              break;
  		   	}
		}else {
			this.hasPressed=false;
			switch(this.type) {
  		  case PRESSBTNB:
                setState(PRESSBTNBSTILL);
                break;
            case PRESSBTNR:
                setState(PRESSBTNRSTILL);
                break;
            case PRESSBTNU:
                setState(PRESSBTNUSTILL);
                break;
            case PRESSBTNL:
                setState(PRESSBTNLSTILL);
                break;
    	   }
		}
    }
    
    public void animatorStart () {
    	if(isPressed) {
    		this.hasPressed=true;
    		this.animator.setTowardRight(false);
    		switch(this.type) {
  		  case PRESSBTNB:
                setState(PRESSBTNB);
                break;
            case PRESSBTNR:
                setState(PRESSBTNR);
                break;
            case PRESSBTNU:
                setState(PRESSBTNU);
                break;
            case PRESSBTNL:
                setState(PRESSBTNL);
                break;
  		}
    	}else if((!isPressed)&&hasPressed){
    		this.animator.setTowardRight(true);//反著播
    		switch(this.type) {
  		  case PRESSBTNB:
                setState(PRESSBTNB);
                break;
            case PRESSBTNR:
                setState(PRESSBTNR);
                break;
            case PRESSBTNU:
                setState(PRESSBTNU);
                break;
            case PRESSBTNL:
                setState(PRESSBTNL);
                break;
  		}
    	}
    }
    
    public void isPressed (boolean isPressed) {
    	if(this.isPressed!=isPressed) {
    		this.isPressed=isPressed;
    	animatorStart();
    	}
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
           delay.play();
//           System.out.print(isPressed+"      ");
// 			System.out.println(hasPressed);
           if (delay.count()) { // 時間到
        	 animatorStop();
              delay.stop();
           }
    }

}
