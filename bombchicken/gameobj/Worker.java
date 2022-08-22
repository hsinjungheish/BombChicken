package bombchicken.gameobj;

import bombchicken.animator.EnemyAnimator;
import bombchicken.animator.PopsAnimator;
import bombchicken.gameobj.Pressbtn.Type;

import static bombchicken.animator.EnemyAnimator.State.*;
import bombchicken.utils.Delay;
import bombchicken.utils.Global.Direction;
import java.awt.Graphics;

public class Worker extends GameObject{
	
	 	public enum Type {
	 		PIG,
	    	HAMMER,
	    	PHONE,
	    	SWAB,
	    	SAUCE,
	    	DEAD;
	    }
	
        private Type type;
	    private EnemyAnimator animator;
	    private EnemyAnimator.State state;
	    private Delay delay;
	    private int speed;//移動速度
	    private boolean needToRemove;
	    private boolean movable;
	    private int x;
	    private int y;
	    private int width;
	    private int height;
	
	  public Worker(int x, int y, int width, int height, Type type) {
		  super(x, y, width, height);
		  this.x=x;
		  this.y=y;
		  this.width=width;
		  this.height=height;
	        this.type = type;
	        this.speed=2;
	        needToRemove=false;
	        initState();
	    }
	  
	  private void initState() {
	        switch (type) {
	        case PIG:
                animator = new EnemyAnimator(PIGR);
                setState(PIGR);
                this.movable=true;
                break;
	            case HAMMER:
	                animator = new EnemyAnimator(HAMMER);
	                setState(HAMMER);
	                this.movable=false;
	                break;
	            case PHONE:
	                animator = new EnemyAnimator(PHONER);
	                setState(PHONER);
	                this.movable=true;
	                break;
	            case SWAB:
	                animator = new EnemyAnimator(SWAB);
	                setState(SWAB);
	                this.movable=false;
	                break;
	            case SAUCE:
	                animator = new EnemyAnimator(SAUCE);
	                setState(SAUCE);
	                this.movable=false;
	                break;
	            case DEAD:
	                animator = new EnemyAnimator(DEAD);
	                setState(DEAD);
	                this.movable=false;
	                break;
	        }
	    }
	  
	   public void setState(EnemyAnimator.State state) {
	        this.state = state;
	        animator.setState(state);
	        if(state==PIGR||state==PIGL) {
	        	delay = new Delay(animator.getState().getFPS()*2);
	        }else {
	        	delay = new Delay(animator.getState().getFPS());
	        }
	    }
	  
	  public EnemyAnimator.State getState(){
	        return this.state;
	    }

	    public void move() {
	        switch (state) {
	            case PHONER:      
	                translateX(speed);
	                this.x+=speed;
	                break;
	            case PHONEL:
	                 translateX(-speed);
	                 this.x-=speed;
	                break;
	            case PIGR:      
	                translateX(speed);
	                this.x+=speed;
	                break;
	            case PIGL:
	                 translateX(-speed);
	                 this.x-=speed;
	                break;
	        }
	    }
	    
	    public void setRemove(boolean remove){
	        needToRemove = remove;
	    }
	    
	    public boolean getRemove(){
	        return needToRemove;
	    }
	    
	    public int getX(){
	        return x;
	    }
	    public int getY(){
	        return y;
	    }
	    public int getWidth(){
	        return width;
	    }
	    public int getHeight(){
	        return height;
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
	    
	    public void update() {
	        animator.update();
	        delay.play();
	        if(this.state==DEAD ){
	        	if(delay.count()) {
	        	delay.stop();
	        	needToRemove=true;
	        	}
	        }else if(movable) {
		        move() ;       
		        if (delay.count()) { // 時間到
		            switch (state) {
		            case PHONER:      
		            	setState(PHONEL);
		                break;
		            case PHONEL:
		            	setState(PHONER);
		                break;
		            case PIGR:      
		            	setState(PIGL);
		                break;
		            case PIGL:
		            	setState(PIGR);
		                break;
		        }
	              delay.stop();
	           } 	
	        }else {
	        	delay.loop();
	        }
	        }
}
