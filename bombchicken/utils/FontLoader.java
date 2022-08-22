/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombchicken.utils;

import java.awt.Font;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class FontLoader {

      private static HashMap<String, Font> map = new HashMap<>();

    public static Font loadFont(String fontFileName, float fontSize) {
        String key = fontFileName + fontSize;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        try {

            File file = new File(fontFileName);

            FileInputStream aixing = new FileInputStream(file);

            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);

            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);

            aixing.close();
            
            map.put(key, dynamicFontPt);

            return dynamicFontPt;

        } catch (Exception e) {
            e.printStackTrace();
            return new java.awt.Font("宋体", Font.PLAIN, 14);

        }

    }

    public static java.awt.Font Retro1(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/Blocktopia.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro2(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/NotoSans.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro3(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/NotoSans-Regular-Russian.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro4(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/trad-chin-jap-rus-greek.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro5(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/Avaca-Davra-2.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro6(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/masego-2.otf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro7(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/AQUARIUM-2.otf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro8(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/ZiTiQuanXinYiLOGOTi-2.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro9(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/CabaretDisplayItalic-BW0Dd-3.ttf", size);//调用
        return font;//返回字体
    }

    public static java.awt.Font Retro10(float size) {
        String root = System.getProperty("user.dir");//项目根目录路径
        Font font = FontLoader.loadFont(root + "/src/resources/fonts/Cabaret-Display-2.ttf", size);//调用
        return font;//返回字体
    }

}
