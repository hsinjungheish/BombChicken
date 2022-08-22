/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.gameobj;

import bombchicken.animator.PopsAnimator;
import static bombchicken.animator.PopsAnimator.State.*;
import bombchicken.controllers.AudioResourceController;
import bombchicken.utils.Delay;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Graphics;

/**
 *
 * @author User
 */
public class Gem extends GameObject {

    private PopsAnimator animator;
    private PopsAnimator.State state;
//    private boolean gemIsturn; // 寶石狀態縮小過沒
//    private boolean chestCloseIsturn; // 寶箱(關閉狀態)縮小過沒
//    private boolean chestOpenIsturn; //  寶箱(開啟狀態)縮小過沒
    private Delay openDelay; // 寶箱開啟動畫計時
    private Delay beEatenDelay; // 寶石被雞吃到後刪除計時
    private boolean isturn; // 被吃到是否變過大小
    private boolean isDelete; // 是否要刪除
    private AudioResourceController arc; // 播音747
    private boolean needToRemove;

    public Gem(int x, int y, int width, int height, PopsAnimator.State state) {
        super(x, y, width, height);
        animator = new PopsAnimator(state);
        this.state = state;
        openDelay = new Delay((PopsAnimator.State.CHESTOPEN.getArrLenth() - 1) * PopsAnimator.State.CHESTOPEN.getSpeed());
//        gemIsturn = false;
//        chestCloseIsturn = false;
//        chestOpenIsturn = false;

        beEatenDelay = new Delay(PopsAnimator.State.GEMGET.getFPS());
        isturn = false; // 初始化為false
        isDelete = false;
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
    }

    public PopsAnimator.State getState() {
        return this.animator.getState();
    }

    public void setState(PopsAnimator.State state) {
        animator.setState(state);
        this.state = state;
    }
    
    public void setRemove(boolean remove){
        needToRemove = remove;
    }
    
    public boolean getRemove(){
        return needToRemove;
    }

    public boolean isDelete() { // 判斷是否要刪除
        return isDelete;
    }

//    public void setCollider() {
//        int n;
//        switch (animator.getState()) {
//            case GEM: //寶石狀態讓碰撞框縮小
//                n = 15;
//                if (!gemIsturn) {
//                    this.collider().setTop(this.collider().top() + n);
//                    this.collider().setBottom(this.collider().bottom() - n);
//                    this.collider().setRight(this.collider().right() - n);
//                    this.collider().setLeft(this.collider().left() + n);
//                }
//                gemIsturn = true;
//                break;
//            case CHESTCLOSE:
//                if (!chestCloseIsturn) {
//                    this.collider().setTop(this.collider().top() + 15);
//                    this.collider().setRight(this.collider().right() - 8);
//                    this.collider().setLeft(this.collider().left() + 8);
//                }
//                chestCloseIsturn = true;
//                break;
//            case CHESTOPEN:
//                n = 30;
//                if(!chestOpenIsturn){
//                this.painter().setTop(this.collider().top() - n);
//                this.painter().setBottom(this.collider().bottom() + n);
//                this.painter().setRight(this.collider().right() + n);
//                this.painter().setLeft(this.collider().left() - n);
//                }
//                chestOpenIsturn = true;
//                break;
//        }
//    }
    @Override
    public void paintComponent(Graphics g) {
        animator.paint(
                painter().left(), painter().top(),
                painter().right(), painter().bottom(), g);
    }

    @Override
    public void update() {
        animator.update();

        if (state == GEMGET) {
            if (!isturn) { // 改painter 與 collider 的大小
                painter().setBottom(reRect().bottom() + reRect().height()); // 四邊各多多少,如果用 painter 改的話就會造成painter的length與width改變
                painter().setTop(reRect().top() - reRect().height()); // 因此用 collider 作為定數
                painter().setRight(reRect().right() + reRect().width());
                painter().setLeft(reRect().left() - reRect().width());
                isturn = true; // 避免重複設定
                arc.play(new Path().sound().ObjectSound().gemwoosh());
            }
        }
        if (state == CHESTOPEN) { // 寶箱打開後開始計時
            
            openDelay.play();
        }
        if(state == GEMGET){
            beEatenDelay.play();
        }
        if (state == GEMGET && beEatenDelay.count()) { 
            isDelete = true;
        }
        if (state == CHESTOPEN && openDelay.count()) { // 時間到，變成空寶箱的狀態
            setState(PopsAnimator.State.CHESTCLOSE);
        }

    }

}
