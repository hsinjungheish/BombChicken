package bombchicken.animator;

import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Chicken.Color;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Global.Direction;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;

public class ActorAnimator {

    public enum State {

        //雞的動畫
        STILL(new int[]{0, 1, 2, 3, 3, 3, 4, 4, 4, 4, 5, 6, 7, 8, 5, 6, 7, 7, 7, 7, 8, 9, 10, 11, 12, 13, 13, 11, 10, 14, 15}, 4), // 靜止
        DROP(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 8, 9, 10, 11, 8, 9, 10, 11, 8, 9, 10, 11, 8, 9, 10, 11, 8, 9, 10, 11, 8, 9, 10, 11}, 3),
        LAYBOMB(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, 1),//雞下蛋
        WALK(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}, 4), //雞走路
        BOOMHEAD(new int[]{0, 1, 2, 3, 4, 5}, 3), //撞到頭
        PUSHBOMB(new int[]{0, 1, 2, 3, 4}, 1),//推炸彈

        DEAD(new int[]{0, 1, 2, 3, 4, 5}, 5), //死亡
        BIRTH(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 5);//出生

        private int[] arr; //要做的動作
        private int speed; //動作間隔

        State(int[] arr, int speed) {
            this.arr = arr;
            this.speed = speed;
        }

        public int getSpeed() {
            return this.speed;
        }

        public int getFPS() {
            return speed * (arr.length - 1);
        }
    }

    private boolean faceRight;
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
    private final Image img32;

    private final Delay delay;
    private int count;
    private State state;

    public ActorAnimator(State state) {

        img = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().still());
        img2 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().walk());
        img3 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().drop());
        img4 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().layBomb());
        img5 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().hitTop());
        img6 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().pushBomb());
        img7 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().dead());
        img8 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().birth());
        img9 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().stillBlack());
        img10 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().walkBlack());
        img11 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().dropBlack());
        img12 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().layBombBlack());
        img13 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().hitTopBlack());
        img14 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().pushBombBlack());
        img15 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().deadBlack());
        img16 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().birthBlack());
        img17 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().stillYellow());
        img18 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().walkYellow());
        img19 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().dropYellow());
        img20 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().layBombYellow());
        img21 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().hitTopYellow());
        img22 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().pushBombYellow());
        img23 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().deadYellow());
        img24 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().birthYellow());
        img25 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().stillPink());
        img26 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().walkPink());
        img27 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().dropPink());
        img28 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().layBombPink());
        img29 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().hitTopPink());
        img30 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().pushBombPink());
        img31 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().deadPink());
        img32 = SceneController.instance().irc().tryGetImage(
                new Path().img().actors().chicken().birthPink());

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

    public void paint(Color color, boolean faceRight, int left, int top, int right, int bottom, Graphics g) {
        this.faceRight = faceRight;
        Image tmp = null;
        switch (state) {
            case STILL:
                switch (color) {
                    case WHITE:
                        tmp = img;
                        break;
                    case BLACK:
                        tmp = img9;
                        break;
                    case YELLOW:
                        tmp = img17;
                        break;
                    case PINK:
                        tmp = img25;
                        break;
                }

                break;
            case WALK:  // 圖片有上下兩層
                switch (color) {
                    case WHITE:
                        tmp = img2;
                        break;
                    case BLACK:
                        tmp = img10;
                        break;
                    case YELLOW:
                        tmp = img18;
                        break;
                    case PINK:
                        tmp = img26;
                        break;
                }
                if (!faceRight) { //左邊方向的動作，繪畫順序反著畫(因為圖片是水平翻轉，動作順序相反)
                    if (count >= state.arr.length) {  //避免超出陣列
                        count = 0;
                    }

                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * (state.arr.length - 1) - Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X * (state.arr.length) - Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                    return;
                } else if (faceRight) {
                    if (count >= state.arr.length) {  //避免超出陣列
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * state.arr[count], Global.UNIT_Y,
                            Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y * 2, null);
                    return;
                }
//                break;
            case DROP:  // 圖片有上下兩層
                switch (color) {
                    case WHITE:
                        tmp = img3;
                        break;
                    case BLACK:
                        tmp = img11;
                        break;
                    case YELLOW:
                        tmp = img19;
                        break;
                    case PINK:
                        tmp = img27;
                        break;
                }
                if (!faceRight) {
                    if (count >= state.arr.length) {  //避免超出陣列
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * 11 - Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X * 12 - Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                    return;
                } else if (faceRight) {
                    if (count >= state.arr.length) {  //避免超出陣列
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * state.arr[count], Global.UNIT_Y,
                            Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y * 2, null);

                    return;
                }
            case LAYBOMB:
                switch (color) {
                    case WHITE:
                        tmp = img4;
                        break;
                    case BLACK:
                        tmp = img12;
                        break;
                    case YELLOW:
                        tmp = img20;
                        break;
                    case PINK:
                        tmp = img28;
                        break;
                }
                break;
            case BOOMHEAD:
                switch (color) {
                    case WHITE:
                        tmp = img5;
                        break;
                    case BLACK:
                        tmp = img13;
                        break;
                    case YELLOW:
                        tmp = img21;
                        break;
                    case PINK:
                        tmp = img29;
                        break;
                }
                break;
            case PUSHBOMB:
                switch (color) {
                    case WHITE:
                        tmp = img6;
                        break;
                    case BLACK:
                        tmp = img14;
                        break;
                    case YELLOW:
                        tmp = img22;
                        break;
                    case PINK:
                        tmp = img30;
                        break;
                }
                if (!faceRight) {
                    if (count >= state.arr.length) {  //避免超出陣列
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * (state.arr.length - 1) - Global.UNIT_X * state.arr[count], 0,
                            Global.UNIT_X * (state.arr.length) - Global.UNIT_X * state.arr[count], Global.UNIT_Y, null);
                    return;
                } else if (faceRight) {
                    if (count >= state.arr.length) {  //避免超出陣列
                        count = 0;
                    }
                    g.drawImage(tmp,
                            left, top,
                            right, bottom,
                            Global.UNIT_X * state.arr[count], Global.UNIT_Y,
                            Global.UNIT_X + Global.UNIT_X * state.arr[count], Global.UNIT_Y * 2, null);
                    return;
                }
                break;
            case DEAD:
                switch (color) {
                    case WHITE:
                        tmp = img7;
                        break;
                    case BLACK:
                        tmp = img15;
                        break;
                    case YELLOW:
                        tmp = img23;
                        break;
                    case PINK:
                        tmp = img31;
                        break;
                }
                break;
            case BIRTH:
                switch (color) {
                    case WHITE:
                        tmp = img8;
                        break;
                    case BLACK:
                        tmp = img16;
                        break;
                    case YELLOW:
                        tmp = img24;
                        break;
                    case PINK:
                        tmp = img32;
                        break;
                }
                break;
        }

        if (count >= state.arr.length) {  //避免超出陣列
            count = 0;
        }

        //沒有左右方向問題的動作，從這邊照順序畫
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

    public boolean animateOver() { //確定動畫播完
        return count == state.arr.length - 1;

    }
}
