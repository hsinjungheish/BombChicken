package bombchicken.scene;

import bombchicken.animator.ActorAnimator;
import bombchicken.animator.PopsAnimator;
import bombchicken.controllers.AudioResourceController;
import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Boom;
import bombchicken.gameobj.Chicken;
import bombchicken.gameobj.Chicken.Group;
import bombchicken.gameobj.Water;
import bombchicken.utils.CommandSolver;
import bombchicken.utils.Delay;
import bombchicken.utils.FontLoader;
import bombchicken.utils.Global;
import bombchicken.utils.Path;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class GameOverScenePVP extends Scene {

    private ArrayList<Chicken> cArr;
    private ArrayList<Chicken> unturned;//未反轉過的名單
    private AudioResourceController arc; 
    private Image background;
    private ArrayList<Boom> bArr;
    private ArrayList<Water> legArr;
    private Delay explosionDelay; //炸死輸家動畫
    private boolean bombShow;
    private boolean legShow;

    public GameOverScenePVP() {

    }

    public GameOverScenePVP(ArrayList<Chicken> cArr) {
        this.unturned = cArr;
    }

    @Override
    public void sceneBegin() {
        cArr = new ArrayList<>();
        legArr = new ArrayList<>();
        bArr = new ArrayList<>();
        background = SceneController.instance().irc().tryGetImage( //背景
                new Path().img().backgrounds().gameOverPc());
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
        arc.stop(new Path().sound().BackGroundMusic().Menu()); // 結束背景音樂
        arc.loop(new Path().sound().BackGroundMusic().ending(), 3); // 放背景音樂   

        //測試用
//        cArr = new ArrayList<>();
        for (int i = unturned.size() - 1; i >= 0; i--) { //先將名單順序反轉回來
            cArr.add(unturned.get(i));
        }
        int n = 10;
        int down = 100;
//        for (int i = 0; i < cArr.size(); i++) {
////            cArr.add(new Chicken(0, 0, Global.UNIT_X, Global.UNIT_Y));
////            cArr.get(i).setColor(Chicken.Color.BLACK);
//            cArr.get(i).setState(Chicken.State.STILL, ActorAnimator.State.STILL);
//            cArr.get(i).setInMenu(true);
//            cArr.get(i).painter().setBottom(cArr.get(i).collider().bottom() - n);
//            cArr.get(i).painter().setTop(cArr.get(i).collider().top() + n);
//            cArr.get(i).painter().setRight(cArr.get(i).collider().right() - n);
//            cArr.get(i).painter().setLeft(cArr.get(i).collider().left() + n);
//            cArr.get(i).setPosition(560, 90 + i * down);

        cArr.get(0).setState(Chicken.State.STILL, ActorAnimator.State.STILL);
        cArr.get(0).setInMenu(true);
        cArr.get(0).setPosition(560, 200);
        cArr.get(0).painter().setBottom(cArr.get(0).collider().bottom() - n);
        cArr.get(0).painter().setTop(cArr.get(0).collider().top() + n);
        cArr.get(0).painter().setRight(cArr.get(0).collider().right() - n);
        cArr.get(0).painter().setLeft(cArr.get(0).collider().left() + n);
        
        cArr.get(1).setState(Chicken.State.DROP, ActorAnimator.State.DROP);
        cArr.get(1).setInMenu(true);
        cArr.get(1).setPosition(560, 350);
        cArr.get(1).painter().setBottom(cArr.get(1).collider().bottom() - n);
        cArr.get(1).painter().setTop(cArr.get(1).collider().top() + n);
        cArr.get(1).painter().setRight(cArr.get(1).collider().right() - n);
        cArr.get(1).painter().setLeft(cArr.get(1).collider().left() + n);
        
        bArr.add(new Boom(cArr.get(1).collider().centerX(), cArr.get(1).collider().centerY() - 6, Global.UNIT_X*4/5 , Global.UNIT_Y*4/5 ));
        legArr.add(new Water(cArr.get(1).collider().centerX(), cArr.get(1).collider().centerY()-5, 80, 70, PopsAnimator.State.CHICKENLEG));
        
        explosionDelay = new Delay(120);//10秒後播放爆炸
        explosionDelay.play();
//        }

        background = SceneController.instance().irc().tryGetImage( //背景
                new Path().img().backgrounds().gameOverPc());

    }

    @Override
    public void sceneEnd() {
        cArr = null;
        background = null;
        unturned = null;
        bArr=null;
        legArr=null;
        arc.stop(new Path().sound().BackGroundMusic().ending()); // 結束背景音樂
        SceneController.instance().irc().clear();

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, 1200, 680, null);
        for (int i = 0; i < cArr.size(); i++) {
            cArr.get(i).paint(g);
            g.setColor(Color.YELLOW);   //名次
            g.setFont(FontLoader.Retro9(40));
            if(i==0) {
            	  g.drawString(("Win") + "", 350, 220);
            }else {
            	g.drawString(("Lose") + "", 350, 370);
            }
          //cArr.get(i).collider().centerY() + 20
            g.setColor(Color.WHITE);   //名字
            g.setFont(FontLoader.Retro8(30));
            g.drawString(cArr.get(i).getName(), 630, cArr.get(i).collider().centerY() + 10);
        }
        if (bombShow) {
            bArr.get(0).paint(g);
        }
        if (legShow) {
            legArr.get(0).paint(g);
        }
        g.setColor(Color.BLACK);  //離開提示
        g.setFont(FontLoader.Retro8(20));
        g.drawString("Press Enter To Exit", 480, 600);

    }

    @Override
    public void update() {
//        System.out.println("carr   " + cArr.size());
//        System.out.println("re   " + unturned.size());
    	for(int i=0;i < cArr.size(); i++) {
    		cArr.get(i).update();
    	}
//            System.out.println(i + "   " + cArr.get(i).painter().centerX() + "   " + cArr.get(i).painter().centerY());
        if (explosionDelay.count()) {
            bombShow = true;
        }
            if (bombShow) {
                bArr.get(0).update();
                if (bArr.get(0).isDelete()) {
                    legShow = true;
                    bombShow=false;
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
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                if (commandCode == Global.ENTER) {
                    SceneController.instance().change(new RoomScene());
                }
            }

            @Override
            public void keyTyped(char c, long trigTime) {
            }

        };
    }
}
