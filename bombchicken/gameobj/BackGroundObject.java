package bombchicken.gameobj;

import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Floor.Type;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;
import javafx.scene.paint.Color;

public class BackGroundObject  extends GameObject{

	 public enum Type {

		 BOXSTACK,
		 BOXSTACK1,
		 BOXSTACK2,
		 BOXESPRINT1,
		 BOXESPRINT2,
		 COOPEGGS,
		 COOPGROUND,
		 COOPGROUNDL,
		 COOPD,
		 LAMP,
		 POT,
		 EGGS,
		 EGGS1,
		 EGGS2,
		 FACTORYWALL1,
		 FACTORYWALL2,
		 FAN1,
		 BIGBOXES,
		 STATUE1,
		 BOXSTACK5,
		 LIGHT1,
		 BARRELSTACK1,
		 BARREL1,
		 STORAGESIGN,
		 SPIDERWEB,
		 SPIDERWEB2,
		 SPIDERWEB3,
		 BILLBOARD1,
		 BILLBOARD2,
		 TOILET,
		 TOILET2,
		 TOILET3;
	    }
	    private final Image img;
	    private Type type;

	    public BackGroundObject(int x, int y, int width, int height, Type type) {
	        super(x, y, width, height);
	        this.type = type;
	        img = setImage();
	    }

	    private Image setImage() {
	        switch (type) {
	            case BOXSTACK:
	                return SceneController.instance().irc().tryGetImage(new Path().img().objs().boxStack());
	            case BOXSTACK1:
	                return SceneController.instance().irc().tryGetImage(new Path().img().objs().boxesStack1());
	            case BOXSTACK2:
	                return SceneController.instance().irc().tryGetImage(new Path().img().objs().boxStack2());
	            case BOXESPRINT1:
	                return SceneController.instance().irc().tryGetImage(new Path().img().objs().boxesPrint1());
	            case BOXESPRINT2:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().boxesPrint2());
	            case COOPEGGS:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().coopEggs());
	            case COOPGROUND:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().coopGround());
	            case COOPGROUNDL:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().coopGroundL());
	            case COOPD:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().coopD());
	            case LAMP:
	                return SceneController.instance().irc().tryGetImage(new Path().img().objs().lamp());
	            case EGGS:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().eggs());
	            case EGGS1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().eggs1());
	            case EGGS2:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().eggs2());
	            case FACTORYWALL1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().factoryWall1());
	            case FACTORYWALL2:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().factoryWall2());
	            case FAN1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().fan1());
	            case POT:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().pot());
	            case BIGBOXES:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().bigboxes());
	            case STATUE1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().statue1());
	            case BOXSTACK5:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().boxStack5());
	            case LIGHT1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().light1());
	            case BARRELSTACK1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().barrelStack1());
	            case BARREL1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().barrel1());
	            case STORAGESIGN:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().storageSign());
	            case SPIDERWEB:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().spiderweb());
	            case SPIDERWEB2:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().spiderweb2());
	            case SPIDERWEB3:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().spiderweb3());
	            case BILLBOARD1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().billboard1());
	            case BILLBOARD2:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().billboard2());
	            case TOILET:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().toilet());
	            case TOILET2:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().toilet2());
	            case TOILET3:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().toilet3());
	                 
	                 
	        }
	        return null;
	    }

	    public Type getType() {
	        return this.type;
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

	    }
}
