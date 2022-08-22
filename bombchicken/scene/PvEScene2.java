package bombchicken.scene;

import bombchicken.animator.ActorAnimator;
import bombchicken.animator.ActorAnimator.State;
import bombchicken.animator.PopsAnimator;
import static bombchicken.gameobj.Boom.State.*;
import bombchicken.camera.Camera;
import bombchicken.camera.MapInformation;
import bombchicken.camera.SmallMap;
import bombchicken.controllers.AudioResourceController;
import bombchicken.controllers.SceneController;
import bombchicken.gameobj.*;
import static bombchicken.gameobj.Chicken.Color.*;
import static bombchicken.gameobj.Chicken.State.*;
import bombchicken.menu.Button;
import bombchicken.menu.PopupWindowScene;
import bombchicken.menu.PopupWindowScene.Type;
import bombchicken.utils.CommandSolver;
import bombchicken.utils.CommandSolver.KeyListener;
import bombchicken.utils.CommandSolver.MouseState;
import bombchicken.utils.Global;
import static bombchicken.utils.Global.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import bombchicken.utils.Delay;
import bombchicken.utils.FontLoader;
import bombchicken.utils.Path;
import java.awt.Font;
import java.awt.event.KeyEvent;
import maploader.MapInfo;
import maploader.MapLoader;

public class PvEScene2 extends Scene {

    public final int grivaty = 5;//重力
    private ArrayList<Camera> cameraArr;
    private ArrayList<Chicken> cArr; // 雞列
    private ArrayList<Floor> fArr;
    private ArrayList<Boom> bArr; 
    private ArrayList<Spike> sArr; 
    private ArrayList<Belt> beltArr;
    private ArrayList<Gem> gArr;
    private ArrayList<Grass> grassArr;
    private ArrayList<Fire> fireArr;
    private ArrayList<Transition> transArr;
    private ArrayList<Elevator> eArr;
    private ArrayList<BackGround> backGrounds;
    private ArrayList<BackGroundObject> bgArr;
    private ArrayList<Water> rebirthPointArr;
    private ArrayList<Pounder> pArr;
    private ArrayList<Door> dArr;
    private ArrayList<Pressbtn> pressBtnArr;
    private ArrayList<Cannon> cannonArr;//砲台
    private ArrayList<Worker> wArr;
    private ArrayList<FriedChicken> fcArr;
    private AudioResourceController arc; 
    private PopupWindowScene exitPop;//離開選項的彈出視窗
    private boolean popUp;//彈出視窗上選項
    private boolean popDown;//彈出視窗下選項
    private Chicken chickenButton; //彈出框選擇鍵
    private ArrayList<Chicken> results; //結算名單
    private Delay nextSceneDelay;
    private boolean receiveNextScene;
    private Image gemLabel; //右上角寶石圖示
    private int gemNum;//吃到的寶石數量
    
//   private int mapWidth = 2560;
//   private int mapHeight = 2170;
   
   private int mapWidth = 50000;
   private int mapHeight = 5000;
   private int pX;
   private int pY;
   
    @Override
    public void sceneBegin() {
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
        arc.stop(new Path().sound().BackGroundMusic().Menu());
        arc.loop(new Path().sound().BackGroundMusic().LavaThemeLayer2(), 10); // 放背景音樂     
        MapInformation.setMapInfo(0, 0, mapWidth, mapHeight);
        cArr = new ArrayList<>();
        bArr = new ArrayList<>();
        fArr = new ArrayList<>();
        sArr = new ArrayList<>();
        gArr = new ArrayList<>();
        dArr = new ArrayList<>();
        grassArr = new ArrayList<>();
        beltArr = new ArrayList<>();
        transArr = new ArrayList<>();
        eArr = new ArrayList<>();
        pArr = new ArrayList<>();
        pressBtnArr = new ArrayList<>();
        bgArr = new ArrayList<>();
        cannonArr = new ArrayList<>();
        rebirthPointArr = new ArrayList<>();
        backGrounds = new ArrayList<>();
        fireArr = new ArrayList<>();
        results = new ArrayList<>();
        cameraArr = new ArrayList<>();
        wArr=new ArrayList<>();
        fcArr=new ArrayList<>();
        
        backGrounds.add(new BackGround(0, 0, mapWidth, mapHeight, BackGround.Type.FACTORY));

        exitPop = new PopupWindowScene(0, 0, 1200, 680, Type.EXIT); //設定彈出視窗座標以及大小
        popUp = true;//預設彈出視窗的選項在上方
        popDown = false;
        chickenButton = new Chicken(490, 460, Global.UNIT_X, Global.UNIT_Y);
        chickenButton.setState(Chicken.State.WALK, ActorAnimator.State.WALK);

        mapInitialize(); //初始化地圖
        cArr.add(new Chicken(130, 560, Global.UNIT_X, Global.UNIT_Y));
//        cArr.add(new Chicken(2465,570, Global.UNIT_X, Global.UNIT_Y));
        cArr.get(0).setName("gaga");

        for (int i = 0; i < cArr.size(); i++) {
            cArr.get(i).setState(BIRTH, ActorAnimator.State.BIRTH);
        }
        
        //寶石標示
        gemLabel = SceneController.instance().irc().tryGetImage(
                new Path().img().props().gem().gemLabel());
        gemNum = 0; //寶石數
        
        //復活點生成
        rebirthPointArr.add(new Water(130,560, Global.UNIT_X, Global.UNIT_Y, PopsAnimator.State.REBIRTHPOINT));
        rebirthPointArr.add(new Water(1600,1280, Global.UNIT_X, Global.UNIT_Y, PopsAnimator.State.REBIRTHPOINTD));
        rebirthPointArr.add(new Water(2810,1220, Global.UNIT_X, Global.UNIT_Y, PopsAnimator.State.REBIRTHPOINTD));
        rebirthPointArr.add(new Water(2465,570, Global.UNIT_X, Global.UNIT_Y, PopsAnimator.State.REBIRTHPOINTD));
        for(int i=1;i<rebirthPointArr.size();i++) {
        	//除了第一個復活點全都禁用
        	rebirthPointArr.get(i).setCanUse(false);
        }
        
        //寶石生成
        gArr.add(new Gem(3075,910,UNIT_X, UNIT_Y, PopsAnimator.State.GEM));
        gArr.add(new Gem(1985,250,UNIT_X, UNIT_Y, PopsAnimator.State.GEM));
        gArr.add(new Gem(2240,250,UNIT_X, UNIT_Y, PopsAnimator.State.GEM));
        
        //敵人生成
//        wArr.add(new Worker(616, 472,77*5/4, Global.UNIT_Y*5/4,Worker.Type.HAMMER));
//        wArr.add(new Worker(616, 472,74*5/4, Global.UNIT_Y*5/4,Worker.Type.PHONE));
//        wArr.add(new Worker(616, 472,79*5/4, Global.UNIT_Y*5/4,Worker.Type.SWAB));
//        wArr.add(new Worker(616, 472,Global.UNIT_X *3/2, Global.UNIT_Y*3/2,Worker.Type.SAUCE));
          wArr.add(new Worker(291, 377,64*5/4, Global.UNIT_Y*5/4,Worker.Type.PIG));
          wArr.add(new Worker(2200, 600,79*5/4, Global.UNIT_Y*5/4,Worker.Type.SWAB));
          wArr.add(new Worker(1761, 600,74*5/4, Global.UNIT_Y*5/4,Worker.Type.PHONE));
          wArr.add(new Worker(1922, 317,64*5/4, Global.UNIT_Y*5/4,Worker.Type.PIG));
          wArr.add(new Worker(3214, 1100,64*5/4, Global.UNIT_Y*5/4,Worker.Type.PIG));
          wArr.add(new Worker(2240, 346,64*5/4, Global.UNIT_Y*5/4,Worker.Type.HAMMER));
          wArr.add(new Worker(3071, 1360,Global.UNIT_X *3/2, Global.UNIT_Y*3/2,Worker.Type.SAUCE));
        
        
      //炸雞生成
      fcArr.add(new FriedChicken(529, 1306,63*5/4, Global.UNIT_Y*5/4,FriedChicken.Type.WALK));
        
        
        //衝槌開始時間
        pArr.get(0).setOpenDelay(Global.random(60, 70));
        pArr.get(1).setOpenDelay(Global.random(60, 70));
        pArr.get(2).setOpenDelay(Global.random(90, 95));
        pArr.get(3).setOpenDelay(Global.random(90, 95));
        pArr.get(4).setOpenDelay(Global.random(90, 100));
        pArr.get(5).setOpenDelay(Global.random(120, 125));
        pArr.get(6).setOpenDelay(Global.random(120, 125));
        pArr.get(7).setOpenDelay(Global.random(120, 130));
        pArr.get(8).setOpenDelay(Global.random(180, 185));
        pArr.get(9).setOpenDelay(Global.random(180, 185));
        pArr.get(10).setOpenDelay(Global.random(180, 185));
        pArr.get(11).setOpenDelay(Global.random(190, 195));
        
        eArr.add(new Elevator(1374, 453, Global.UNIT_X * 4 / 3, 125));
        nextSceneDelay = new Delay(180);
        nextSceneDelay.play();
        cameraArr.add(new Camera.Builder(Global.SCREEN_X , Global.SCREEN_Y)
                .setChaseObj(cArr.get(0), 10, 10) //綁定物件，填入鏡頭追焦速度
                .setCameraWindowLocation(0, 0)
                .gen());
    }

    @Override
    public void sceneEnd() {
        cArr = null;
        fArr = null;
        bArr = null;
        cameraArr = null;
        sArr = null;
        gArr = null;
        dArr = null;
        grassArr = null;
        pArr = null;
        transArr=null;
        eArr = null;
        pressBtnArr = null;
        bgArr = null;
        beltArr = null;
        backGrounds = null;
        fireArr = null;
        gemLabel = null;
        exitPop = null;
        chickenButton = null;
        results = null;
        cannonArr = null;
        wArr = null;
        fcArr = null;
        SceneController.instance().irc().clear();
//        arc.stop(new Path().sound().BackGroundMusic().LavaThemeLayer2()); // 結束背景音樂
    }

    @Override
    public void paint(Graphics g) {
        for (int n = 0; n < cameraArr.size(); n++) {
            cameraArr.get(n).start(g);
            for (int i = 0; i < backGrounds.size(); i++) {
                if (cameraArr.get(n).isCollision(backGrounds.get(i))) {
                    backGrounds.get(i).paint(g);
                }
            }
            
            for (int i = 0; i < eArr.size(); i++) {
                eArr.get(i).paint(g);
            }

            for (int i = 0; i < pArr.size(); i++) {
                if (cameraArr.get(n).isCollision(pArr.get(i))) {
                    pArr.get(i).paint(g);
                }
            }            
            for (int i = 0; i < bgArr.size(); i++) {
                if (cameraArr.get(n).isCollision(bgArr.get(i))) {
                	bgArr.get(i).paint(g);
                }
            }
            for (int i = 0; i < dArr.size(); i++) {
                if (cameraArr.get(n).isCollision(dArr.get(i))) {
                	dArr.get(i).paint(g);
                }
            }
            
            for (int i = 0; i < fArr.size(); i++) {
                if (cameraArr.get(n).isCollision(fArr.get(i))) {
                    fArr.get(i).paint(g);
                }
            }
            
            for (int i = 0; i < transArr.size(); i++) {
                if (cameraArr.get(n).isCollision(transArr.get(i))) {
                	transArr.get(i).paint(g);
                }
            }
            
            for (int i = 0; i < grassArr.size(); i++) {
                if (cameraArr.get(n).isCollision(grassArr.get(i))) {
                	grassArr.get(i).paint(g);
                }
            }
            
            for (int i = 0; i < pressBtnArr.size(); i++) {
                if (cameraArr.get(n).isCollision(pressBtnArr.get(i))) {
                	pressBtnArr.get(i).paint(g);
                }
            }
            
            for (int i = 0; i < fcArr.size(); i++) {
                if (cameraArr.get(n).isCollision(fcArr.get(i))) {
                	fcArr.get(i).paint(g);
                }
            }

            for (int i = 0; i < bArr.size(); i++) {
                if (cameraArr.get(n).isCollision(bArr.get(i))) {
                    bArr.get(i).paint(g);
                    if (bArr.get(i).getState() == EXPLODE) {
                        if (!bArr.get(i).getAlreadyShake(n)) {
                            cameraArr.get(n).cameraShake();
                            bArr.get(i).setAlreadyShake(true, n);
                        }
                    }
                }
            }
            
            for (int i = 0; i < sArr.size(); i++) {
                if (cameraArr.get(n).isCollision(sArr.get(i))) {
                    sArr.get(i).paint(g);
                }
            }
            for (int i = 0; i < beltArr.size(); i++) {
                if (cameraArr.get(n).isCollision(beltArr.get(i))) {
                    beltArr.get(i).paint(g);
                }
            }
            for (int i = 0; i < gArr.size(); i++) {
                if (cameraArr.get(n).isCollision(gArr.get(i))) {
                    gArr.get(i).paint(g);
                }
            }
 
            
            for (int i = 0; i < rebirthPointArr.size(); i++) {
                if (cameraArr.get(n).isCollision(rebirthPointArr.get(i))) {
                    rebirthPointArr.get(i).paint(g);
                }
            }
            for (int i = 0; i < fireArr.size(); i++) {
                if (cameraArr.get(n).isCollision(fireArr.get(i))) {
                    fireArr.get(i).paint(g);
                }
            }
            for (int i = 0; i < cannonArr.size(); i++) {
                if (cameraArr.get(n).isCollision(cannonArr.get(i))) {
                    cannonArr.get(i).paint(g);
                }
            }
            
            //enemy
            for (int i = 0; i < wArr.size(); i++) {
                if (cameraArr.get(n).isCollision(wArr.get(i))) {
                	wArr.get(i).paint(g);
                }
            }
  
            for (int i = 0; i < cArr.size(); i++) {
                if (cameraArr.get(n).isCollision(cArr.get(i))&&(!cArr.get(i).getVisibleState())) {
                    cArr.get(i).paint(g);
                }
            }
            
            cameraArr.get(n).end(g);
        }


        g.setColor(Color.WHITE);
        g.setFont(FontLoader.Retro8(25));
        //雞生命和獲得寶石數
        g.setColor(Color.WHITE);
        g.drawString(cArr.get(0).getColor() + " life: " , 990, 30);
        g.setColor(Color.YELLOW);
        g.drawString(cArr.get(0).getLifeCount() + "", 1150, 30);
        g.drawString(cArr.get(0).getGemNumber() + "", 1150, 76);
        g.drawImage(gemLabel, 1100, 50, Global.UNIT_X / 2, Global.UNIT_Y / 2, null);
        
        g.setFont(FontLoader.Retro8(10));
        g.setColor(Color.WHITE);
        int x = pX + cameraArr.get(0).collider().left();
        int y = pY + cameraArr.get(0).collider().top();
        g.drawString("("+x+","+y+" )" , pX, pY);


        if (exitPop.isShow()) {
            exitPop.paintWindow(g);
            chickenButton.paint(g); //選項框
            g.setFont(FontLoader.Retro8(50));
            g.setColor(Color.BLACK);
            g.drawString("Continue", 490, 290);
            g.drawString("Exit", 550, 400);

        }
    }

    @Override
    public void update() {
        for (int i = 0; i < cameraArr.size(); i++) {
            cameraArr.get(i).update();
        }
        chickenButton.update();
        chickenUpdate();
        
        for (int i = 0; i < eArr.size(); i++) {
            eArr.get(i).update();
        }
        
//         判斷勝利事件
        gameOver(); // 換 scene 判斷
        changeScene();
        
        bombUpdate();
        
        for (int i = 0; i < gArr.size(); i++) {
            gArr.get(i).update();
            if (gArr.get(i).isDelete()) {
                gArr.remove(i);
                break;
            }
        }
        
      for (int i = 0; i < beltArr.size(); i++) {
      beltArr.get(i).update();
  }
      
    for (int i = 0; i < pArr.size(); i++) {
    pArr.get(i).update();
}
    
    for (int i = 0; i < cannonArr.size(); i++) {
      cannonArr.get(i).update();
      if (cannonArr.get(i).canShoot()) {
          switch (cannonArr.get(i).direction()) {
              case LEFT:
                  fireArr.add(new Fire(cannonArr.get(i).collider().centerX(), cannonArr.get(i).collider().centerY(), Global.UNIT_X * 4, Global.UNIT_Y, PopsAnimator.State.FIREFLYLEFT));
                  break;
              case RIGHT:
                  fireArr.add(new Fire(cannonArr.get(i).collider().centerX(), cannonArr.get(i).collider().centerY(), Global.UNIT_X * 4, Global.UNIT_Y, PopsAnimator.State.FIREFLYRIGHT));
                  break;
          }
      }
  }
  fireUpdate();
        
        for (int i = 0; i < sArr.size(); i++) {
            sArr.get(i).update();
        }
        
        for (int i = 0; i < transArr.size(); i++) {
        	transArr.get(i).update();
        }
        
        for (int i = 0; i < pressBtnArr.size(); i++) {
        	pressBtnArr.get(i).update();
        }
        
        //enemy
        for (int i = 0; i < wArr.size(); i++) {
        	wArr.get(i).update();
        	if (wArr.get(i).getRemove()) {
        		wArr.remove(i);
                break;
        	}
        }
        
        for (int i = 0; i < fcArr.size(); i++) {
        	fcArr.get(i).update();
        	if (fcArr.get(i).getRemove()) {
        		fcArr.remove(i);
                break;
        	}
        }
        
    }

    public void chickenUpdate() {
        for (int j = 0; j < cArr.size(); j++) {
        	 if (!cArr.get(j).getReadyToChange()) { // if you are not ready to move then you go to update
                 // 到定點搭電梯
                 for (int i = 0; i < eArr.size(); i++) {
                     if (cArr.get(j).touch(eArr.get(i))) { // 如果雞碰到電梯
                         if (eArr.get(i).getState() == PopsAnimator.State.ELEVATORSTOP) {
                         	PopsAnimator.setElevatorColor(WHITE);
                             eArr.get(i).setState(PopsAnimator.State.ELEVATORWORK);
                         }
                     }
                     if (eArr.get(i).inElevator() && cArr.get(j).touch(eArr.get(i))) { // 如果電梯動畫播放到了雞進電梯時，要加碰撞判定要不然下一次update時cArr[0]就會被傳走（for迴圈從頭開始跑的關係）
                         cArr.get(j).setReadyToChange(true); // 設為死亡狀態
                         cArr.get(j).setVisibleState(true);
                         System.out.println(j);
                         eArr.get(i).setinElevator(false);  // 進去馬上改 false 免得下一隻雞進判斷時還是true
                         break;
                     }
                 }
        	 }
        	
            if (!cArr.get(j).getNextState()) { // 如果 c 無法更新狀態
                cArr.get(j).update(); // 為了要讓delay count++
                if (cArr.get(j).getState() == LAYBOMB && cArr.get(j).getNextState()) { // 更新完剛好可以更改狀態並且狀態為 LAYBOMB 就接下蛋動作（修正還未下蛋就更改狀態的問題）
                    bArr.add(new Boom(cArr.get(j).collider().centerX(), cArr.get(j).collider().centerY(), Global.UNIT_X, Global.UNIT_Y));
                    cArr.get(j).translateY(-Global.UNIT_Y - 6); //  雞向上位移，要是 - 的 ( - 6 為固定常數勿改)
                }
                if (cArr.get(j).getState() == DEAD && cArr.get(j).getNextState() && cArr.get(j).getLifeCount() > 0) {
                	Water temp=rebirthPointArr.get(0);
                	for(int i=rebirthPointArr.size()-1;i>=0;i--) {
                		if(rebirthPointArr.get(i).canUse()==true) {
                			temp =rebirthPointArr.get(i) ;
                			break;
                		}
                	}
                    cArr.get(j).setPosition(temp.collider().centerX(), temp.collider().centerY());
                    cArr.get(j).setUntouchable();
                }
                continue; 
            }

            //  && !cArr.get(j).getDead()
            if (cArr.get(j).getLifeCount() == 0) { // 沒命就設雞狀態
                cArr.get(j).setDead(true);
                results.add(cArr.get(j)); //先死的先加進名單
                continue; // 跳過這隻雞接下來的動作，不然會判斷錯誤dead
            }

            cArr.get(j).setState(DROP, ActorAnimator.State.DROP);

            cArr.get(j).translateY(grivaty); // 重力下墜

            // 判斷死亡事件
            for (int i = 0; i < bArr.size(); i++) { //被炸彈炸死
                if (cArr.get(j).touch(bArr.get(i)) && bArr.get(i).getState() == EXPLODE && !cArr.get(j).untouchable()) {
                    cArr.get(j).getState().die(cArr.get(j));
                    break; // 觸發後即不會再觸發的狀況加上 break 以節省效能（以下皆為此操作）
                }
            }
            for (int i = 0; i < sArr.size(); i++) { //碰到尖刺死亡
                if (cArr.get(j).touch(sArr.get(i)) && !cArr.get(j).untouchable()) {
                    cArr.get(j).getState().die(cArr.get(j));
                    break;
                }
            }
          for (int i = 0; i < pArr.size(); i++) { //碰到衝槌死亡
          if (cArr.get(j).touchTop(pArr.get(i)) && !cArr.get(j).untouchable()) {
              cArr.get(j).getState().die(cArr.get(j));
              break;
          }
      }
      for (int i = 0; i < fireArr.size(); i++) { //碰到火球死亡
          if (cArr.get(j).touch(fireArr.get(i)) && !cArr.get(j).untouchable()) {
              cArr.get(j).getState().die(cArr.get(j));
              fireArr.remove(i);
              break;
          }
      }
      for (int i = 0; i < wArr.size(); i++) { 
     	 if (cArr.get(j).touch(wArr.get(i)) && !cArr.get(j).untouchable()) {
              cArr.get(j).getState().die(cArr.get(j));
              break;
          }
     }
            
            
            // 下碰事件
            for (int i = 0; i < cArr.size(); i++) {
                if (i != j && !cArr.get(i).getDead()) { // 排除判斷自己
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
            
            for (int i = 0; i < rebirthPointArr.size(); i++) { // 啟用復活點
                if (cArr.get(j).touchBottom(rebirthPointArr.get(i))&&rebirthPointArr.get(i).canUse()==false) {
                	rebirthPointArr.get(i).setCanUse(true);
                	rebirthPointArr.get(i).setState(PopsAnimator.State.REBIRTHPOINT);
                    break;
                }
            }
            
            for (int i = 0; i < transArr.size(); i++) { 
                if (cArr.get(j).touchBottom(transArr.get(i))) {
                    cArr.get(j).getState().touchBottom(cArr.get(j));
                    switch(transArr.get(i).getType()) {
                    case PIPE: 
                    	transArr.get(i).setState(PopsAnimator.State.TRANSITIONON);
                    	break;
                    }
                }
                else if(!cArr.get(j).touchBottom(transArr.get(i))){
                    switch(transArr.get(i).getType()) {
                    case PIPE:
                      	transArr.get(i).setState(PopsAnimator.State.TRANSITIONOFF);
                    	break;
                    }
                }
            }
            
            for (int i = 0; i < dArr.size(); i++) { 
                if (cArr.get(j).touchBottom(dArr.get(i))) {
                    cArr.get(j).getState().touchBottom(cArr.get(j));
                    break;
                }
            }
            
            for (int i = 0; i < bArr.size(); i++) { // 下碰炸彈
                if (cArr.get(j).touchBottom(bArr.get(i))) {
                    cArr.get(j).getState().touchBottom(cArr.get(j));
                    break;
                }
            }
            
          for (int i = 0; i < beltArr.size(); i++) { // 下碰輸送帶
          if (cArr.get(j).touchBottom(beltArr.get(i))) {
              cArr.get(j).getState().touchBottom(cArr.get(j));
              cArr.get(j).translateX(beltArr.get(i).getMoveSpeed()); // 在輸送帶上會自動位移
              break;
          }
      }
            
          for (int i = 0; i < pressBtnArr.size(); i++) { // 下碰pressBtn
          if (cArr.get(j).touchBottom(pressBtnArr.get(i))) {
              cArr.get(j).getState().touchBottom(cArr.get(j));
              pressBtnArr.get(i).isPressed(true);
              dArr.get(i).update();
              break;
          }else {
        	  pressBtnArr.get(i).isPressed(false);
              break;
          }
      }
            for (int i = 0; i < gArr.size(); i++) { // 下碰寶石牆
                if (cArr.get(j).touchBottom(gArr.get(i))) {
                    //變成空寶箱或寶石被吃掉時可以走過
                    if (gArr.get(i).getState() != PopsAnimator.State.CHESTEMPTY && gArr.get(i).getState() != PopsAnimator.State.GEMGET) {
                        cArr.get(j).getState().touchBottom(cArr.get(j));
                    }
                    
                    if (gArr.get(i).getState() == PopsAnimator.State.CHESTOPEN && gArr.get(i).getState() != PopsAnimator.State.GEMGET) {
                    	gArr.get(i).setState(PopsAnimator.State.GEMGET);
                        cArr.get(j).setGemNumber(cArr.get(j).getGemNumber() + 5);
                        gArr.get(i).setState(PopsAnimator.State.CHESTEMPTY);
                        //5顆寶石加一條命
                        if(cArr.get(j).getGemNumber()%5==0&&cArr.get(j).getGemNumber()!=0) {
                      	  cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1);
                        }
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.GEM) { //變成寶石時可以吃掉
                        gArr.get(i).setState(PopsAnimator.State.GEMGET);
                        cArr.get(j).setGemNumber(cArr.get(j).getGemNumber() + 1);
                        //5顆寶石加一條命
                        if(cArr.get(j).getGemNumber()%5==0&&cArr.get(j).getGemNumber()!=0) {
                      	  cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1);
                        }
                        System.out.println("d");
                    }
                }
            }

            cArr.get(j).update(); // 更新有沒有左右位移

            // 雞雞碰撞(要寫在更新之後)
            for (int i = 0; i < cArr.size(); i++) {
                if (i != j) { // 排除判斷自己
                    if (cArr.get(j).touchLeft(cArr.get(i)) && !cArr.get(i).getDead()) { // 雞雞間的左碰撞
                        cArr.get(j).getState().touchFloor(cArr.get(j));
                        if (cArr.get(j).touchLeft(cArr.get(i))) { // 如果又再碰的話就再直接位移，避免黏一起的狀況發生
                            cArr.get(j).setPosition(cArr.get(j).collider().centerX() + 5, cArr.get(j).collider().centerY());
                        }
                    }
                    if (cArr.get(j).touchRight(cArr.get(i)) && !cArr.get(i).getDead()) { // 雞雞間的右碰撞
                        cArr.get(j).getState().touchFloor(cArr.get(j));
                        if (cArr.get(j).touchRight(cArr.get(i))) { // 如果又再碰的話就再直接位移，避免黏一起的狀況發生
                            cArr.get(j).setPosition(cArr.get(j).collider().centerX() - 5, cArr.get(j).collider().centerY());
                        }
                    }
                }
            }

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
            
            if (cArr.get(j).touchRight(pressBtnArr.get(1))) { // 在地板走撞到的話直接回覆上一動
                cArr.get(j).getState().touchFloor(cArr.get(j));
                pressBtnArr.get(1).isPressed(true);
                dArr.get(1).update();
                break;
            } else if (cArr.get(j).touchLeft(pressBtnArr.get(1))) { //判斷走路撞到左邊牆壁
                cArr.get(j).getState().touchFloor(cArr.get(j));
                pressBtnArr.get(1).isPressed(true);
                dArr.get(1).update();
                break;
            }else {
            	pressBtnArr.get(1).isPressed(false);
            }
            
            for (int i = 0; i < dArr.size(); i++) { 
                if (cArr.get(j).touchRight(dArr.get(i))) { // 在地板走撞到的話直接回覆上一動
                    cArr.get(j).getState().touchFloor(cArr.get(j));
                    break;
                }
                if (cArr.get(j).touchLeft(dArr.get(i))) {
                    cArr.get(j).getState().touchFloor(cArr.get(j));
                    break;
                }
            }
            
//            主角碰撞輸送帶
          for (int i = 0; i < beltArr.size(); i++) { // 每個找一次
              // 判斷走路撞到輸送帶
              if (cArr.get(j).touchRight(beltArr.get(i))) { // 在輸送帶走撞到的話直接回覆上一動
                  cArr.get(j).getState().touchFloor(cArr.get(j));
                  break;
              }
              //判斷走路撞到輸送帶
              if (cArr.get(j).touchLeft(beltArr.get(i))) {
                  cArr.get(j).getState().touchFloor(cArr.get(j));
                  break;
              }
          }

            //主角碰撞寶石牆
            for (int i = 0; i < gArr.size(); i++) {
                //判斷走路撞到右邊寶石牆
                if (cArr.get(j).touchRight(gArr.get(i))) {
                    if (gArr.get(i).getState() != PopsAnimator.State.CHESTEMPTY && gArr.get(i).getState() != PopsAnimator.State.GEMGET) {
                        cArr.get(j).getState().touchFloor(cArr.get(j));
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.CHESTOPEN && gArr.get(i).getState() != PopsAnimator.State.GEMGET) {
                    	gArr.get(i).setState(PopsAnimator.State.GEMGET);
                        cArr.get(j).setGemNumber(cArr.get(j).getGemNumber() + 5);
                        gArr.get(i).setState(PopsAnimator.State.CHESTEMPTY);
                        //5顆寶石加一條命
                        if(cArr.get(j).getGemNumber()%5==0&&cArr.get(j).getGemNumber()!=0) {
                      	  cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1);
                        }
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.GEM) { //變成寶石時可以吃掉
                        gArr.get(i).setState(PopsAnimator.State.GEMGET);
                        cArr.get(j).setGemNumber(cArr.get(j).getGemNumber() + 1);
                        //5顆寶石加一條命
                        if(cArr.get(j).getGemNumber()%5==0&&cArr.get(j).getGemNumber()!=0) {
                      	  cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1);
                        }
//                        System.out.println("r");
                    }
                }
                //判斷走路撞到左邊寶石牆
                if (cArr.get(j).touchLeft(gArr.get(i))) {
                    if (gArr.get(i).getState() != PopsAnimator.State.CHESTEMPTY && gArr.get(i).getState() != PopsAnimator.State.GEMGET) {
                        cArr.get(j).getState().touchFloor(cArr.get(j));
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.CHESTOPEN && gArr.get(i).getState() != PopsAnimator.State.GEMGET) {
                    	gArr.get(i).setState(PopsAnimator.State.GEMGET);
                        cArr.get(j).setGemNumber(cArr.get(j).getGemNumber() + 5);
                        gArr.get(i).setState(PopsAnimator.State.CHESTEMPTY);
                        //5顆寶石加一條命
                        if(cArr.get(j).getGemNumber()%5==0&&cArr.get(j).getGemNumber()!=0) {
                      	  cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1);
                        }
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.GEM) { //變成寶石時可以吃掉
                        gArr.get(i).setState(PopsAnimator.State.GEMGET);
                        cArr.get(j).setGemNumber(cArr.get(j).getGemNumber() + 1);
                        //5顆寶石加一條命
                        if(cArr.get(j).getGemNumber()%5==0&&cArr.get(j).getGemNumber()!=0) {
                      	  cArr.get(j).setLifeCount(cArr.get(j).getLifeCount() + 1);
                        }
//                        System.out.println("l");
                    }
                }
            }
          //主角碰撞衝槌
          for (int i = 0; i < pArr.size(); i++) { // 每個找一次
              //判斷走路撞到右邊衝槌
              if (cArr.get(j).touchRight(pArr.get(i))) {
                  cArr.get(j).getState().touchFloor(cArr.get(j));
                  break;
              }
              //判斷走路撞到左邊衝槌
              if (cArr.get(j).touchLeft(pArr.get(i))) {
                  cArr.get(j).getState().touchFloor(cArr.get(j));
                  break;
              }
          }

            // 主角撞炸彈
            for (int i = 0; i < bArr.size(); i++) {
                if (cArr.get(j).touchRight(bArr.get(i)) && bArr.get(i).getState() != EXPLODE) { //&& cArr.get(j).getState() == WALK  拉掉此行的原因是讓雞ＳＴＩＬＬ時也會觸發飛行中的炸彈
                    cArr.get(j).getState().touchBoom(cArr.get(j)); //撞到炸彈，主角狀態設為推炸彈
                    bArr.get(i).getState().beenTouchLeft(bArr.get(i));
                }
                if (cArr.get(j).touchLeft(bArr.get(i)) && bArr.get(i).getState() != EXPLODE) { //&& cArr.get(j).getState() == WALK
                    cArr.get(j).getState().touchBoom(cArr.get(j));
                    bArr.get(i).getState().beenTouchRight(bArr.get(i));
                }
            }
            cArr.get(j).setReRect(cArr.get(j).collider()); // 儲存現在資訊
            //        System.out.println("----------------------");
        }
    }

    public void bombUpdate() {
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
            
            for (int i = 0; i < transArr.size(); i++) {   
                if (bArr.get(n).touchBottom(transArr.get(i)) && bArr.get(n).getState() != EXPLODE) {
                    bArr.get(n).getState().beenTouchBottom(bArr.get(n));
                    break;
                }
            }
            
            for (int i = 0; i < dArr.size(); i++) { 
                if (bArr.get(n).touchBottom(dArr.get(i)) && bArr.get(n).getState() != EXPLODE) {
                    bArr.get(n).getState().beenTouchBottom(bArr.get(n));
                    break;
                }
            }

            for (int i = 0; i < bArr.size(); i++) { // 炸彈與炸彈間的下碰撞
                if (n != i && bArr.get(n).touchBottom(bArr.get(i))) {
                    bArr.get(n).getState().beenTouchBottom(bArr.get(n));
                    break;
                }
            }

          for (int i = 0; i < beltArr.size(); i++) { // 炸彈與輸送帶的下碰撞
          if (bArr.get(n).touchBottom(beltArr.get(i))) {
              bArr.get(n).getState().beenTouchBottom(bArr.get(n));
              bArr.get(n).translateX(beltArr.get(i).getMoveSpeed());
              break;
          }
      }

      for (int i = 0; i < beltArr.size(); i++) { // 炸彈右碰輸送帶
          if (bArr.get(n).touchRight(beltArr.get(i))) {
              bArr.get(n).getState().beenTouchRight(bArr.get(n));
              break;
          }
      }
      for (int i = 0; i < beltArr.size(); i++) { // 炸彈左碰輸送帶
          if (bArr.get(n).touchLeft(beltArr.get(i))) {
//              System.out.println(1);
              bArr.get(n).getState().beenTouchLeft(bArr.get(n));
              break;
          }
      }
            
            for (int i = 0; i < fArr.size(); i++) { // 炸彈右碰撞牆
                if (bArr.get(n).touchRight(fArr.get(i))) {
                    bArr.get(n).getState().beenTouchRight(bArr.get(n));
                    break;
                }
            }
            for (int i = 0; i < fArr.size(); i++) { // 炸彈左碰撞牆
                if (bArr.get(n).touchLeft(fArr.get(i))) {
                    bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                    break;
                }
            }
            
            for (int i = 0; i < dArr.size(); i++) {
                if (bArr.get(n).touchRight(dArr.get(i))) {
                    bArr.get(n).getState().beenTouchRight(bArr.get(n));
                    break;
                }
            }
            for (int i = 0; i < dArr.size(); i++) { 
                if (bArr.get(n).touchLeft(dArr.get(i))) {
                    bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                    break;
                }
            }
            
            for (int i = 0; i < wArr.size(); i++) { 
                if (bArr.get(n).touchRight(wArr.get(i))) {
                    bArr.get(n).getState().beenTouchRight(bArr.get(n));
                    break;
                }
            }
            for (int i = 0; i < wArr.size(); i++) { 
                if (bArr.get(n).touchLeft(wArr.get(i))) {
                    bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                    break;
                }
            }
            
            for (int i = 0; i < gArr.size(); i++) { // 炸彈右碰寶石牆
                if (bArr.get(n).touchRight(gArr.get(i))) {
                    if (gArr.get(i).getState() == PopsAnimator.State.CHESTCLOSE && bArr.get(n).getState() == EXPLODE) {
                        gArr.get(i).setState(PopsAnimator.State.CHESTOPEN);
                        arc.play(new Path().sound().ObjectSound().chestOpen());
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.GEMWALL) { //寶石在磚牆狀態才會擋住炸彈，並讓炸彈爆炸
                        bArr.get(n).getState().beenTouchRight(bArr.get(n));
                    }
                }
            }
            for (int i = 0; i < gArr.size(); i++) { // 炸彈左碰寶石牆
                if (bArr.get(n).touchLeft(gArr.get(i))) {
                    if (gArr.get(i).getState() == PopsAnimator.State.CHESTCLOSE && bArr.get(n).getState() == EXPLODE) {
                        gArr.get(i).setState(PopsAnimator.State.CHESTOPEN);
                        arc.play(new Path().sound().ObjectSound().chestOpen());
                    }
                    if (gArr.get(i).getState() == PopsAnimator.State.GEMWALL) {//寶石在磚牆狀態才會擋住炸彈，並讓炸彈爆炸
                        bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                    }
                }
            }
            
            for (int i = 0; i < bArr.size(); i++) { // 炸彈右碰炸彈
                if (n != i && bArr.get(n).touchRight(bArr.get(i)) && bArr.get(i).getState() != EXPLODE && bArr.get(n).getState() != EXPLODE) { // 避免卡彈的情況發生
                    bArr.get(n).getState().beenTouchRight(bArr.get(n));
                    bArr.get(i).setState(EXPLODE, PopsAnimator.State.EXPLODE);
                }
            }
            for (int i = 0; i < bArr.size(); i++) { // 炸彈左碰炸彈
                if (n != i && bArr.get(n).touchLeft(bArr.get(i)) && bArr.get(i).getState() != EXPLODE && bArr.get(n).getState() != EXPLODE) { // 避免卡彈的情況發生// 避免卡彈的情況發生
                    bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                    bArr.get(i).setState(EXPLODE, PopsAnimator.State.EXPLODE);
                }
            }
            for (int i = 0; i < fArr.size(); i++) { // 爆炸炸毀磚牆
                if (bArr.get(n).touch(fArr.get(i))) {
                    if (bArr.get(n).getState() == EXPLODE && fArr.get(i).getType() == Floor.Type.BRICK) {
                        fArr.remove(i);
                    }else if(bArr.get(n).getState() == EXPLODE && fArr.get(i).getType() == Floor.Type.DOOR) {
                    	fArr.remove(i);
                    }else if(bArr.get(n).getState() == EXPLODE && fArr.get(i).getType() == Floor.Type.WALLBRICKABLE1) {
                    	fArr.remove(i);
                    }
                }
            }
            for (int i = 0; i < gArr.size(); i++) { // 爆炸炸毀寶石磚牆
                if (bArr.get(n).touch(gArr.get(i))) {
                    if (bArr.get(n).getState() == EXPLODE && gArr.get(i).getState() == PopsAnimator.State.GEMWALL) {
                        gArr.get(i).setState(PopsAnimator.State.GEM);
                    }
                }
            }
            
            for (int i = 0; i < pArr.size(); i++) { // 炸彈右碰衝槌
              if (bArr.get(n).touchRight(pArr.get(i))) {
                  bArr.get(n).getState().beenTouchRight(bArr.get(n));
                  break;
              }
          }
          for (int i = 0; i < pArr.size(); i++) { // 炸彈左碰衝槌
              if (bArr.get(n).touchLeft(pArr.get(i))) {
                  bArr.get(n).getState().beenTouchLeft(bArr.get(n));
                  break;
              }
          }
          
          for (int i = 0; i < wArr.size(); i++) { 
              if (bArr.get(n).touch(wArr.get(i))) {
                  if (bArr.get(n).getState() == EXPLODE ) {
                     wArr.add(new Worker(wArr.get(i).getX(),wArr.get(i).getY()-10,55*5/4,wArr.get(i).getHeight(),Worker.Type.DEAD));
                     wArr.remove(i);
                  }
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
    
  public void fireUpdate() {
  for (int i = 0; i < fireArr.size(); i++) {
      fireArr.get(i).update();
      switch (fireArr.get(i).getState()) {
          case FIREFLYRIGHT:
              if (fireArr.get(i).collider().centerX() >= 2600) {
//                  Boom tmp = new Boom(fireArr.get(i).collider().centerX(), fireArr.get(i).collider().centerY(), Global.UNIT_X, Global.UNIT_Y);
//                System.out.println("!!!!!!");
//                  Boom tmp = new Boom(200, 220, Global.UNIT_X, Global.UNIT_Y);
//                  tmp.setState(THROB, PopsAnimator.State.EXPLODE);
                  fireArr.remove(i);
                  break;
              }
              break;
          case FIREFLYLEFT:
              if (fireArr.get(i).collider().centerX() <= 0) {
                  fireArr.remove(i);
                  break;
              }
              break;
      }

  }
}

    private void mapInitialize() { //地圖載入
        try {
            ArrayList<MapInfo> mapInfo = new MapLoader("scene2.bmp", "scene2.txt").combineInfo();
            for (MapInfo tmp : mapInfo) {
                int[] info = {tmp.getX(), tmp.getY(), tmp.getSizeX(), tmp.getSizeY()};
                switch (tmp.getName()) {
                    case "steel": //鋼鐵牆
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.STEEL));
                        break;
                    case "wall1": //牆壁1
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL1));
                        break;
                    case "wall2": //牆壁2
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL2));
                        break;
                    case "brick": //可破壞的磚牆
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.BRICK));
                        break;
                    case "ceiling": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.CEILING));
                        break;
                    case "door": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.DOOR));
                        break;
                    case "wallL": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALLL));
                        break;
                    case "wall3": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL3));
                    case "wall4": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL4));
                        break;
                    case "wall5": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL5));
                        break;
                    case "wall6": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL6));
                        break;
                    case "wall7": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL7));
                        break;
                    case "corner1": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.CORNER1));
                        break;
                    case "corner2": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.CORNER2));
                        break;
                    case "corner3": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.CORNER3));
                        break;
                    case "corner4": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.CORNER4));
                        break;
                    case "corner8": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.CORNER8));
                        break;
                    case "wallBrickable1": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALLBRICKABLE1));
                        break;
                    case "wallstone": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALLSTONE));
                        break;
                    case "wall8": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALL8));
                        break;
                    case "invisitile1": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.INVISITILE1));
                        break;
                    case "wallC": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.WALLC));
                        break;
                    case "tile2": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.TILE2));
                        break;
                    case "tile3": 
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.TILE3));
                        break;
                    case "spike": //尖刺
                        sArr.add(new Spike(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3]));
                        break;
                    case "beltRollRight": //向右轉的輸送帶
                        beltArr.add(new Belt(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+10,
                                UNIT_X * info[2], UNIT_Y * info[3], 2));
                        break;
                    case "beltRollLeft": //向左轉的輸送帶
                        beltArr.add(new Belt(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+10,
                                UNIT_X * info[2], UNIT_Y * info[3], -2));
                        break;
                    case "pounder": //衝槌
                        pArr.add(new Pounder(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2-20,
                                UNIT_X * info[2], UNIT_Y * info[3]));
                        break;
                    case "gemWall": //有寶石的磚牆
                        gArr.add(new Gem(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], PopsAnimator.State.GEMWALL));
                        break;
                    case "chest": //寶箱
                        gArr.add(new Gem(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2-10,
                                UNIT_X * info[2]*3/2, UNIT_Y * info[3]*3/2, PopsAnimator.State.CHESTCLOSE));
                        break;
                    case "floor1":
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.FLOOR1));
                        break;
                    case "floor2":
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.FLOOR2));
                        break;
                    case "spike2":
                        sArr.add(new Spike(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3]));
                        break;
                    case "yellowSteel":
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.YELLOWSTEEL));
                        break;
                    case "a":
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.A));
                        break;
                    case "big":
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.BIG));
                        break;
                    case "stone":
                        fArr.add(new Floor(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Floor.Type.STONE));
                        break;
                    case "rebirthPoint":
                        rebirthPointArr.add(new Water(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], PopsAnimator.State.REBIRTHPOINT));
                        break;
                    case "boxStack":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BOXSTACK));
                        break;
                    case "boxStack1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BOXSTACK1));
                        break;
                    case "boxStack2":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BOXSTACK2));
                        break;
                    case "boxesPrint1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BOXESPRINT1));
                        break;
                    case "boxesPrint2":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+17,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BOXESPRINT2));
                        break;
                    case "coopEggs":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.COOPEGGS));
                        break;
                    case "coopGround":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.COOPGROUND));
                        break;
                    case "coopGroundL":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.COOPGROUNDL));
                        break;
                    case "coopD":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.COOPD));
                        break;
                    case "lamp":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.LAMP));
                        break;
                    case "eggs":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.EGGS));
                        break;
                    case "eggs1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.EGGS1));
                        break;
                    case "eggs2":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.EGGS2));
                        break;
                    case "factoryWall1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.FACTORYWALL1));
                        break;
                    case "factoryWall2":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.FACTORYWALL2));
                        break;
                    case "fan1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.FAN1));
                        break;
                    case "grass1":
                        grassArr.add(new Grass(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+UNIT_Y-10,
                                UNIT_X * info[2], UNIT_Y * info[3], Grass.Type.GRASS1));
                        break;
                    case "pot":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+8,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.POT));
                        break;
                    case "bigboxes":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BIGBOXES));
                        break;
                    case "statue1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.STATUE1));
                        break;
                    case "light":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2+34,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2-5,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.LIGHT1));
                        break;
                    case "barrelStack1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+UNIT_Y+10,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BARRELSTACK1));
                        break;
                    case "barrel1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BARREL1));
                        break;
                    case "storageSign":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.STORAGESIGN));
                        break;
                    case "spiderweb":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.SPIDERWEB));
                        break;
                    case "spiderweb2":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.SPIDERWEB2));
                        break;
                    case "spiderweb3":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.SPIDERWEB3));
                        break;
                    case "billboard1":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BILLBOARD1));
                        break;
                    case "billboard2":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.BILLBOARD2));
                        break;
                        
                    case "pressBtn1":
                        pressBtnArr.add(new Pressbtn(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+42,
                                UNIT_X * info[2], UNIT_Y * info[3], Pressbtn.Type.PRESSBTNB));
                        break;
                    case "pressbtnL":
                        pressBtnArr.add(new Pressbtn(UNIT_X * info[0] + UNIT_X * info[2] / 2-42,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Pressbtn.Type.PRESSBTNL));
                        break;
                    case "pressbtnR":
                        pressBtnArr.add(new Pressbtn(UNIT_X * info[0] + UNIT_X * info[2] / 2+42,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Pressbtn.Type.PRESSBTNR));
                        break;
                    case "pressbtnU":
                        pressBtnArr.add(new Pressbtn(UNIT_X * info[0] + UNIT_X * info[2] / 2-5,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2-50,
                                UNIT_X * info[2], UNIT_Y * info[3], Pressbtn.Type.PRESSBTNU));
                        break;
                    case "bulkheadSwitch1":
                        dArr.add(new Door(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+10,
                                UNIT_X * info[2]+20, UNIT_Y * info[3], Door.Type.BULKHEADSWITCH1));
                        break;
                    case "bulkHeadSwitch2":
                        dArr.add(new Door(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3]+30, Door.Type.BULKHEADSWITCH2));
                        break;
                    case "trandpipe1":				//transition pipe 打錯字
                        transArr.add(new Transition(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Transition.Type.PIPE));
                        break;
                    case "flap":				
                        transArr.add(new Transition(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2,
                                UNIT_X * info[2], UNIT_Y * info[3], Transition.Type.FLAP));
                        break;
                    case "toilet":
                        bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                UNIT_Y * info[1] + UNIT_Y * info[3] / 2+20,
                                UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.TOILET));
                        break;
                    case "toilet2":				
                    	   bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                   UNIT_Y * info[1] + UNIT_Y * info[3] / 2+20,
                                   UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.TOILET2));
                        break;
                    case "toilet3":				
                    	   bgArr.add(new BackGroundObject(UNIT_X * info[0] + UNIT_X * info[2] / 2,
                                   UNIT_Y * info[1] + UNIT_Y * info[3] / 2+20,
                                   UNIT_X * info[2], UNIT_Y * info[3], BackGroundObject.Type.TOILET3));
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PvEScene1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gameOver() {
        // 判斷死亡人數 > 雞數 - 1 時進結算判定
        int tmp = 0;
        for (int i = 0; i < cArr.size(); i++) { // 有死雞就＋＋
            if (cArr.get(i).getDead()) {
                tmp++;
            }
        }
        if (tmp >= cArr.size()) { 
          SceneController.instance().change(new RoomScene()); // 回到大廳
      }
    }
    
    private void changeScene() {
        // 進電梯
        boolean temp = false;
        temp =  cArr.get(0).getReadyToChange(); 
//        System.out.println("---------------"+temp);
            if (temp && nextSceneDelay.count() || receiveNextScene) { 
            	//進電梯後進下一場景，延遲3秒等電梯動畫跑完再進
            	SceneController.instance().change(new RoomScene()); // 回到大廳
        }
    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return new  CommandSolver.MouseCommandListener(){
        	 @Override
        	 public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
        		  if (state == MouseState.PRESSED) {
        			  
        			  pX = e.getX();
        			  pY = e.getY();
        		  }else if(state == MouseState.RELEASED) {
        			  pX = 0;
        			  pY = 0;
        		  }
        	 }
        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return new KeyListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                if (!exitPop.isShow()) {
                    cArr.get(0).keyPressed(commandCode, trigTime);
                }
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
                            SceneController.instance().change(new RoomScene());
                        }
                    }
                }
                
                if (commandCode == Global.REPLACE) {
                        cArr.get(0).setPosition(rebirthPointArr.get(0).collider().centerX(),rebirthPointArr.get(0).collider().centerY() );
                }
                
                
                if (commandCode == Global.P1DOWN && cArr.get(0).getState() != DROP && cArr.get(0).getState() != BOOMHEAD && cArr.get(0).getState() != DEAD && !exitPop.isShow()) { // 當按下鍵時,後面的判斷是用來避免無限撞頭
                   
                	Chicken temp = new Chicken(cArr.get(0).collider().centerX(), cArr.get(0).collider().centerY(), cArr.get(0).collider().width(), cArr.get(0).collider().height()); // 利用替身攻擊來判斷移動後會不會撞到
                    temp.translateY(-Global.UNIT_Y - 0); // 替身先移動
                    Chicken temp1 = new Chicken(cArr.get(0).collider().centerX(), cArr.get(0).collider().centerY(), cArr.get(0).collider().width(), cArr.get(0).collider().height()); // 利用替身攻擊來判斷移動後會不會撞到
                    temp1.translateY(Global.UNIT_Y + 0); // 替身先移動
                    
                  
                    
                    for (int i = fArr.size() - 1; i >= 0; i--) {
                        if (temp.touchTop(fArr.get(i))) {
                            cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                            cArr.get(0).painter().setTop(fArr.get(i).collider().bottom() - 1); // 設定為看起來剛好撞到
                            cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                            cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                            cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); 
                            break;
                        }
                    }
                    
                    
                    for (int i = dArr.size() - 1; i >= 0; i--) {
                        if (temp.touchTop(dArr.get(i))) {
                            cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                            cArr.get(0).painter().setTop(dArr.get(i).collider().bottom() - 1); // 設定為看起來剛好撞到
                            cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                            cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                            cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); 
                            break;
                        }
                    }
                    
                    for (int i = pressBtnArr.size() - 1; i >= 0; i--) {
                        if (temp.touchTop(pressBtnArr.get(i))) {
                            cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                            cArr.get(0).painter().setTop(pressBtnArr.get(i).collider().bottom() - 1); // 設定為看起來剛好撞到
                            cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                            cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                            cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); 
                            pressBtnArr.get(i).isPressed(true);
                            dArr.get(i).update();
                            break;
                        }else {
                        	pressBtnArr.get(i).isPressed(false);
                        	break;
                        }
                    }
                    
                    //transition 的上碰傳送下碰穿越
                    for (int i = transArr.size() - 1; i >= 0; i--) {
                        if (temp.touchTop(transArr.get(i))) {
                            cArr.get(0).painter().setTop(transArr.get(i).painter().bottom() - 1); // 設定為看起來剛好撞到
                            cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                            cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                            cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); //
                            cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                        	cArr.get(0).translateY(-transArr.get(i).collider().height()-Global.UNIT_Y);
                            break;
                        }
                        if (temp1.touchBottom(transArr.get(i))) {
                        	if(transArr.get(i).getType()== Transition.Type.FLAP ) {
                        		transArr.get(i).setState(PopsAnimator.State.FLAPPING);
                        	}else {
                        		  cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                                	cArr.get(0).translateY(transArr.get(i).collider().height()+Global.UNIT_Y*2);
                        	}
                        }
                    }
                    
                    for (int i = gArr.size() - 1; i >= 0; i--) {
                        if (temp.touchTop(gArr.get(i))) {
                            if (gArr.get(i).getState() == PopsAnimator.State.GEMWALL) {
                                cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                                cArr.get(0).painter().setTop(gArr.get(i).collider().bottom() - 1); // 設定為看起來剛好撞到
                                cArr.get(0).painter().setBottom(cArr.get(0).painter().top() + cArr.get(0).collider().height()); //
                                cArr.get(0).collider().setTop(cArr.get(0).painter().top()); // collider 也跟上
                                cArr.get(0).collider().setBottom(cArr.get(0).painter().bottom()); //
                            }
                            if (gArr.get(i).getState() == PopsAnimator.State.GEM) { //變成寶石時可以吃掉
                                gArr.get(i).setState(PopsAnimator.State.GEMGET);
                                cArr.get(0).setGemNumber(cArr.get(0).getGemNumber() + 1);
                            }
                            break;
                        }
                    }
                    
                    for (int i = beltArr.size() - 1; i >= 0; i--) { // 撞輸送帶
                        if (temp.touchTop(beltArr.get(i))) {
                            cArr.get(0).setState(BOOMHEAD, ActorAnimator.State.BOOMHEAD);
                            cArr.get(0).painter().setTop(beltArr.get(i).painter().bottom() - 1); // 設定為看起來剛好撞到
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
//                        arc.play(new Path().sound().ChickenSound().bomb_lay());
                    }

                    cArr.get(0).setReRect(cArr.get(0).collider()); //儲存現在資訊
                }

                if (commandCode == Global.ESC) {
                    chickenButton.setPosition(440, 280);
                    exitPop.sceneBegin();
                    exitPop.show();
                }
            }

            @Override
            public void keyTyped(char c, long trigTime) {

            }
        };

    }

}
