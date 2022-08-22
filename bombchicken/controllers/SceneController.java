
package bombchicken.controllers;

import bombchicken.scene.Scene;
import bombchicken.utils.CommandSolver;
import java.awt.Graphics;

public class SceneController {
    private static SceneController sceneController;

    private SceneController() {
        lastIrc = new ImgController();
        currentIrc = new ImgController();
    }

    public static SceneController instance() {
        if (sceneController == null) {
            sceneController = new SceneController();
        }
        return sceneController;
    }

    private Scene lastScene;
    private Scene currentScene;
    private ImgController lastIrc;
    private ImgController currentIrc;

    public void change(Scene scene) {
        lastScene = currentScene;

        ImgController tmp = currentIrc;
        currentIrc = lastIrc;
        lastIrc = tmp;

        if (scene != null) {
            scene.sceneBegin();
        }
        currentScene = scene;
    }

    public void paint(Graphics g) {
        if (currentScene != null) {
            currentScene.paint(g);
        }
    }

    public void update() {
        if (lastScene != null) {
            lastScene.sceneEnd();
            lastIrc.clear();
            lastScene = null;
        }
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public CommandSolver.MouseCommandListener mouseListener() {
        return currentScene.mouseListener();
    }

    public CommandSolver.KeyListener keyListener() {
        return currentScene.keyListener();
    }

    public ImgController irc() {
        return currentIrc;
    }
}
