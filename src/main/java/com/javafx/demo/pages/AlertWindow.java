package com.javafx.demo.pages;

import com.javafx.demo.utils.ResourceBundleUtil;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author
 * @date
 */
public class AlertWindow {
    private static boolean res;

    public static boolean display(String title, String msg) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label();
        label.setText(msg);
        label.setStyle("-fx-font-weight: bold;-fx-font-size: 18");
        // 国际化
        String confirm = ResourceBundleUtil.getStringValue("confirm");
        Button btn1 = new Button(confirm);
        Button btn2 = new Button("取消");
        btn1.setOnMouseClicked(event -> {
            res = true;
            // System.out.println("你点击了是");
            stage.close();
        });
        btn2.setOnMouseClicked(event -> {
            res = false;
            // System.out.println("你点击了否");
            stage.close();
        });
        VBox vBox = new VBox();
        HBox hbox1 = new HBox();
        hbox1.getChildren().add(btn1);
        hbox1.getChildren().add(btn2);
        hbox1.getChildren().add(label);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.BOTTOM_CENTER);
        /*Line line = new Line();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(10);
        line.setEndY(10);*/
        vBox.getChildren().addAll(label, hbox1);
        // 设置居中
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        Scene scene = new Scene(vBox, 200, 160);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.showAndWait();

        return res;
    }
}