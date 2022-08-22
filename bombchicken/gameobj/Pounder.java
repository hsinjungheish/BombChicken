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
import bombchicken.utils.Path;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author User
 */
public class Pounder extends GameObject { //衝槌

    private Delay extendDelay; //延展時間
    private Delay shortenDelay;//縮短時間
    private Delay stopDelay; //停頓時間
    private Image img;
    private boolean start; //開始啟動衝槌
    private Delay openDelay; //創建多久後開始啟動衝槌

    public Pounder(int x, int y, int width, int height) {
        super(x, y, width, height);
        extendDelay = new Delay(30);
        stopDelay = new Delay(100);
        shortenDelay = new Delay(75);
        img = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().pounder());
        openDelay = new Delay(180);
        openDelay.play();
        start = false;
    }

    public void setOpenDelay(int times) {
        openDelay = new Delay(times);
        openDelay.play();
    }

    public void move() {
        if (start) {
            if (extendDelay.getCount() != 0) {
                translateY(5);
            }
            if (shortenDelay.getCount() != 0) {
                translateY(-2);
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img,
                painter().left(), painter().top(),
                painter().right(), painter().bottom(),
                0, 0,
                Global.UNIT_X, Global.UNIT_Y * 3, null);
    }

    @Override
    public void update() {
        if (openDelay.count()) {
            start = true;
            extendDelay.play();
        }
        move();
        if (extendDelay.count()) {
            extendDelay.stop();
            stopDelay.play();
        }
        if (stopDelay.count()) {
            stopDelay.stop();
            shortenDelay.play();
        }
        if (shortenDelay.count()) {
            shortenDelay.stop();
            extendDelay.play();
        }
    }

}
