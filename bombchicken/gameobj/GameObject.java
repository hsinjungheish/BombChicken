package bombchicken.gameobj;

import bombchicken.camera.MapInformation;
import bombchicken.utils.GameKernel.GameInterface;
import bombchicken.utils.Global;
import java.awt.Color;
import java.awt.Graphics;

public abstract class GameObject implements GameInterface {

    private final Rect collider;
    private final Rect painter;
    private Rect reRect; // 上一禎的正方形

    public GameObject(int x, int y, int width, int height) { // 中心 ,寬,高
        this(x, y, width, height, x, y, width, height);
    }

    public GameObject(Rect rect) {
        collider = new Rect(rect);
        painter = new Rect(rect);
        reRect = new Rect(rect);
    }

    public GameObject(int x, int y, int width, int height,
            int x2, int y2, int width2, int height2) {
        collider = Rect.genWithCenter(x, y, width, height);
        painter = Rect.genWithCenter(x2, y2, width2, height2);
        reRect = Rect.genWithCenter(x2, y2, width2, height2);
    }

    public GameObject(Rect rect, Rect rect2) {
        collider = new Rect(rect);
        painter = new Rect(rect2);
        reRect = new Rect(rect);
    }

    public boolean outOfScreen() {
        if (painter.bottom() <= 0) {
            return true;
        }
        if (painter.right() <= 0) {
            return true;
        }
        if (painter.left() >= MapInformation.mapInfo().right()) {
            return true;
        }
        if (painter.top() >= MapInformation.mapInfo().bottom()) {
            return true;
        }
        return false;
    }

    // 設位置(用於碰撞後設定位置) (左上起始點的位置)，再利用其他去設定右下角，避免形變
    public  void setPosition(int x, int y) { // 直接設中心點
        painter().setCenter(x, y);
        collider().setCenter(x, y); 
    }

    // 判斷碰撞下面時，先判斷底邊是否與obj的頂邊有無重疊(炸彈可以用到)
    // 再判斷 y 是否撞到
    public boolean touchTop(GameObject obj) {  //別人碰到我的上面時回傳true
        boolean leftJudg = collider().left() >= obj.collider().left() && collider().left() <= obj.collider().right(); // 左邊極限值落在obj的左極極右極之間
        boolean rightJudg = collider().right() >= obj.collider().left() && collider().right() <= obj.collider().right(); // 右邊極限值落在obj的左極極右極之間
        boolean centerJudg = obj.collider().right() <= collider().right() && obj.collider().left() >= collider().left(); // obj 在中間穿過
        if (leftJudg || rightJudg || centerJudg) { // 有其中一邊落在obj的左極右極之間的話就進入判斷
            if (collider.overlap(obj.collider)) { // 如果有交疊
                return collider.top() <= obj.collider.bottom(); // 撞到回傳ture
            }
        }
        return false;
    }

    public boolean touchBottom(GameObject obj) {  //別人碰到我的下面時回傳true
        boolean leftJudg = collider().left() >= obj.collider().left() && collider().left() <= obj.collider().right(); // 左邊極限值落在obj的左極極右極之間
        boolean rightJudg = collider().right() >= obj.collider().left() && collider().right() <= obj.collider().right(); // 右邊極限值落在obj的左極極右極之間
        boolean centerJudg = obj.collider().right() <= collider().right() && obj.collider().left() >= collider().left(); // obj 在中間穿過
        if (leftJudg || rightJudg || centerJudg) { // 有其中一邊落在obj的左極右極之間的話就進入判斷
            if (collider.overlap(obj.collider)) { // 如果有交疊
                return collider.bottom() >= obj.collider.top(); // 撞到回傳ture
            }
        }
        return false;

    }

    public boolean touchLeft(GameObject obj) {
        boolean topJudg = collider().top() >= obj.collider().top() && collider().top() <= obj.collider().bottom(); // 上邊極限值落在obj的上極下極之間
        boolean bottomJudg = collider().bottom() >= obj.collider().top() && collider().bottom() <= obj.collider().bottom(); // 下邊極限值落在obj的上極下極之間
        boolean centerJudg = obj.collider().bottom() <= collider().bottom() && obj.collider().top() >= collider().top(); // obj 在中間穿過
        if (topJudg || bottomJudg || centerJudg) { // 有其中一邊落在obj的上極下極之間的話就進入判斷
            if (collider.overlap(obj.collider)) { // 如果有交疊
                return collider.left() <= obj.collider.right() && collider.right() > obj.collider.right(); // 撞到回傳ture
            }
        }
        return false;

    }

    public boolean touchRight(GameObject obj) {
        boolean topJudg = collider().top() >= obj.collider().top() && collider().top() <= obj.collider().bottom(); // 上邊極限值落在obj的上極下極之間
        boolean bottomJudg = collider().bottom() >= obj.collider().top() && collider().bottom() <= obj.collider().bottom(); // 下邊極限值落在obj的上極下極之間
        boolean centerJudg = obj.collider().bottom() <= collider().bottom() && obj.collider().top() >= collider().top(); // obj 在中間穿過
        if (topJudg || bottomJudg || centerJudg) { // 有其中一邊落在obj的上極下極之間的話就進入判斷
            if (collider.overlap(obj.collider)) { // 如果有交疊
                return collider.right() >= obj.collider.left() && collider.right() < obj.collider.right(); // 撞到回傳ture
            }
        }
        return false;
    }
    // 單純的碰撞 (撞倒致命物件)
    public boolean touch(GameObject obj) {
        return collider.overlap(obj.collider);
    }

    //判斷碰撞地圖四方，避免超出地圖範圍
    public boolean touchLeft() {
        return collider.left() <= 0;
    }

    public boolean touchTop() {
        return collider.top() <= 0;
    }

    public boolean touchRight() {
        return collider.right() >= MapInformation.mapInfo().right();
    }

    public boolean touchBottom() {
        return collider.bottom() >= MapInformation.mapInfo().bottom();
    }

    public boolean isCollision(GameObject obj) {
        return collider.overlap(obj.collider);
    }

    public final void translate(int x, int y) {
        collider.translate(x, y);
        painter.translate(x, y);
    }

    public final void translateX(int x) {
        collider.translateX(x);
        painter.translateX(x);
    }

    public final void translateY(int y) {
        collider.translateY(y);
        painter.translateY(y);
    }

    public final Rect collider() {
        return collider;
    }

    public final Rect painter() {
        return painter;
    }

    public Rect reRect() { // 取得上一禎的位置資訊
        return reRect;
    }

    public void setReRect(Rect rect) { // 更新上一禎的位置
        reRect = new Rect(rect);
    }

    @Override
    public final void paint(Graphics g) {
        paintComponent(g);
        if (Global.IS_DEBUG) {
            g.setColor(Color.RED);
            g.drawRect(this.painter.left(), this.painter.top(), this.painter.width(), this.painter.height());
            g.setColor(Color.BLUE);
            g.drawRect(this.collider.left(), this.collider.top(), this.collider.width(), this.collider.height());
            g.setColor(Color.BLACK);
        }
    }

    public abstract void paintComponent(Graphics g);
}
