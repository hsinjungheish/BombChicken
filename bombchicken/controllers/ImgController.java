/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.controllers;

import bombchicken.utils.Global;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class ImgController {
     private static class KeyPair {

        private String path;
        private Image img;

        public KeyPair(String path, Image img) {
            this.path = path;
            this.img = img;
        }
    }

    // 內容
    private Map<String, Image> imgMap; // 用字串當key,image當值,玩成值鍵對

    public ImgController() {
        imgMap = new HashMap<>(); // 改成map 讓搜尋時只進行一次，節省耗能
    }

    public Image tryGetImage(String path) {
        if(imgMap.containsKey(path)){ // 用key去找，如果key存在
            return imgMap.get(path); // 就回傳相對的圖給他
        }
        return addImage(path); // 沒找到就先加在回傳
    }

    private Image addImage(String path) {
        try {
            if (Global.IS_DEBUG) {
//                System.out.println("load img from: " + path);
            }
            Image img = ImageIO.read(getClass().getResource(path));
            imgMap.put(path, img);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public void clear(){
        imgMap.clear();
    }

    // 做標記 => 標記哪些資源是這個場景開始後才載入
}
