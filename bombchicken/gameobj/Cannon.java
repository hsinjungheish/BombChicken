/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import bombchicken.controllers.SceneController;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Global.Direction;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author User
 */
public class Cannon extends GameObject {

    private Direction dir;
    private Image img;
    private Delay shootDelay; //發射火球頻率
    private boolean canShoot;
    private int delayTime; //發射火球間隔時間
    private boolean auto; //自動發射
    private Delay openDelay; //創建多久後開始發射

    public Cannon(int x, int y, int width, int height, Direction dir) { //符合條件才發射
        super(x, y, width, height);
        this.dir = dir;
        setImgByDirection();
        delayTime = 1;
        shootDelay = new Delay(delayTime);
        openDelay = new Delay(180);
        openDelay.play();
        canShoot = false;
        auto = false;

    }

    public Cannon(int x, int y, int width, int height, Direction dir, boolean auto) { //創建就發射
        super(x, y, width, height);
        this.dir = dir;
        setImgByDirection();
        delayTime = 120;
        shootDelay = new Delay(delayTime);
        openDelay = new Delay(180);
        openDelay.play();
        canShoot = false;
        this.auto = auto;
    }

    public void setOpenDelay(int times) {
        openDelay = new Delay(times);
        openDelay.play();
    }
       public void setShootDelay(int delayTime) {
        shootDelay = new Delay(delayTime);
    }

    public void setImgByDirection() {
        if (dir == Global.Direction.RIGHT) {
            img = SceneController.instance().irc().tryGetImage(
                    new Path().img().objs().cannonRight());
        } else {
            img = SceneController.instance().irc().tryGetImage(
                    new Path().img().objs().cannonLeft());
        }

    }

    public void start() {
        shootDelay.play();
    }



    public Direction direction() {
        return this.dir;
    }

    public boolean canShoot() {
        return this.canShoot;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img,
                painter().left(), painter().top(),
                painter().right(), painter().bottom(),
                0, 0,
                35, 21, null);
    }

    @Override
    public void update() {
        if (openDelay.count()) {
            if (auto) {
                shootDelay.loop();
            }
//            } else {
//                shootDelay.play();
//            }
//            canShoot = true;
        }
        if (shootDelay.count()) {
            canShoot = true;
        } else {
            canShoot = false;
        }

    }

}
