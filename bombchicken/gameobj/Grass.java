package bombchicken.gameobj;

import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Floor.Type;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;
import javafx.scene.paint.Color;

public class Grass  extends GameObject{

	 public enum Type {

		 GRASS1,

	    }
	    private final Image img;
	    private Type type;

	    public Grass(int x, int y, int width, int height, Type type) {
	        super(x, y, width, height);
	        this.type = type;
	        img = setImage();
	    }

	    private Image setImage() {
	        switch (type) {
	      
	            case GRASS1:
	                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().grass1());
	                 
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
