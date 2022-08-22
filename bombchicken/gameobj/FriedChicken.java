package bombchicken.gameobj;

import bombchicken.animator.EnemyAnimator;
import bombchicken.animator.PopsAnimator;
import bombchicken.gameobj.Pressbtn.Type;

import static bombchicken.animator.EnemyAnimator.State.*;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Global.Direction;
import java.awt.Graphics;

public class FriedChicken extends GameObject{
	
	 	public enum Type {
		 	WALK,
	    	ROAST;
	    }
	
        private Type type;
	    private EnemyAnimator animator;
	    private EnemyAnimator.State state;
	    private Delay delay;
	    private boolean isRoom;
	    private int speed;//移動速度
	    private boolean needToRemove;
	    private boolean movable;
	    private int x;
	    private int y;
	    private int width;
	    private int height;
	
	  public FriedChicken(int x, int y, int width, int height, Type type) {
		  super(x, y, width, height);
		  this.x=x;
		  this.y=y;
		  this.width=width;
		  this.height=height;
	        this.type = type;
	        this.speed=2;
	        isRoom=false;
	        needToRemove=false;
	        initState();
	    }
	  
	  private void initState() {
	        switch (type) {
	            case WALK:
	                animator = new EnemyAnimator(WALK);
	                setState(WALK);
	                this.movable=true;
	                break;
	            case ROAST:
	                animator = new EnemyAnimator(ROAST);
	                setState(ROAST);
	                this.movable=false;
	                break;
	        }
	    }
	  
	   public void setState(EnemyAnimator.State state) {
	        this.state = state;
	        animator.setState(state);
	        if(state==WALK||state==WALKL) {
	        	if(isRoom) {
	        		delay = new Delay(animator.getState().getFPS()*Global.random(1,3));
	        	}else {
	        		delay = new Delay(animator.getState().getFPS()*2);
	        	}
	        	
	        }else {
	        	delay = new Delay(animator.getState().getFPS());
	        }
	        
	    }
	  
	  public EnemyAnimator.State getState(){
	        return this.state;
	    }

	    public void move() {
	        switch (state) {
	            case WALK:      
	                translateX(speed);
	                this.x+=speed;
	                break;
	            case WALKL:
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
	    
	    public void setRoom(boolean isRoom){
	        this.isRoom = isRoom;
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
	        if(this.state==ROAST ){
	        	if(delay.count()) {
	        	delay.stop();
	        	needToRemove=true;
	        	}
	        }else if(movable) {
		        move() ;       
		        if (delay.count()) { // 時間到
		            switch (state) {
		            case WALK:      
		            	setState(LOOK);
		            	movable=false;
		                break;
		            case WALKL:
		            	setState(LOOKL);
		            	movable=false;
		                break;
		        }
	              delay.stop();
	           } 	
	        }else {
	        	if (delay.count()) { // 時間到
		            switch (state) {
		            case LOOK:      
		            	setState(PECK);
		                break;
		            case PECK:
		            	setState(WALKL);
		            	movable=true;
		                break;
		            case LOOKL:      
		            	setState(PECKL);
		                break;
		            case PECKL:
		            	setState(WALK);
		            	movable=true;
		                break;
		        }
	        	delay.loop();
	        }
	        }
	    }
}
