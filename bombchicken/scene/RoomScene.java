/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.scene;

import bombchicken.animator.ActorAnimator;
import bombchicken.animator.EnemyAnimator;
import bombchicken.animator.PopsAnimator;
import static bombchicken.animator.PopsAnimator.State.BIGCHICKEN;
import static bombchicken.animator.PopsAnimator.State.ELEVATORWORK;
import static bombchicken.animator.PopsAnimator.State.WATER;
import bombchicken.camera.Camera;
import bombchicken.camera.MapInformation;
import bombchicken.controllers.AudioResourceController;
import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Boom;
import static bombchicken.gameobj.Boom.State.*;
import bombchicken.gameobj.Chicken;
import static bombchicken.gameobj.Chicken.Color.*;
import static bombchicken.gameobj.Chicken.State.*;
import bombchicken.gameobj.Elevator;
import bombchicken.gameobj.Floor;
import bombchicken.gameobj.FriedChicken;

import static bombchicken.gameobj.Floor.Type.WALL1;
import bombchicken.gameobj.Rect;
import bombchicken.gameobj.Water;
import bombchicken.menu.BackgroundType;
import bombchicken.menu.Button;
import bombchicken.menu.EditText;
import bombchicken.menu.Label;
import bombchicken.menu.PopupWindowScene;
import bombchicken.menu.PopupWindowScene.Type;
import bombchicken.menu.Style;
import bombchicken.menu.*;
import bombchicken.utils.CommandSolver;
import bombchicken.utils.Delay;
import bombchicken.utils.FontLoader;
import bombchicken.utils.Global;
import bombchicken.utils.MouseTriggerImpl;
import bombchicken.utils.Path;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class RoomScene extends Scene { //大廳

    private EditText edit;
    private Image background; //背景
    private PopupWindowScene exitPop;//離開選項彈出視窗
    private boolean popUp;//彈出視窗上選項
    private boolean popDown;//彈出視窗下選項
    private Chicken chickenButton; //彈出框
    private PopupWindowScene teachPop;//教學彈出視窗
    private boolean firstTeach = true; //教學視窗彈出過沒

    private ArrayList<Boom> bArr;
    private ArrayList<Chicken> cArr;
    private ArrayList<Floor> fArr;
    private ArrayList<Water> wArr;
    private ArrayList<Elevator> eArr;
    private ArrayList<FriedChicken> fcArr;
    private AudioResourceController arc; 
    private Delay nextSceneDelay;
    private String stage;//場景
    public final int grivaty = 5;//重力
    private boolean receiveNextScene;
    private ArrayList<Camera> cameraArr;

    @Override
    public void sceneBegin() {
        MapInformation.setMapInfo(0, 0, 3000, 2000);
            background = SceneController.instance().irc().tryGetImage(
                    new Path().img().backgrounds().mobaRoom());
        //離開彈出視窗
        exitPop = new PopupWindowScene(0, 0, 1200, 680, Type.EXIT);
        popUp = true;//預設彈出視窗的選項在上方
        popDown = false;
        chickenButton = new Chicken(490, 460, Global.UNIT_X, Global.UNIT_Y);
        chickenButton.setState(Chicken.State.WALK, ActorAnimator.State.WALK);
        chickenButton.setInMenu(popUp);
//        //教學
//        teachPop = new PopupWindowScene(0, 0, 1200, 680, Type.TEACH);
//        teachPop.sceneBegin();
//        teachPop.show();

        //音樂
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
//        arc.loop(new Path().sound().BackGroundMusic().Menu(), 10); // 放背景音樂

        cArr = new ArrayList<>();
        cArr.add(new Chicken(400, 240, Global.UNIT_X, Global.UNIT_Y));
        cArr.get(0).setState(BIRTH, ActorAnimator.State.BIRTH);
        fArr = new ArrayList<>();
        fArr.add(new Floor(570, 590, Global.SCREEN_X + 100, 100, WALL1)); //下
        fArr.add(new Floor(-40, 0, 60, 1200, WALL1)); //左
        fArr.add(new Floor(1260, 0, 60, 1200, WALL1)); //右
        wArr = new ArrayList<>();
        wArr.add(new Water(195, 612, Global.UNIT_X / 2, Global.UNIT_Y * 4, WATER)); //左
        wArr.add(new Water(981, 613, Global.UNIT_X / 2, Global.UNIT_Y * 4, WATER)); //右
        wArr.add(new Water(584, 265, Global.UNIT_X * 7, 550, BIGCHICKEN)); //大雞
        bArr = new ArrayList<>();
        eArr = new ArrayList<>();
        fcArr=new ArrayList<>();
        cameraArr = new ArrayList<>();
        eArr.add(new Elevator(1115, 476, Global.UNIT_X * 4 / 3, 120));//電梯右           
        eArr.add(new Elevator(65, 476, Global.UNIT_X * 4 / 3, 120));//電梯左

        //炸雞生成
        fcArr.add(new FriedChicken(Global.random(400, 800), 500,63*5/4, Global.UNIT_Y*5/4,FriedChicken.Type.WALK));
        fcArr.add(new FriedChicken(Global.random(200, 900), 500,63*5/4, Global.UNIT_Y*5/4,FriedChicken.Type.WALK));
        fcArr.add(new FriedChicken(Global.random(200, 900), 500,63*5/4, Global.UNIT_Y*5/4,FriedChicken.Type.WALK));
        fcArr.get(0).setState(EnemyAnimator.State.WALKL);
        
        nextSceneDelay = new Delay(180);
        nextSceneDelay.play();

        cameraArr.add(new Camera.Builder(Global.SCREEN_X, Global.SCREEN_Y)
                .setChaseObj(cArr.get(0), 10, 10) //綁定物件，填入鏡頭追焦速度
                .setCameraWindowLocation(0, 0)
                .gen());

    }

    @Override
    public void sceneEnd() {
        background = null;
//        label = null;
//        button = null;
//        edit = null;
        fArr = null;
        bArr = null;
        wArr = null;
      fcArr = null;
        eArr = null;
        exitPop = null;
//        teachPop = null;
        chickenButton = null;
//        arc.stop(new Path().sound().BackGroundMusic().Menu()); // 結束背景音樂
        SceneController.instance().irc().clear();
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < fArr.size(); i++) {
            fArr.get(i).paint(g);
        }
        g.drawImage(background, -10, 0, null);

        wArr.get(2).paint(g); //畫大雞

        for (int i = 0; i < eArr.size(); i++) {
            eArr.get(i).paint(g);
        }
        for (int i = 0; i < fcArr.size(); i++) {
        	fcArr.get(i).setRoom(true);
            if (cameraArr.get(0).isCollision(fcArr.get(i))) {
            	fcArr.get(i).paint(g);
            }
        }
        for (int i = 0; i < bArr.size(); i++) {
            if (bArr.get(i).getState() == Boom.State.EXPLODE) {
                cameraArr.get(0).cameraShake();
            }
            bArr.get(i).paint(g);
        }
        for (int i = 0; i < cArr.size(); i++) {
            cArr.get(i).paint(g);
            g.setColor(Color.WHITE);  //雞的名字顯示
            g.setFont(FontLoader.Retro8(20));
            g.drawString(cArr.get(i).getName(), cArr.get(i).painter().left() + 10, cArr.get(i).painter().top() - 10);
        }
        for (int i = 0; i < wArr.size(); i++) {
            if (i == 2) {
                continue;
            }
            wArr.get(i).paint(g);
        }

        if (exitPop.isShow()) {
            exitPop.paintWindow(g);
            chickenButton.paint(g); //選項框
            g.setFont(FontLoader.Retro8(50));
            g.setColor(Color.BLACK);
            g.drawString("Continue", 490, 290);
            g.drawString("Exit", 550, 400);
        }
//        if (teachPop.isShow()) {
//            teachPop.paintWindow(g);
//        }

    }

    @Override
    public void update() {
        System.out.println(cArr.get(0).getName());
        chickenUpdate();
        chickenButton.update();
        changeScene(); // 換場景判斷
        bombUpdate();

        for (int i = 0; i < eArr.size(); i++) {
            eArr.get(i).update();
        }
        for (int i = 0; i < wArr.size(); i++) {
            wArr.get(i).update();
        }
        for (int i = 0; i < fcArr.size(); i++) {
        	fcArr.get(i).update();
        	if (fcArr.get(i).getRemove()) {
        		fcArr.remove(i);
        		 fcArr.add(new FriedChicken(Global.random(200, 1100), 500,63*5/4, Global.UNIT_Y*5/4,FriedChicken.Type.WALK));
                break;
        	}
        }
    }

    private void chickenUpdate() {
        for (int j = 0; j < cArr.size(); j++) {
            if (!cArr.get(j).getReadyToChange()) { // if you are not ready to move then you go to update
                // 到定點搭電梯
                for (int i = 0; i < eArr.size(); i++) {
                    if (cArr.get(j).touch(eArr.get(i))) { // 如果雞碰到電梯
                        if (eArr.get(i).getState() == PopsAnimator.State.ELEVATORSTOP) {
                        	PopsAnimator.setElevatorColor(WHITE);
                            eArr.get(i).setState(PopsAnimator.State.ELEVATORWORK);
                        }
                      //右邊電梯 雙人
                        if (i == 0) {
                            stage="PvP";
                        }
                      //左邊電梯 人
                        if (i == 1) {
                        	stage="PvE";
                        }
                    }
                    if (eArr.get(i).inElevator() && cArr.get(j).touch(eArr.get(i))) { // 如果電梯動畫播放到了雞進電梯時，要加碰撞判定要不然下一次update時cArr[0]就會被傳走（for迴圈從頭開始跑的關係）
                        cArr.get(j).setReadyToChange(true); // 設為死亡狀態
                        cArr.get(j).setPosition(Global.random(-100, 5000), -2000); // 這位置到畫面外並停止更新
//                        System.out.println(j);
//                        eArr.get(i).setinElevator(false);  // 進去馬上改 false 免得下一隻雞進判斷時還是true
                        break;
                    }
                }

                if (!cArr.get(j).getNextState()) { // 如果 c 無法更新狀態
                    cArr.get(j).update(); // 為了要讓delay count++
                    if (cArr.get(j).getState() == LAYBOMB && cArr.get(j).getNextState()) { // 更新完剛好可以更改狀態並且狀態為 LAYBOMB 就接下蛋動作（修正還未下蛋就更改狀態的問題）
                        bArr.add(new Boom(cArr.get(j).collider().centerX(), cArr.get(j).collider().centerY(), Global.UNIT_X, Global.UNIT_Y));
                        cArr.get(j).translateY(-Global.UNIT_Y - 6); //  雞向上位移，要是 - 的 ( - 6 為固定常數勿改)
                    }
                    if (cArr.get(j).getState() == DEAD && cArr.get(j).getNextState() && cArr.get(j).getLifeCount() > 0) {
                        cArr.get(j).setPosition(Global.random(200, 900), Global.random(50, 300)); // 不設置重生點，使其在固定範圍內隨機出生
                        cArr.get(j).setUntouchable();
                    }
                    continue; // now we got 2 chicken so we can't use return ,return will skip second chicken's update
                }

                cArr.get(j).setState(DROP, ActorAnimator.State.DROP);

                cArr.get(j).translateY(grivaty); // 重力下墜

                // 判斷死亡事件
                for (int i = 0; i < bArr.size(); i++) { //被炸彈炸死
                    if (cArr.get(j).touch(bArr.get(i)) && bArr.get(i).getState() == EXPLODE && !cArr.get(j).untouchable()) {
                        cArr.get(j).getState().die(cArr.get(j));
                        cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1); // 死了加回來
                        break; // 觸發後即不會再觸發的狀況加上 break 以節省效能（以下皆為此操作）
                    }
                }

                // 下碰事件
                for (int i = 0; i < cArr.size(); i++) {
                    if (i != j) { // 排除判斷自己
                        if (cArr.get(j).touchBottom(cArr.get(i))) { // 雞雞間的下碰撞
                            cArr.get(j).getState().touchBottom(cArr.get(j));
                        }
                    }
                }
                for (int i = 0; i < fArr.size(); i++) { // 下碰地板
                    if (cArr.get(j).touchBottom(fArr.get(i))) {
                        cArr.get(j).getState().touchBottom(cArr.get(j));
                        break;
                    }
                }
                for (int i = 0; i < bArr.size(); i++) { // 下碰炸彈
                    if (cArr.get(j).touchBottom(bArr.get(i))) {
                        cArr.get(j).getState().touchBottom(cArr.get(j));
//                        System.out.println("cd");
                        break;
                    }
                }

                cArr.get(j).update(); // 更新有沒有左右位移

                // 判斷左右碰撞 
                // 主角碰撞地板
                for (int i = 0; i < fArr.size(); i++) { // 每個找一次
                    //判斷走路撞到右邊牆壁
                    if (cArr.get(j).touchRight(fArr.get(i))) { // 在地板走撞到的話直接回覆上一動
                        cArr.get(j).getState().touchFloor(cArr.get(j));
                        break;
                    }
                    //判斷走路撞到左邊牆壁
                    if (cArr.get(j).touchLeft(fArr.get(i))) {
                        cArr.get(j).getState().touchFloor(cArr.get(j));
                        break;
                    }
                }

                // 主角撞炸彈
                for (int i = 0; i < bArr.size(); i++) {
                    if (cArr.get(j).touchRight(bArr.get(i)) && bArr.get(i).getState() != EXPLODE) { //&& cArr.get(j).getState() == WALK  拉掉此行的原因是讓雞ＳＴＩＬＬ時也會觸發飛行中的炸彈
                        cArr.get(j).getState().touchBoom(cArr.get(j)); //撞到炸彈，主角狀態設為推炸彈
                        bArr.get(i).getState().beenTouchLeft(bArr.get(i));
//                        System.out.println("cr");
                    }
                    if (cArr.get(j).touchLeft(bArr.get(i)) && bArr.get(i).getState() != EXPLODE) { //&& cArr.get(j).getState() == WALK
                        cArr.get(j).getState().touchBoom(cArr.get(j));
                        bArr.get(i).getState().beenTouchRight(bArr.get(i));
//                        System.out.println("cl");
                    }
                }

                cArr.get(j).setReRect(cArr.get(j).collider()); // 儲存現在資訊
            }
        }
    }

    private void bombUpdate() {
        // 炸彈的碰撞
        for (int n = 0; n < bArr.size(); n++) { // 炸彈的碰撞(
            for (int i = 0; i < bArr.size(); i++) { // 判斷是否要跟著左移右移
                boolean leftJudg = bArr.get(n).collider().left() >= bArr.get(i).collider().left() && bArr.get(n).collider().left() <= bArr.get(i).collider().right(); // 左邊極限值落在obj的左極極右極之間
                boolean rightJudg = bArr.get(n).collider().right() >= bArr.get(i).collider().left() && bArr.get(n).collider().right() <= bArr.get(i).collider().right(); // 右邊極限值落在obj的左極極右極之間
                boolean onTopY = bArr.get(n).collider().bottom() < bArr.get(i).collider().top();
                boolean onTopX = leftJudg || rightJudg;
                if (n == i) {
                    continue;
                }
                //推下面炸彈上面炸彈會一起動
                if (bArr.get(n).getState() == THROB && onTopY && onTopX && bArr.get(i).getState() != EXPLODE) { // 如果這顆炸彈未爆且在上方且下方炸彈不為爆炸
                    bArr.get(n).setState(bArr.get(i).getState(), bArr.get(i).getAnimator().getState());
                }
            }

            bArr.get(n).update(); // 先更新

            //左右飛出去時不受地心引力影響
            if (bArr.get(n).getState() != FLYRIGHT && bArr.get(n).getState() != FLYLEFT && bArr.get(n).getState() != EXPLODE) {
                bArr.get(n).translateY(grivaty); //地心引力
            }
            for (int i = 0; i < fArr.size(); i++) { // 在地板下碰撞（在地板上）    
                if (bArr.get(n).touchBottom(fArr.get(i)) && bArr.get(n).getState() != EXPLODE) {
                    bArr.get(n).getState().beenTouchBottom(bArr.get(n));
                    break; // 觸發後即不會在觸發的狀況加上 break 以節省效能（以下皆為此操作）
                }
            }

            for (int i = 0; i < bArr.size(); i++) { // 炸彈與炸彈間的下碰撞
                if (n != i && bArr.get(n).touchBottom(bArr.get(i))) {
                    bArr.get(n).getState().beenTouchBottom(bArr.get(n));
                    break;
                }
            }

            for (int i = 0; i < fArr.size(); i++) { // 炸彈右碰撞牆
                if (bArr.get(n).touchRight(fArr.get(i))) {
                    bArr.get(n).getState().beenTouchRight(bArr.get(n));
//                    System.out.println("rf");
                    break;
                }
            }
            for (int i = 0; i < fArr.size(); i++) { // 炸彈左碰撞牆
                if (bArr.get(n).touchLeft(fArr.get(i))) {
                    bArr.get(n).getState().beenTouchLeft(bArr.get(n));
//                    System.out.println("lf");
                    break;
                }
            }

            for (int i = 0; i < bArr.size(); i++) { // 炸彈右碰炸彈
                if (n != i && bArr.get(n).touchRight(bArr.get(i)) && bArr.get(i).getState() != EXPLODE && bArr.get(n).getState() != EXPLODE) { // 避免卡彈的情況發生
                    bArr.get(n).getState().beenTouchRight(bArr.get(n));
                    bArr.get(i).setState(EXPLODE, PopsAnimator.State.EXPLODE);
//                    System.out.println("rb");
                }
            }
            for (int i = 0; i < bArr.size(); i++) { // 炸彈左碰炸彈
                if (n != i && bArr.get(n).touchLeft(bArr.get(i)) && bArr.get(i).getState() != EXPLODE && bArr.get(n).getState() != EXPLODE) { // 避免卡彈的情況發生// 避免卡彈的情況發生
                    bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                    bArr.get(i).setState(EXPLODE, PopsAnimator.State.EXPLODE);
//                    System.out.println("lb");
                }
            }
            
            for (int i = 0; i < fcArr.size(); i++) { 
                if (bArr.get(n).touch(fcArr.get(i))) {
                    if (bArr.get(n).getState() == EXPLODE ) {
                    	fcArr.add(new FriedChicken(fcArr.get(i).getX(),fcArr.get(i).getY(),75,fcArr.get(i).getHeight(),FriedChicken.Type.ROAST));
                    	fcArr.remove(i);
                    	break;
                    }
                }
            }
            
            bArr.get(n).setReRect(bArr.get(n).collider()); // 儲存現在資訊
        }

        for (int i = 0; i < bArr.size(); i++) {
            if (bArr.get(i).isDelete()) {
                bArr.remove(i);
            }
        }
    }

    private void changeScene() {
        // 進電梯
        boolean temp = false;
        temp =  cArr.get(0).getReadyToChange(); 
        
            if (temp && nextSceneDelay.count() || receiveNextScene) { 
            	//進電梯後進下一場景，延遲3秒等電梯動畫跑完再進
            if (stage.equals("PvP")) { //換關
                SceneController.instance().change(new PvPScene1());
            } else if (stage.equals("PvE")) {
                SceneController.instance().change(new PvEScene1());
            }
        }
    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return null;
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return new CommandSolver.KeyListener() {

            @Override
            public void keyPressed(int commandCode, long trigTime) {
                if (cArr.get(0).collider().bottom() >= -1) { // 限定雞的空氣牆和讓雞在播放電梯動畫時不能移動
                    if (cArr.get(0).collider().centerX() <= 64) {
                        cArr.get(0).setPosition(64, cArr.get(0).collider().centerY());
                    }
                    if (cArr.get(0).collider().centerX() >= 1115) {
                        cArr.get(0).setPosition(1115, cArr.get(0).collider().centerY());
                    }
                    if (cArr.get(0).collider().centerX() > 66 && cArr.get(0).collider().centerX() < 1113 && !exitPop.isShow()) {
                        cArr.get(0).keyPressed(commandCode, trigTime);
                    }

                }

//                cArr.get(0).keyPressed(commandCode, trigTime);
                if (commandCode == Global.P1LEFT && !exitPop.isShow()) {
                    ArrayList<String> arrs = new ArrayList<>();
                    arrs.add(cArr.get(0).collider().centerX() + "");
                    arrs.add(cArr.get(0).collider().centerY() + "");
                }
                if (commandCode == Global.P1RIGHT && !exitPop.isShow()) {
                    ArrayList<String> arrs = new ArrayList<>();
                    arrs.add(cArr.get(0).collider().centerX() + "");
                    arrs.add(cArr.get(0).collider().centerY() + "");
                }
                
//                if (commandCode == Global.ENTER) { // 強制進場景
//                    if (stage.equals("PvE")) { //換關
//                        SceneController.instance().change(new TestScene());
//                    } else if (stage.equals("PvP")) {
//                        SceneController.instance().change(new TestScene());
//                    }
//                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                if (exitPop.isShow()) {
                    if (commandCode == Global.P1DOWN) {  //彈出視窗
                        popDown = true;
                        popUp = false;
                        chickenButton.setPosition(500, 380);
                    }
                    if (commandCode == Global.P1UP) {  //彈出視窗
                        popUp = true;
                        popDown = false;
                        chickenButton.setPosition(440, 280);
                    }
                    if (commandCode == Global.ENTER) {
                        if (popUp) { //選擇繼續
                            exitPop.hide();
                            exitPop.sceneEnd(); //離開彈出視窗  isShow = false;  
                            popDown = false;
                            popUp = false;
                            chickenButton.setPosition(-1000000, -1000000); //關掉彈出視窗時選相框移走
                        } else if (popDown) { //選擇離開
                            SceneController.instance().change(new MenuScene());
                        }
                    }
                }

                if (commandCode == Global.ESC) {
//                    if (firstTeach) { //第一次按esc，關閉教學    
//                        teachPop.hide();
//                        teachPop.sceneEnd();
//                        firstTeach = false;
//                    } else 
                    	if (!exitPop.isShow() && !teachPop.isShow()) { //之後按 esc 開啟離開選項，或在教學頁面離開
                        chickenButton.setPosition(440, 280);
                        exitPop.sceneBegin();
                        exitPop.show();
                    } else if (teachPop.isShow()) {
                        teachPop.hide();
                        teachPop.sceneEnd();
                    }
                }
//                if (commandCode == Global.TEACH) { //按 T 跑出教學
//                    teachPop.sceneBegin();
//                    teachPop.show();
//                }
                if (commandCode == Global.P1DOWN && cArr.get(0).getState() != DROP && cArr.get(0).getState() != BOOMHEAD && !exitPop.isShow()) { // 當按下空白鍵時,後面的判斷是用來避免無限撞頭
                    if (cArr.get(0).collider().centerX() > 66 && cArr.get(0).collider().centerX() < 1113) {
                        ArrayList<String> arrs = new ArrayList<>();
                        arrs.add(cArr.get(0).collider().centerX() + "");
                        arrs.add(cArr.get(0).collider().centerY() + "");
                        Chicken temp = new Chicken(cArr.get(0).collider().centerX(), cArr.get(0).collider().centerY(), cArr.get(0).collider().width(), cArr.get(0).collider().height()); // 利用替身攻擊來判斷移動後會不會撞到
                        temp.translateY(-Global.UNIT_Y - 0); // 替身先移動

                        for (int i = cArr.size() - 1; i > 0; i--) { // 由於是由下向上位移，所以碰撞判定要由下向上判斷，否則會發生穿過事件
                            if (!cArr.get(i).getDead() && temp.touchTop(cArr.get(i))) { // 雞上碰雞
                                cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                                break;
                            }
                        }

                        for (int i = fArr.size() - 1; i >= 0; i--) {
                            if (temp.touchTop(fArr.get(i))) {
                                cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                                cArr.get(0).painter().setTop(fArr.get(i).collider().bottom() - 1); // 設定為看起來剛好撞到
                                cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                                cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                                cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); //
                                break;
                            }
                        }
                        for (int i = bArr.size() - 1; i >= 0; i--) { // 撞炸彈
                            if (temp.touchTop(bArr.get(i)) && bArr.get(i).getState() != EXPLODE) { // 限制為非爆炸狀態，不然會超出地圖
                                cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                                cArr.get(0).painter().setTop(bArr.get(i).painter().bottom() - 1); // 設定為看起來剛好撞到
                                cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                                cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                                cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); //
                                break;
                            }
                        }
                        if (cArr.get(0).getNextState()) { // 排除狀態後判斷是否能更變狀態
                            cArr.get(0).setState(LAYBOMB, ActorAnimator.State.LAYBOMB);
                        }
                    }
                    cArr.get(0).setReRect(cArr.get(0).collider()); //儲存現在資訊
                }

            }

            @Override
            public void keyTyped(char c, long trigTime) {

            }
        };

    }

}
