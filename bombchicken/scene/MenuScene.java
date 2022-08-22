/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.scene;

import bombchicken.animator.ActorAnimator;
import bombchicken.animator.PopsAnimator;
import bombchicken.client.ClientClass;
import bombchicken.controllers.AudioResourceController;
import bombchicken.controllers.SceneController;
import bombchicken.gameobj.Boom;
import bombchicken.gameobj.Chicken;
import bombchicken.gameobj.Fire;
import bombchicken.gameobj.Rect;
import bombchicken.gameobj.Water;
import bombchicken.menu.BackgroundType;
import bombchicken.menu.Button;
import bombchicken.menu.EditText;
import bombchicken.menu.Label;
import bombchicken.menu.PopupWindowScene;
import bombchicken.menu.PopupWindowScene.Type;
import bombchicken.menu.Style;
import bombchicken.menu.Theme;
import bombchicken.utils.CommandSolver;
import bombchicken.utils.Delay;
import bombchicken.utils.FontLoader;
import bombchicken.utils.Global;
import bombchicken.utils.MouseTriggerImpl;
import bombchicken.utils.Path;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import static java.awt.Font.SANS_SERIF;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author User
 */
public class MenuScene extends Scene {
    //Retro1 像素風字體
    //Retro2 細高字體
    //Retro3 中等字體
    //Retro4 圓短字體
	
    private Button button;
    private Image img;
    private Image startB;
    private Image exitB;
    private Water menu;  //背景畫面，借用Water類生成
    private Chicken chickenButton;
    private int chickenButtonX = 485;
    private int chickenButtonY = 460;
    private boolean chooseExit; //要不要顯現黑色start字
    private AudioResourceController arc; 

    @Override
    public void sceneBegin() {
        initTheme();
        startB = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().startWordBlack());
        exitB = SceneController.instance().irc().tryGetImage(
                new Path().img().objs().exitWordBlack());
        button = new Button((Global.SCREEN_X / 2 - 50), 400, Theme.get(0));
        button.setClickedActionPerformed((int x, int y) //按下button要做的事
                -> SceneController.instance().change(new RoomScene()) //換關
        );

        menu = new Water(600, 330, 1200, 680, PopsAnimator.State.MENU);
        chickenButton = new Chicken(490, 460, Global.UNIT_X, Global.UNIT_Y);
        chickenButton.setState(Chicken.State.WALK, ActorAnimator.State.WALK);
        chickenButton.setInMenu(true);
        chooseExit = false; //預設選項為start，所以不顯現黑字start
        arc = AudioResourceController.getInstance(); // 單例創建聲音播放器
        arc.loop(new Path().sound().BackGroundMusic().Menu(), 15); // 放背景音樂

    }
	

    @Override
    public void sceneEnd() {
        img = null;
        button = null;
        startB = null;
        exitB = null;
        menu = null;
        chickenButton = null;
        SceneController.instance().irc().clear();
    }

    @Override
    public void paint(Graphics g) {
        menu.paint(g);
        chickenButton.paint(g);
        if (chooseExit) { //選到exit
            g.drawImage(startB, chickenButtonX + 57, chickenButtonY - 19, 120, 34, null); //用黑字遮住start
        }
        if (!chooseExit) { //選到start
            g.drawImage(exitB, chickenButtonX + 75, chickenButtonY + 77, 79, 32, null);
        }
        g.setColor(Color.WHITE);
        g.setFont(FontLoader.Retro8(20));
        g.drawString("Press Enter To Check", 470, 630);
    }

    @Override
    public void update() {
        menu.update();
        chickenButton.update();
    }

    private static void initTheme() {
        Style simple = new Style.StyleOval(100, 100, true, new BackgroundType.BackgroundColor(Color.YELLOW))
                .setTextColor(new Color(128, 128, 128))
                .setHaveBorder(true)
                .setBorderColor(new Color(255, 215, 0))
                .setBorderThickness(5)
                .setTextFont(FontLoader.Retro1(30))
                .setText("Press");
        Style aa = new Style.StyleOval(100, 100, true, new BackgroundType.BackgroundColor(new Color(184, 134, 11)))
                .setTextColor(Color.BLACK)
                .setHaveBorder(true)
                .setBorderColor(new Color(230, 184, 0))
                .setBorderThickness(5)
                .setTextFont(FontLoader.Retro1(30))
                .setText("Start");
        Style im = new Style.StyleOval(100, 100, new BackgroundType.BackgroundImage(SceneController.instance().irc().tryGetImage(new Path().img().objs().chickenButton())))
                .setHaveBorder(false)
                .setText("Press");

        Theme.add(new Theme(im, simple, aa)); //為ArrayList<Theme>新增一實體
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
                        if (!chooseExit) { 
                            chickenButton.setPosition(100, 370);    
                            SceneController.instance().change(new RoomScene());//進入大廳
                        } else{ //按下exit
                            System.exit(0); //關閉遊戲
                        }
                    } 
                if (commandCode == Global.P1UP) {
                        chickenButton.setPosition(chickenButtonX, chickenButtonY);
                        chooseExit = false;
                }
                if (commandCode == Global.P1DOWN) {
                        chickenButton.setPosition(chickenButtonX, chickenButtonY + 100);
                        chooseExit = true;
                }
            }

            @Override
            public void keyTyped(char c, long trigTime) {

            }
        };

    }
}
