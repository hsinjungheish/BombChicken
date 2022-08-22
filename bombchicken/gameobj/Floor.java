/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.gameobj;

import bombchicken.controllers.SceneController;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;
import javafx.scene.paint.Color;

public class Floor extends GameObject {

    public enum Type {

        STEEL,
        WALL1,
        WALL2,
        BRICK,
        FLOOR1,
        FLOOR2,
        YELLOWSTEEL,
        A,
        BIG,
        STONE,
    	CEILING,
    	DOOR,
    	WALLL,
    	WALL3,
    	WALL4,
    	WALL5,
    	WALL6,
    	WALL7,
    	CORNER1,
    	CORNER2,
    	CORNER3,
    	CORNER4,
    	CORNER8,
    	WALLBRICKABLE1,
    	WALLSTONE,
    	WALL8,
    	INVISITILE1,
    	WALLC,
    	TILE2,
    	TILE3;
    }
    
    private final Image img;
    private Type type;

    public Floor(int x, int y, int width, int height, Type type) {
        super(x, y, width, height);
        this.type = type;
        img = setImage();
    }

    private Image setImage() {
        switch (type) {
            case STEEL:
//                this.collider().setBottom(this.collider().bottom() - 20);
//                this.painter().setBottom(this.collider().bottom());
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().steel());
            case WALL1:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall1());
            case WALL2:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall2());
            case BRICK:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().brick());
            case FLOOR1:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().floor1());
            case FLOOR2:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().floor2());
            case YELLOWSTEEL:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().yellowSteel());
            case A:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().a());
            case BIG:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().big());
            case STONE:
                 return SceneController.instance().irc().tryGetImage(new Path().img().objs().stone());
            case CEILING:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().ceiling());
            case DOOR:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().door());
            case WALLL:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wallL());
            case WALL3:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall3());
            case WALL4:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall4());
            case WALL5:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall5());
            case WALL6:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall6());
            case WALL7:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall7());
            case CORNER1:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().corner1());
            case CORNER2:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().corner2());
            case CORNER3:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().corner3());
            case CORNER4:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().corner4());
            case CORNER8:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().corner8());
            case WALLBRICKABLE1:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wallBrickable1());
            case WALLSTONE:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wallstone());
            case WALL8:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wall8());
            case INVISITILE1:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().invisitile1());
            case WALLC:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().wallC());
            case TILE2:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().tile2());
            case TILE3:
                return SceneController.instance().irc().tryGetImage(new Path().img().objs().tile3());
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
