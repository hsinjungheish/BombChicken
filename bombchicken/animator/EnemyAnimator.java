package bombchicken.animator;

import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Chicken.Color;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Global.Direction;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import javax.swing.*;

public class EnemyAnimator {

	 public enum State {

	        //worker的動畫
	        HAMMER(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 4), 
	        PHONER(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10, 11, 12}, 8),
	        PHONEL(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10, 11, 12}, 8),
	        SWAB(new int[]{0, 1, 2,  3,  4, 5, 6, 7, 8,  9, 10, 11, 12, 13, 14,15,16,17,18}, 4),
	        SAUCE(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 8), 
	        
	      //pig的動畫
	        PIGR(new int[]{0, 1, 2, 3, 4, 5,0, 1, 2, 3, 4, 5}, 4), 
	        PIGL(new int[]{0, 1, 2, 3, 4, 5,0, 1, 2, 3, 4, 5}, 4), 
	        
	        //friedchicken動畫
	        LOOK(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9}, 4),
	        LOOKL(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9}, 4),
	        WALK(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10, 11, 12, 13, 14,15}, 4),
	        WALKL(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10, 11, 12, 13, 14,15}, 4),
	        PECK(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10, 11}, 4), 
	        PECKL(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10, 11}, 4), 
	        ROAST(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10,10,10,10,10}, 4), 
	        
	        //死亡動畫
	        DEAD(new int[]{0, 1, 2,  3, 4, 5, 6, 7, 8,  9, 10,11}, 2);
	        

	        private int[] arr; //要做的動作
	        private int speed; //動作間隔

	        State(int[] arr, int speed) {
	            this.arr = arr;
	            this.speed = speed;
	        }

	        public int getArrLenth() {
	            return this.arr.length;
	        }
	        
	        public int getSpeed() {
	            return this.speed;
	        }

	        public int getFPS() {
	            return speed * (arr.length - 1);
	        }
	    }
	 
	    private boolean towardRight;
	    private final Image img;
	    private final Image img2;
	    private final Image img3;
	    private final Image img4;
	    private final Image img5;
	    private final Image img6;
	    private final Image img7;
	    private final Image img8;
	    private final Image img9;
	    private final Image img10;
	    private final Image img11;
	    private final Image img12;
	    private final Image img13;
	    private final Image img14;
	    private final Image img15;
	    
	    private final Delay delay;
	    private int count;
	    private State state;
	    
	    public EnemyAnimator(State state) {
	 
	    	  img = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().worker().hammer());
	    	  img2= SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().worker().phoneR());
	    	  img3 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().worker().phoneL());
	    	  img4 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().worker().swab());
	    	  img5 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().worker().sauce());
	    	  img6 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().dead().dead());
	    	  img7 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().pig().flappyPigRight());
	    	  img8 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().pig().flappyPigLeft());
	    	  img9 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().look());
	    	  img10 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().lookL());
	    	  img11 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().walk());
	    	  img12 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().walkL());
	    	  img13= SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().peck());
	    	  img14 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().peckL());
	    	  img15 = SceneController.instance().irc().tryGetImage(
	                  new Path().img().actors().friedChicken().roast());
	    
	    	  delay = new Delay(0);
	          delay.loop();
	          count = 0;
	          setState(state);
	    }
	 
	    public final void setState(State state) {
	        this.state = state;
	        this.delay.setLimit(state.speed);

	    }

	    public State getState() {
	        return this.state;
	    }
	    
	    public void setTowardRight(boolean towardRight) {
	        this.towardRight = towardRight;
	    }

	    public int count() {
	        return this.count;
	    }
	    
	    public void paint(int left, int top, int right, int bottom, Graphics g) {
	        Image tmp = null;
	        int width;
	        switch (state) {
	        case HAMMER:
	        	tmp=img;
	        	width=77;
	        	 if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	 	        g.drawImage(tmp,
	 	                left, top,
	 	                right, bottom,
	 	               width* state.arr[count], 0,
	 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case PHONER:
	        	tmp=img2;
	        	width=74;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case PHONEL:
	        	tmp=img3;
	        	width=74;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width*12-width* state.arr[count], 0,
		 	              width*13-width* state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case SWAB:
	        	tmp=img4;
	        	width=79;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case SAUCE:
	        	tmp=img5;
	        	 if (count >= state.arr.length) {
		 	            count = 0;
		 	        }
		 	        g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               Global.UNIT_X* state.arr[count], 0,
		 	              Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case DEAD:
	        	tmp=img6;
	        	width=55;
	        	 if (count >= state.arr.length) {
		 	            count = 0;
		 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case PIGR:
	        	tmp=img7;
	        	width=64;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case PIGL:
	        	tmp=img8;
	        	width=64;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case LOOK:
	        	tmp=img9;
	        	width=63;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case LOOKL:
	        	tmp=img10;
	        	width=63;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width*9-width* state.arr[count], 0,
		 	              width*10-width* state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case WALK:
	        	tmp=img11;
	        	width=63;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case WALKL:
	        	tmp=img12;
	        	width=63;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width*15-width* state.arr[count], 0,
		 	              width*16-width* state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case PECK:
	        	tmp=img13;
	        	width=64;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case PECKL:
	        	tmp=img14;
	        	width=62;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width*11-width* state.arr[count], 0,
		 	              width*12-width* state.arr[count], Global.UNIT_Y, null);
	        	break;
	        case ROAST:
	        	tmp=img15;
	        	width=75;
	        	if (count >= state.arr.length) {
	 	            count = 0;
	 	        }
	        	  g.drawImage(tmp,
		 	                left, top,
		 	                right, bottom,
		 	               width* state.arr[count], 0,
		 	              width+ width * state.arr[count], Global.UNIT_Y, null);
	        	break;
	        }
//	        //一般動畫
//	        if (count >= state.arr.length) {
//	            count = 0;
//	        }
//
//	        g.drawImage(tmp,
//	                left, top,
//	                right, bottom,
//	                Global.UNIT_X * state.arr[count], 0,
//	                Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
	    }
	    public void update() {
	        if (delay.count()) {
	            count = ++count % state.arr.length; //讓動畫照特定順序循環播放
	        }
	    }
	 
}
