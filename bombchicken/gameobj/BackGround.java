/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.gameobj;

import bombchicken.controllers.SceneController;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author User
 */
public class BackGround extends GameObject {

    public enum Type {
        FACTORY,
        FACTORY2,
     BLACK;
    }
    private  Image img;
    private Type type;

    public BackGround(int x, int y, int width, int height, Type type) {
        super(x, y, width, height);
        this.type = type;
        setImage();

    }

    private Image setImage() {
        switch (type) {
            case FACTORY:
               img  =  SceneController.instance().irc().tryGetImage(new Path().img().backgrounds().factory());
              break;
            case BLACK:
                img  =  SceneController.instance().irc().tryGetImage(new Path().img().backgrounds().black());
               break;
            
        }
        super.setPosition(this.painter().centerX()+ this.painter().width()/2, 
                          this.painter().centerY()+this.painter().height()/2);
        return null;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img,
                this.painter().left(), this.painter().top(),
                this.painter().right(), this.painter().bottom(),
                0, 0,
                this.painter().width(), this.painter().height(), null);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
