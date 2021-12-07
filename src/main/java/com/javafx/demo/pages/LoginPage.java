package com.javafx.demo.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author
 * @date
 */
public class LoginPage {
    public static void test() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome");
        // 为sceneTitle创建Id
        sceneTitle.setId("welcome-text");
        // 用css文件控制
        // sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        // grid.add()方法将scenetitle变量添加到grid布局之中。
        // 在grid中的行列号均从0开始，而scenetitle被添加到第0列、第0行。
        // grid.add()方法的最后两个参数分别表示列跨度为2、行跨度为1。
        grid.add(sceneTitle, 0, 0, 2, 1);

        // 创建Label对象，放到第0列，第1行
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        // 创建文本输入框，放到第1列，第1行
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        // HBox面板为按钮设置了与grid面板中其它控件不同的对齐方式。
        // alignment属性值为Pos.BOTTOM_RIGHT，表示将对应的节点设置为靠右下对齐。按钮被添加为HBox面板的子节点，而HBox面板被添加到grid中的第1列，第4行。
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        // 将按钮控件作为子节点
        hbBtn.getChildren().add(btn);
        // 将HBox pane放到grid中的第1列，第4行
        grid.add(hbBtn, 1, 4);

        // 增加用于显示信息的文本
        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        actionTarget.setId("actionTarget");

        // 注册事件handler
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // 将文字颜色变成 firebrick red
                // actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Sign in button pressed");
            }
        });

        // 场景
        Scene scene = new Scene(grid, 360, 275);

        // 加入舞台
        stage.setScene(scene);
        scene.getStylesheets().add(LoginPage.class.getResource("/css/Login.css").toExternalForm());
        stage.setTitle("JavaFX Welcome");
        stage.show();
    }
}
