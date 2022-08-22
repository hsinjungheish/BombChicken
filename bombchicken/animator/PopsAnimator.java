package bombchicken.animator;

import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Chicken;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import javax.swing.*;

public class PopsAnimator { //道具動畫

    public enum State {

        //炸彈的動畫
        THROB(new int[]{0, 0, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 15, 16, 17, 18, 15, 16, 17, 18, 15, 16, 17, 18, 19, 20, 21, 22}, 5), // 靜止
        FLYRIGHT(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7}, 1),
        FLYLEFT(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7}, 1),
        EXPLODE(new int[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110}, 2),//爆炸

        //尖刺動畫
        SHINE(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, 5),
        //輸送帶動畫
        WORK(new int[]{0, 8, 16, 24}, 5),
        //寶石動畫
        GEMWALL(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 5),
        GEM(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, 5),
        CHESTOPEN(new int[]{0, 2, 4, 6, 8, 10, 12, 14, 16, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66}, 8),
        CHESTCLOSE(new int[]{0}, 1000000000),
        CHESTEMPTY(new int[]{0}, 1000000000),
        GEMGET(new int[]{0, 2, 4, 6, 8, 10, 12, 14, 16}, 3),
        //火球動畫
        FIREFLYLEFT(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29}, 2),
        FIREFLYRIGHT(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29}, 2),
        //水流動畫
        WATER(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 10, 11, 12, 13, 14, 15, 16, 17, 18, 10, 11, 12, 13, 14, 15, 16, 17, 18, 10, 11, 12, 13, 14, 15, 16, 17, 18, 10, 11, 12, 13, 14, 15, 16, 17, 18,
            19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31, 31}, 5),
        //transition pipe 
        TRANSITIONON(new int[]{0}, 1000000000),
        TRANSITIONOFF(new int[]{0}, 1000000000),
        //flap
        FLAP(new int[]{0}, 1000000000),
        FLAPPING(new int[]{0,1,2, 3, 4, 5, 6, 7, 8}, 8),
        //pressbtn動畫
        PRESSBTNB(new int[]{3,1,2, 3}, 5),
        PRESSBTNR(new int[]{3,1,2, 3}, 5),
        PRESSBTNU(new int[]{3,1,2, 3}, 5),
        PRESSBTNL(new int[]{3,1,2, 3}, 5),
        PRESSBTNBSTILL(new int[]{0}, 1000000000),
        PRESSBTNRSTILL(new int[]{0}, 1000000000),
        PRESSBTNUSTILL(new int[]{0}, 1000000000),
        PRESSBTNLSTILL(new int[]{0}, 1000000000),
        PRESSBTNBPRESS(new int[]{3}, 100000000),
        PRESSBTNRPRESS(new int[]{3}, 100000000),
        PRESSBTNUPRESS(new int[]{3}, 100000000),
        PRESSBTNLPRESS(new int[]{3}, 100000000),
        //大雞動畫
//        BIGCHICKEN(new int[]{0, 4, 8, 4}, 10),
        BIGCHICKEN(new int[]{0, 0,0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4,4,8,8,8}, 20),
        //電梯動畫
        ELEVATORWORK(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65}, 3),
        ELEVATORSTOP(new int[]{0}, 100000000),
        //首頁炸彈火花動畫
        BOMBFIRE(new int[]{20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98}, 2),
        //首頁閃爍動畫  , 1, 0, 1, 0, 1
        MENU(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}, 3),
        //首頁雞選項圖
        CHICKENBUTTON(new int[]{0}, 1000000000),
        //首頁
        MENU2(new int[]{0}, 1000000000),
        //分頁離開選項圖
        EXITCHOOSE(new int[]{0}, 1000000000),
        //雞腿
        CHICKENLEG(new int[]{0}, 1000000),
        //復活點 
        REBIRTHPOINT(new int[]{0}, 1000000),
        REBIRTHPOINTD(new int[]{0}, 1000000);
    	
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
            return speed * arr.length;
        }
    }

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
    private final Image img16;
    private final Image img17;
    private final Image img18;
    private final Image img19;
    private final Image img20;
    private final Image img21;
    private final Image img22;
    private final Image img23;
    private final Image img24;
    private final Image img25;
    private final Image img26;
    private final Image img27;
    private final Image img28;
    private final Image img29;
    private final Image img30;
    private final Image img31;
    private final Image img33;
    private final Image img34;
    private final Image img35;
    private final Image img36;
    private final Image img37;
    private final Image img38;
    private final Image img39;
    private final Image img40;
    
    private final Delay delay;
    private int count;
    private State state;
    private boolean towardRight; //方向朝右邊
    public static Chicken.Color color = Chicken.Color.WHITE;//電梯雞顏色

    public PopsAnimator(State state) {

        img = SceneController.instance().irc().tryGetImage(
                new Path().img().props().bomb().throb());
        img2 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().bomb().explode());
        img3 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().bomb().flyRight());
        img4 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().bomb().flyLeft());
        img5 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().spike().shine());
        img6 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().belt().work());
        img7 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().gemWall());
        img8 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().gem());
        img9 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().chestClose());
        img10 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().chestOpen());
        img11 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().chestEmpty());
        img12 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().fire().fireFlyLeft());
        img13 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().water());
        img14 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().bigchicken());
        img15 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().elevator());
        img16 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().gemGet());
        img17 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().bomb().bombFire());
        img18 = SceneController.instance().irc().tryGetImage(
                new Path().img().backgrounds().main());
        img19 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().chickenButton());
        img20 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().exitChoose());
        img21 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().lightRing());
        img22 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().elevatorBlack());
        img23 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().elevatorPink());
        img24 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().elevatorYellow());
        img25 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().fire().fireFlyRight());
        img26 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().blueTower());
        img27 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().redTower());
        img28 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().redMark());
        img29 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().blueMark());
        img30 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().chickenLeg());
        img31 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().rebirthPoint());
        img33 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().transpipe1());
        img34 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().transpipe2());
        img35 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().flap().flap1());
        img36 = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().rebirthPointD());
        img37 = SceneController.instance().irc().tryGetImage(//圖片要改
                new Path().img().props().pressbtn().pressbtn1());
        img38 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().pressbtn().pressbtn2());
        img39 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().pressbtn().pressbtn3());
        img40 = SceneController.instance().irc().tryGetImage(
                new Path().img().props().pressbtn().pressbtn4());

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
        switch (state) {
            case THROB:
                tmp = img;
                g.drawImage(tmp,
                        left, top, //讓放大的炸彈往上移點，使其看起來不會陷入地板
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                return;
            case EXPLODE:
                tmp = img2;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X * 5, Global.UNIT_Y * 5, null);
                return;

            case FLYRIGHT:
                tmp = img3;
                break;
            case FLYLEFT:
                tmp = img4;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * 7 - Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * 8 - Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                return;
            case SHINE:
                tmp = img5;
                break;
            case WORK:
                tmp = img6;
                if (towardRight) { //輸送帶如果向右轉，則逆著順序播放圖片
                    if (count >= state.arr.length) {
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * 24 - Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X * 32 - Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                    return;

                } else {
                    if (count >= state.arr.length) {
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X * state.arr[count] + Global.UNIT_X * 8, Global.UNIT_Y, null);
                    return;
                }
            case GEMWALL:
                tmp = img7;
                break;
            case GEM:
                tmp = img8;
                break;
            case CHESTCLOSE:
                tmp = img9;
                break;
            case CHESTOPEN:
                tmp = img10;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp, //放大寶箱開啟的動畫到正常大小
                        left - Global.UNIT_X *3/ 4, top - Global.UNIT_Y*3/2,
                        right + Global.UNIT_X *3/4, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X * 2, Global.UNIT_Y * 2, null);
                return;
            case CHESTEMPTY:
                tmp = img11;
                break;
            case FIREFLYLEFT:
                tmp = img12;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        0, Global.UNIT_Y * state.arr[count],
                        Global.UNIT_X * 3, Global.UNIT_Y * state.arr[count] + Global.UNIT_Y, null);
                return;

            case WATER:
                tmp = img13;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top - Global.UNIT_Y,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X, Global.UNIT_Y * 3, null);
                return;
            case BIGCHICKEN:
                tmp = img14;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X * 4, 342, null);
                return;
            case ELEVATORWORK:
                switch (color) {
                    case WHITE:
                        tmp = img15;
                        break;
                    case BLACK:
                        tmp = img22;
                        break;
                    case PINK:
                        tmp = img23;
                        break;
                    case YELLOW:
                        tmp = img24;
                        break;
                }
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X, 96, null);
                return;
            case ELEVATORSTOP:
                tmp = img15;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X, 96, null);
                return;
            case GEMGET:
                tmp = img16;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X * state.arr[count] + Global.UNIT_X * 2, Global.UNIT_Y * 2, null);
                return;
            case BOMBFIRE:
                tmp = img17;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        60 * state.arr[count], 0,
                        60 * state.arr[count] + 60, 60, null);
                return;
            case MENU:
                tmp = img18;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        1200 * state.arr[count], 0,
                        1200 * state.arr[count] + 1200, 680, null);
                return;
            case CHICKENBUTTON:
                tmp = img19;
                break;
            case EXITCHOOSE:
                tmp = img20;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        0, 0,
                        300, 170, null);
                return;
      
            case FIREFLYRIGHT: //逆著播
                tmp = img25;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        0, Global.UNIT_Y * 29 - Global.UNIT_Y * state.arr[count],
                        Global.UNIT_X * 3, Global.UNIT_Y * 30 - Global.UNIT_Y * state.arr[count], null);

                return;
            case CHICKENLEG:
                tmp = img30;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        0, 0,
                        26, 22, null);
                return;
            case REBIRTHPOINT:
                tmp = img31;
                break;
                
            case REBIRTHPOINTD:
                tmp = img36;
                break;
                
            case TRANSITIONON:
                tmp = img34;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                return;
            case TRANSITIONOFF:
                tmp = img33;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                return;
            case FLAPPING:
            	  tmp = img35;
                  if (count >= state.arr.length) {
                      count = 0;
                  }
                  g.drawImage(tmp,
                          left, top, 
                          right, bottom,
                          Global.UNIT_X * state.arr[count], 0,
                          Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                return; 
            case FLAP:
            	tmp = img35;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                return; 
            case PRESSBTNB:
            	tmp = img37;
                if (count >= state.arr.length) {
                    count = 0;
                }
                if(towardRight) {//反著播
                	  g.drawImage(tmp,
                              left, top,
                              right, bottom,
                              Global.UNIT_X *6- Global.UNIT_X *2* state.arr[count], 0,
                              Global.UNIT_X *8- Global.UNIT_X *2* state.arr[count], Global.UNIT_Y, null);
                }else {
                	  g.drawImage(tmp,
                              left, top,
                              right, bottom,
                              Global.UNIT_X *2* state.arr[count], 0,
                              Global.UNIT_X*2 + Global.UNIT_X *2* state.arr[count], Global.UNIT_Y, null);
                }
               
                return; 
            case PRESSBTNBSTILL:
            	tmp = img37;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        (Global.UNIT_X*2) * state.arr[count], 0,
                        Global.UNIT_X*2 + Global.UNIT_X*2 * state.arr[count], Global.UNIT_Y, null);
                return; 
            case PRESSBTNBPRESS:
            	tmp = img37;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        (Global.UNIT_X*2) * state.arr[count], 0,
                        Global.UNIT_X*2 + Global.UNIT_X*2 * state.arr[count], Global.UNIT_Y, null);
                return; 
                
            case PRESSBTNR:
            	tmp = img38;
                if (count >= state.arr.length) {
                    count = 0;
                }
                if(towardRight) {//反著播
                	  g.drawImage(tmp,
                              left, top,
                              right, bottom,
                              Global.UNIT_X *3- Global.UNIT_X * state.arr[count], 0,
                              Global.UNIT_X *4- Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
                }else {
                	  g.drawImage(tmp,
                              left, top,
                              right, bottom,
                              Global.UNIT_X * state.arr[count], 0,
                              Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
                }
                return; 
            case PRESSBTNRSTILL:
            	tmp = img38;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
                return; 
            case PRESSBTNRPRESS:
            	tmp = img38;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
                return; 
                
            case PRESSBTNU:
            	tmp = img39;
                if (count >= state.arr.length) {
                    count = 0;
                }
                if(towardRight) {//反著播
              	  g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X *6- Global.UNIT_X *2* state.arr[count], 0,
                            Global.UNIT_X *8- Global.UNIT_X *2* state.arr[count], Global.UNIT_Y, null);
              }else {
              	  g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X *2* state.arr[count], 0,
                            Global.UNIT_X*2 + Global.UNIT_X *2* state.arr[count], Global.UNIT_Y, null);
              }
                return; 
            case PRESSBTNUSTILL:
            	tmp = img39;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X *2* state.arr[count], 0,
                        Global.UNIT_X *2+ Global.UNIT_X  *2*state.arr[count], Global.UNIT_Y, null);
                return; 
            case PRESSBTNUPRESS:
            	tmp = img39;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X *2* state.arr[count], 0,
                        Global.UNIT_X *2+ Global.UNIT_X *2* state.arr[count], Global.UNIT_Y, null);
                return; 
            case PRESSBTNL:
            	tmp = img40;
                if (count >= state.arr.length) {
                    count = 0;
                }
                if(towardRight) {//反著播
              	  g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X *3- Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X *4- Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
              }else {
              	  g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
              }
                return; 
            case PRESSBTNLPRESS:
            	tmp = img40;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
                return; 
            case PRESSBTNLSTILL:
            	tmp = img40;
                if (count >= state.arr.length) {
                    count = 0;
                }
                g.drawImage(tmp,
                        left, top,
                        right, bottom,
                        Global.UNIT_X * state.arr[count], 0,
                        Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y*2, null);
                return; 
                
        }

        //一般動畫
        if (count >= state.arr.length) {
            count = 0;
        }

        g.drawImage(tmp,
                left, top,
                right, bottom,
                Global.UNIT_X * state.arr[count], 0,
                Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);

    }

    public void update() {
        if (delay.count()) {
            count = ++count % state.arr.length; //讓動畫照特定順序循環播放
        }
    }

    public static void setElevatorColor(Chicken.Color value) {
        color = value;
    }

}
