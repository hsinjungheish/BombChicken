package bombchicken;

import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Chicken;
import bombchicken.scene.*;
import bombchicken.utils.CommandSolver.*;
import bombchicken.utils.GameKernel;
import bombchicken.utils.Global;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GI implements GameKernel.GameInterface, MouseCommandListener, KeyListener {
    private ArrayList<Chicken> cArr;
    
    Chicken white = new Chicken(670, 420, Global.UNIT_X, Global.UNIT_Y, 0, Chicken.Color.WHITE, "");
       Chicken black = new Chicken(806, 420, Global.UNIT_X, Global.UNIT_Y, 0, Chicken.Color.BLACK, "");
       Chicken pink = new Chicken(942, 420, Global.UNIT_X, Global.UNIT_Y, 0, Chicken.Color.PINK, "");
       Chicken yellow = new Chicken(1080, 420, Global.UNIT_X, Global.UNIT_Y, 0, Chicken.Color.YELLOW, "");
       

    public GI() {
        cArr = new ArrayList<>();
        cArr.add(white);
        cArr.add(black);
        cArr.add(pink);
        cArr.add(yellow);
        
        
//        SceneController.instance().change(new MenuScene());
//          SceneController.instance().change(new TestScene());
//        SceneController.instance().change(new RoomScene());
//        SceneController.instance().change(new PvPScene1());
//          SceneController.instance().change(new PvEScene1());
//        SceneController.instance().change(new PvEScene2());
      SceneController.instance().change(new PvPScene2());
//         SceneController.instance().change(new MobaScene());
//        SceneController.instance().change(new GameOverSceneMoba());
//        SceneController.instance().change(new GameOverScenePVP(cArr));

    }

    @Override
    public void paint(Graphics g) {
        SceneController.instance().paint(g);
    }

    @Override
    public void update() {
        SceneController.instance().update();
    }

    @Override
    public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
        MouseCommandListener ml = SceneController.instance().mouseListener();
        if (ml != null) {
            ml.mouseTrig(e, state, trigTime);
        }
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        KeyListener kl = SceneController.instance().keyListener();
        if (kl != null) {
            kl.keyPressed(commandCode, trigTime);
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        KeyListener kl = SceneController.instance().keyListener();
        if (kl != null) {
            kl.keyReleased(commandCode, trigTime);
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {
        KeyListener kl = SceneController.instance().keyListener();
        if (kl != null) {
            kl.keyTyped(c, trigTime);
        }
    }
}
