package com.javafx.demo.utils;

import javafx.scene.control.Button;

/**
 * 生成组件
 *
 * @author
 * @date
 */
public class ViewerUtil {
    public static Button getColorButton(String title, String color) {
        Button b = new Button(title);
        b.setStyle(String.format("-fx-base: %s;", color.toLowerCase()));
        return b;
    }

	/**
	 * 返回一个 main.ext 文件名的 main
	 * @param filename
	 * @return
	 */
	public static String getMainFileName(String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

	/**
	 * 返回一个 main.ext 文件名的 .ext
	 * @param filename
	 * @return
	 */
	public static String getExtFileName(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
