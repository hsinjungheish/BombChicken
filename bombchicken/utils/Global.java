/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.utils;

import java.awt.event.KeyEvent;

/**
 *
 * @author User
 */
public class Global {

    public enum Direction {

        UP(3),
        DOWN(0),
        LEFT(1),
        RIGHT(2),
        NO(-1);
        private int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static final boolean IS_DEBUG = false;
//    public static final boolean IS_DEBUG = true;

    public static void log(String str) {
        if (IS_DEBUG) {
            System.out.println(str);
        }
    }
    // 視窗大小
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 680;
//    public static final int SCREEN_X = WINDOW_WIDTH - 8 - 8;
//    public static final int SCREEN_Y = WINDOW_HEIGHT - 31 - 8;
  public static final int SCREEN_X = WINDOW_WIDTH ;
  public static final int SCREEN_Y = WINDOW_HEIGHT ;
    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 60;// 每秒更新60次遊戲邏輯
    public static final int NANOSECOND_PER_UPDATE = 1000000000 / UPDATE_TIMES_PER_SEC;// 每一次要花費的奈秒數
    // 畫面更新時間
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT;

    public static final int P1LEFT = 1;
    public static final int P1RIGHT = 2;
    public static final int P1UP = 3;
    public static final int P1DOWN = 4;
    public static final int P1STOP = 10;
    public static final int P2LEFT = 5;
    public static final int P2RIGHT = 6;
    public static final int P2UP = 7;
    public static final int P2DOWN = 8;
    public static final int P2STOP = 11;
    public static final int SPACE = 9;
    public static final int ESC = 12;
    public static final int ENTER = 13;
    public static final int TEACH = 14;
    public static final int REPLACE = 15;
    public static final int REPLACE2 = 16;

    //單位網格大小
    public static final int UNIT_X = 64;
    public static final int UNIT_Y = 64;

    public static int random(int min, int max) {
       
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static boolean random(int rate) {
        return random(1, 100) <= rate;
    }
}
