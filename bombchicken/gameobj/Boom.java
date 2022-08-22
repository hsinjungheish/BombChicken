package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import bombchicken.controllers.AudioResourceController;
import static bombchicken.gameobj.Boom.State.*;
import bombchicken.controllers.SceneController;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;
import java.awt.Image;

public class Boom extends GameObject {

    private PopsAnimator animator;
    private State state;
    private Delay boomDelay; // 放置後倒數計時
    private Delay explodeDelay; // 爆炸後計時
    private boolean isDelete; // 是否要刪除
    private boolean isturn; // 爆炸後是否變過大小
    private int explodeRange = 2; // 爆炸後的判定範圍collider所用（為除數，因此越大範圍就越小）
    private int explodePaintRange = 3; // painter 用的擴張範圍（為倍數用乘的）
    private boolean isq;
    private AudioResourceController arc; // 播音747
    private boolean[] alreadyShake = new boolean[2];


    public interface stateMove { // 把炸彈傳進來為所欲為

        public void beenTouchLeft(Boom b); // no matter boom or floor ,左右撞牆

        public void beenTouchRight(Boom b);

        public void beenTouchBottom(Boom b);
    }

    public enum State implements stateMove {

        THROB { // 靜止
                    @Override
                    public void beenTouchLeft(Boom b) {
                        b.setState(FLYRIGHT, PopsAnimator.State.FLYRIGHT);
                    }

                    @Override
                    public void beenTouchRight(Boom b) {
                        b.setState(FLYLEFT, PopsAnimator.State.FLYLEFT);
                    }

                    @Override
                    public void beenTouchBottom(Boom b) {
                        b.setPosition(b.collider().centerX(), b.reRect().centerY());
                    }

                },
        FLYRIGHT {
                    @Override
                    public void beenTouchLeft(Boom b) {
                        b.setState(EXPLODE, PopsAnimator.State.EXPLODE);
                        b.setPosition(b.reRect().centerX(), b.collider().centerY());
                    }

                    @Override
                    public void beenTouchRight(Boom b) {
                        b.setState(EXPLODE, PopsAnimator.State.EXPLODE);
                        b.setPosition(b.reRect().centerX(), b.collider().centerY());
                    }

                    @Override
                    public void beenTouchBottom(Boom b) {

                    }

                },
        FLYLEFT {
                    @Override
                    public void beenTouchLeft(Boom b) {
                        b.setState(EXPLODE, PopsAnimator.State.EXPLODE);
                        b.setPosition(b.reRect().centerX(), b.collider().centerY());
                    }

                    @Override
                    public void beenTouchRight(Boom b) {
                        b.setState(EXPLODE, PopsAnimator.State.EXPLODE);
                        b.setPosition(b.reRect().centerX(), b.collider().centerY());
                    }

                    @Override
                    public void beenTouchBottom(Boom b) {

                    }

                },
        EXPLODE {
                    @Override
                    public void beenTouchLeft(Boom b) {

                    }

                    @Override
                    public void beenTouchRight(Boom b) {

                    }

                    @Override
                    public void beenTouchBottom(Boom b) {

                    }

                };

    }

    public PopsAnimator getAnimator() {
        return animator;
    }

    public Boom(int x, int y, int width, int height) {
        super(x, y, width, height);
        animator = new PopsAnimator(PopsAnimator.State.THROB);
        setState(THROB, PopsAnimator.State.THROB);
        //放置後倒數計時，delay幀數與動畫throb陣列的總幀數相同
        boomDelay = new Delay((PopsAnimator.State.THROB.getFPS()));
        boomDelay.play();
        //爆炸後刪除計時
        explodeDelay = new Delay(PopsAnimator.State.EXPLODE.getFPS());
        isDelete = false; // 初始化為false
        isturn = false; // 初始化為false
        isq = false;
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器

    }

    public boolean isDelete() { // 判斷是否要刪除
        return isDelete;
    }
    

    public void move() {
        if (state == FLYLEFT) {
            translateX(-15);
        }
        if (state == FLYRIGHT) {
            translateX(15);
        }
    }

    public void setState(State state, PopsAnimator.State astate) {
        this.state = state;
        animator.setState(astate);
    }

    public State getState() {
        return this.state;
    }
    
    public void setAlreadyShake(boolean als, int index) {
        alreadyShake[index] = als;
    }

    public boolean getAlreadyShake(int index) {
        return alreadyShake[index];
    }

    @Override
    public void paintComponent(Graphics g) {
        animator.paint(
                painter().left(), painter().top(),
                painter().right(), painter().bottom(), g);
    }

    @Override
    public void update() {
        animator.update();
        move();
        if (state == EXPLODE) {
            if (!isturn) { // 改painter 與 collider 的大小
                painter().setBottom(reRect().bottom() + reRect().height() * explodePaintRange ); // 四邊各多多少,如果用 painter 改的話就會造成painter的length與width改變
                painter().setTop(reRect().top() - reRect().height() * explodePaintRange  ); // 因此用 collider 作為定數
                painter().setRight(reRect().right() + reRect().width() * explodePaintRange); 
                painter().setLeft(reRect().left() - reRect().width() * explodePaintRange); 
                collider().setBottom(reRect().bottom() + reRect().height() / explodeRange  ); // 用 painter 作為定數
                collider().setTop(reRect().top() - reRect().height() / explodeRange); 
                collider().setRight(reRect().right() + reRect().width() / explodeRange ); 
                collider().setLeft(reRect().left() - reRect().width() / explodeRange); 
                isturn = true; // 避免重複設定
                arc.play(new Path().sound().ObjectSound().explode2());
            }
        }

        if (state == EXPLODE) { // 飛行後開啟計時
            explodeDelay.play();
        }
        if (boomDelay.count()) { // 計時到便改變狀態
            setState(EXPLODE, PopsAnimator.State.EXPLODE);
            explodeDelay.play();
        }
        if (state == EXPLODE && explodeDelay.count()) { 
            isDelete = true;
        }

        if (state == FLYLEFT || state == FLYRIGHT) { // 飛左飛右時不會在空中爆炸
            boomDelay.pause();

        }
    }

}
