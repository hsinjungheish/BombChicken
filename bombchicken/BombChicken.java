package bombchicken;

import bombchicken.utils.GameKernel;
import bombchicken.utils.Global;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class BombChicken {

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("Bomb Chicken");
        jf.setSize(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null); 
        int[][] command = {
            {KeyEvent.VK_LEFT, Global.P1LEFT},
            {KeyEvent.VK_RIGHT, Global.P1RIGHT},
            {KeyEvent.VK_UP, Global.P1UP},
            {KeyEvent.VK_DOWN, Global.P1DOWN},
            {KeyEvent.VK_A, Global.P2LEFT},
            {KeyEvent.VK_D, Global.P2RIGHT},
            {KeyEvent.VK_W, Global.P2UP},
            {KeyEvent.VK_S, Global.P2DOWN},
            {KeyEvent.VK_SPACE, Global.SPACE},
            {KeyEvent.VK_ESCAPE, Global.ESC},
            {KeyEvent.VK_ENTER, Global.ENTER},
            {KeyEvent.VK_T, Global.TEACH},
            {KeyEvent.VK_R, Global.REPLACE2},
            {KeyEvent.VK_P, Global.REPLACE}

        };

        GI gi = new GI();

        GameKernel gk = new GameKernel.Builder(gi, Global.LIMIT_DELTA_TIME, Global.NANOSECOND_PER_UPDATE)
                .initListener(command)
                .enableKeyboardTrack(gi)
                .enableMouseTrack(gi)
                .mouseForceRelease()
   
                .keyCleanMode() 
                .trackChar()
                .gen();

        jf.add(gk);

        jf.setVisible(true);
        gk.run(Global.IS_DEBUG);
    }

}
