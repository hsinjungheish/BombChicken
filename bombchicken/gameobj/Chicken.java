package bombchicken.gameobj;

import bombchicken.animator.ActorAnimator;
import static bombchicken.animator.ActorAnimator.State.BOOMHEAD;
import bombchicken.client.ClientClass;
import bombchicken.controllers.AudioResourceController;
import bombchicken.controllers.SceneController;
import static bombchicken.gameobj.Chicken.State.*;
import bombchicken.utils.CommandSolver;
import bombchicken.utils.Delay;
import bombchicken.utils.GameKernel.GameInterface;
import bombchicken.utils.Global;
import bombchicken.utils.Global.Direction;
import bombchicken.utils.Path;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Chicken extends GameObject implements CommandSolver.KeyListener {

    public enum Color {

        WHITE(0),
        BLACK(1),
        PINK(2),
        YELLOW(3);
        private int value;

        Color(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum Group { //陣營

        BLUE(0),
        RED(1);
        private int value;

        Group(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private ActorAnimator animator;
    private State state;
    private Direction dir;
    private Delay eggDelay; // 限制下蛋頻率
    private Delay moveDelay; // 限制更變狀態之delay
    private Delay untouchDelay; // 無敵狀態的delay
    private Delay twinkleDelay; // 閃爍的delay
    private Delay elevatorDelay; //搭電梯的delay
    private Image img;
    private boolean untouchable; // 是否為無敵狀態
    private boolean faceRight; // 面向
    private boolean neverBirth; //尚未出生
    private boolean nextState; // 動作完成可接受下一個狀態指令
    private boolean twinkle; // 閃爍
    private boolean inMenu; // 在選單時不閃爍
    private int lifeCount; // 命
    private Color color; // 雞的顏色
    private int[] commendCode; // 多p時要用到的不同指令
    private int ID;
    private AudioResourceController arc; 
    private boolean dead; // 死了設屬性就好了，不能設 null
    private boolean readyToChange; // 死了設屬性就好了，不能設 null
    private String name;
    private int moveSpeed;//移動速度
    private int getGemNumber;
    private boolean host; // 房主
    private Group group; //陣營
    private boolean isWin; //輸贏
    private Water rebirthPoint;//重生點是哪個
    private boolean invisible=false;

    public Chicken(int x, int y, int width, int height) {
        this(x, y, width, height, 0, Color.WHITE, "");
//        super(x, y, width, height);
//        eggDelay = new Delay(20); // 雞下蛋的頻率
//        eggDelay.loop();
//        twinkleDelay = new Delay(10); // 閃爍頻率
//        twinkleDelay.loop(); // 設定迴圈
//        untouchDelay = new Delay(180); // 無敵三秒
//        untouchDelay.play(); // 開始計時
//        elevatorDelay = new Delay(120); //等電梯兩秒      
//        animator = new ActorAnimator(ActorAnimator.State.BIRTH);
//        setState(State.BIRTH, ActorAnimator.State.BIRTH);
//        faceRight = true;
//        neverBirth = true;
//        nextState = true; // 預設為可更變狀態
//        lifeCount = 10; // 預設10條命
//        this.color = Color.WHITE; //預設白雞
//        commendCode = new int[]{1, 2, 10}; // 預設為1p狀態
//        untouchable = true; // 開局先無敵
//        twinkle = true; // 閃爍先設為 true
//        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
//        dead = false;
    }

    public Chicken(int x, int y, int width, int height, int ID, Color color, String name) {
        super(x, y, width, height);
        eggDelay = new Delay(20); // 雞下蛋的頻率
        eggDelay.loop();
        twinkleDelay = new Delay(20); // 閃爍頻率
        twinkleDelay.loop(); // 設定迴圈
        untouchDelay = new Delay(120); // 無敵三秒
        untouchDelay.play(); // 開始計時
        elevatorDelay = new Delay(120); //等電梯兩秒  
        animator = new ActorAnimator(ActorAnimator.State.BIRTH);
        setState(State.BIRTH, ActorAnimator.State.BIRTH);
        faceRight = true;
        neverBirth = true;
        nextState = true; // 預設為可更變狀態
        lifeCount = 100; // 預設10條命
        commendCode = new int[]{1, 2, 10}; // 預設為1p狀態
        untouchable = true; // 開局先無敵
        twinkle = true; // 閃爍先設為 true
        this.color = color;
        this.ID = ID;
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
        dead = false;
        readyToChange = false;
        this.name = name;
        moveSpeed = 5;
        getGemNumber = 0;
        isWin = true;
        rebirthPoint = null;
        group = Group.BLUE; // 預設為籃隊
       
    }

    public interface stateMove { 
        public void touchFloor(Chicken c); // no matter boom or floor , 左右撞牆

        public void touchBoom(Chicken c);

        public void touchBottom(Chicken c);

        public void touchTop(Chicken c);

        public void moveDelay(Chicken c);

        public void die(Chicken c);
    }

    public enum State implements stateMove {

        STILL { // 靜止
                    @Override
                    public void touchFloor(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBoom(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBottom(Chicken c) {
                        c.setPosition(c.collider().centerX(), c.reRect().centerY()); // 上下撞到只回復y
                    }

                    @Override
                    public void touchTop(Chicken c) { // 目前還不會發生的就先不定義
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;

                    }
                },
        DROP { // 下墜 
                    @Override
                    public void touchFloor(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBoom(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBottom(Chicken c) {
                        c.setPosition(c.collider().centerX(), c.reRect().centerY()); // 上下撞到只回復y
                        c.setState(STILL, ActorAnimator.State.STILL);
                    }

                    @Override
                    public void touchTop(Chicken c) {
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;
                    }

                },
        LAYBOMB { // 雞下蛋（延遲）
                    @Override
                    public void touchFloor(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBoom(Chicken c) {

                    }

                    @Override
                    public void touchBottom(Chicken c) {

                    }

                    @Override
                    public void touchTop(Chicken c) {
                        c.setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD); // 設狀態 
                    }

                    @Override
                    public void moveDelay(Chicken c) {

                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;
                    }

                },
        WALK { // 雞走路
                    @Override
                    public void touchFloor(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBoom(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                        c.setState(PUSHBOMB, ActorAnimator.State.PUSHBOMB); // 撞到炸彈，主角狀態設為推炸彈
                    }

                    @Override
                    public void touchBottom(Chicken c) {

                    }

                    @Override
                    public void touchTop(Chicken c) {
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;
                    }

                },
        BOOMHEAD { // 撞到頭（延遲）
                    @Override
                    public void touchFloor(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBoom(Chicken c) {

                    }

                    @Override
                    public void touchBottom(Chicken c) {

                    }

                    @Override
                    public void touchTop(Chicken c) {
                        c.setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;
                    }

                },
        PUSHBOMB { // 撞炸彈（延遲）
                    @Override
                    public void touchFloor(Chicken c) {
                        c.setPosition(c.reRect().centerX(), c.collider().centerY()); // 左右撞到只回復x值
                    }

                    @Override
                    public void touchBoom(Chicken c) {

                    }

                    @Override
                    public void touchBottom(Chicken c) {
                    }

                    @Override
                    public void touchTop(Chicken c) {
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;
                    }

                },
        BIRTH { // 出生（延遲）

                    @Override
                    public void touchFloor(Chicken c) {
                    }

                    @Override
                    public void touchBoom(Chicken c) {
                    }

                    @Override
                    public void touchBottom(Chicken c) {
                    }

                    @Override
                    public void touchTop(Chicken c) {
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                        c.lifeCount--;
                    }

                },
        DEAD { // 死亡（延遲）
                    @Override
                    public void touchFloor(Chicken c) {
                    }

                    @Override
                    public void touchBoom(Chicken c) {
                    }

                    @Override
                    public void touchBottom(Chicken c) {
                    }

                    @Override
                    public void touchTop(Chicken c) {
                    }

                    @Override
                    public void moveDelay(Chicken c) {
                    }

                    @Override
                    public void die(Chicken c) {
                        c.setState(DEAD, ActorAnimator.State.DEAD);
                    }

                };

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public void setColor(Color color) { //設定雞的顏色
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setRebirthPoint(Water rebirthPoint) {
        this.rebirthPoint = rebirthPoint;
    }

    public Water getRebirthPoint() {
        return this.rebirthPoint;
    }

    public void setCommendCode(int[] arr) { // 覆蓋指令，多p時使用
        commendCode = arr;
    }

    public int getLifeCount() { // 取出命     
        return lifeCount;
    }

    public void setLifeCount(int life) { // 設命
        if (life <= 0) {
            lifeCount = 0;
        } else {
            lifeCount = life;
        }
    }

    public boolean getNextState() {
        return nextState;
    }

    public Direction direction() {
        return this.dir;
    }

    public boolean getDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean getHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public int getGemNumber() {
        return this.getGemNumber;
    }

    public void setGemNumber(int getGemNumber) {
        this.getGemNumber = getGemNumber;
    }

    public boolean getReadyToChange() {
        return readyToChange;
    }

    public void setReadyToChange(boolean readyToChange) {
        this.readyToChange = readyToChange;
    }

    public void changeDir(int c) {
        if (c == commendCode[0]) {
            faceRight = false;
            dir = Direction.LEFT;
        } else if (c == commendCode[1]) {
            faceRight = true;
            dir = Direction.RIGHT;
        } else if (c == commendCode[2]) {
            dir = Direction.NO;
        }
    }

    public void move() {
        if (dir == Direction.RIGHT && !touchRight() && nextState) {
            translateX(moveSpeed);
            if (state != DROP && state != LAYBOMB) { // DROP狀態優先於WALK (為解決不會空中黏牆上)
                setState(WALK, ActorAnimator.State.WALK);
            }

        } else if (dir == Direction.LEFT && !touchLeft() && nextState) {
            translateX(-moveSpeed);
            if (state != DROP && state != LAYBOMB) { // DROP狀態優先於WALK (為解決不會空中黏牆上)
                setState(WALK, ActorAnimator.State.WALK);
            }
        }
    }

    public void setMoveSpeed(int speed) {
        this.moveSpeed = speed;
    }

    public State getState() {
        return state;
    }

    public ActorAnimator getAnimator() {
        return this.animator;
    }

    public void setState(State state, ActorAnimator.State astate) { // 分開設
        this.state = state;
        animator.setState(astate); // set animator state

        switch (state) {
            case LAYBOMB: // 當目標狀態是需要延遲時
                arc.play(new Path().sound().ChickenSound().bomb_lay());
                moveDelay = new Delay(animator.getState().getFPS()); // 創建新的 delay
                moveDelay.play(); // 啟動
                nextState = false; // 設定為不能更變狀態
                break;
            case BOOMHEAD:
                arc.play(new Path().sound().ChickenSound().headBang());
                moveDelay = new Delay(animator.getState().getFPS()); // 創建新的 delay
                moveDelay.play(); // 啟動
                nextState = false; // 設定為不能更變狀態
                break;
            case PUSHBOMB:
                arc.play(new Path().sound().ChickenSound().kick_bomb1());
                moveDelay = new Delay(animator.getState().getFPS()); // 創建新的 delay
                moveDelay.play(); // 啟動
                nextState = false; // 設定為不能更變狀態
                break;
            case BIRTH:
//                arc.play(new Path().sound().ChickenSound().reborn()); 改什麼都找不到這個檔名
                moveDelay = new Delay(animator.getState().getFPS()); // 創建新的 delay
                moveDelay.play(); // 啟動
                nextState = false; // 設定為不能更變狀態
                break;
            case DEAD:
                arc.play(new Path().sound().ChickenSound().chicken_death());
                moveDelay = new Delay(animator.getState().getFPS()); // 創建新的 delay
                moveDelay.play(); // 啟動
                nextState = false; // 設定為不能更變狀態
                break;
            default:
                moveDelay = new Delay(animator.getState().getFPS()); // 創建新的 delay
                moveDelay.stop();// 暫停
                nextState = true; // 設定為可更變狀態
        }
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return this.ID;
    }
    
    public boolean getVisibleState() {
        return invisible;
    }

    public void setVisibleState(boolean invisible) {
        this.invisible = invisible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Chicken.Color intToColor(int n) {
        switch (n) {
            case 0:
                return Chicken.Color.WHITE;
            case 1:
                return Chicken.Color.BLACK;
            case 2:
                return Chicken.Color.PINK;
            case 3:
                return Chicken.Color.YELLOW;
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (twinkle || inMenu) {
            animator.paint(color, faceRight,
                    painter().left(), painter().top(),
                    painter().right(), painter().bottom(), g);

        }

    }

    public boolean neverBirth() {
        return this.neverBirth;
    }

    public void setNeverBirth(boolean neverBirth) {
        this.neverBirth = neverBirth;
    }

    public boolean untouchable() { // 判斷是否為無敵狀態
        return untouchable;
    }

    public void setUntouchable() {
        untouchable = true;
        untouchDelay.play();
    }

    @Override
    public void update() {
        animator.update(); //避免被重置成still狀態的delay

        move();

        dir = Direction.NO; // 避免連續移動 

        if (moveDelay.count()) { // 動作延遲完畢
            nextState = true;  // 設為可更變狀態
        };

        if (untouchDelay.count()) { // 無敵時間倒數
            untouchable = false;
            untouchDelay.stop();
        }

        if (untouchable) { // 如果為無敵狀態
            if (twinkleDelay.count()) { // twinkle 就開始 count
                twinkle = !twinkle;
            }

        } else {
            twinkle = true;
        }

    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        if (nextState) { // 容許更新狀態時才能更變
            changeDir(commandCode);
            if (dir != Direction.NO && state != DROP) { // 下墜優先於行走
                this.setState(WALK, ActorAnimator.State.WALK);
            }
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
//        if (nextState) { // 容許更新狀態時才能更變
//            changeDir(commandCode);
//            if (dir != Direction.NO && state != DROP) { // 下墜優先於行走
//                this.setState(WALK, ActorAnimator.State.WALK);
//            }
//        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }

}
