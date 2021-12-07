package com.javafx.demo.pages;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author
 * @date
 */
public class BtnGroup {

    private static final Color COLOR = Color.web("#464646");

    Button button3 = new Button("Decline");
    DropShadow shadow = new DropShadow();
    Label label = new Label();

    public void btnGroup() {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Button Sample");
        stage.setWidth(500);
        stage.setHeight(300);
        // scene.getStylesheets().add("buttonsample/ControlStyle.css");

        label.setFont(Font.font("Times New Roman", 22));
        label.setTextFill(COLOR);

        Image imageDecline = new Image(getClass().getResourceAsStream("/icon/icon.png"));
        Image imageAccept = new Image(getClass().getResourceAsStream("/icon/icon.jpg"));

        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();

        // Button button1 = new Button("Accept", new ImageView(imageAccept));
        Button button1 = new Button("Accept");
        button1.getStyleClass().add("button1");
        button1.setOnAction((ActionEvent e) -> {
            label.setText("Accepted button1");
        });


        Button button2 = new Button("Accept");
        button2.setOnAction((ActionEvent e) -> {
            label.setText("Accepted button2");
        });

        button3.setOnAction((ActionEvent e) -> {
            label.setText("Declined button3");
        });

        button3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            button3.setEffect(shadow);
        });

        button3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            button3.setEffect(null);
        });


        hbox1.getChildren().add(button2);
        hbox1.getChildren().add(button3);
        hbox1.getChildren().add(label);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.BOTTOM_CENTER);

        Button button4 = new Button();
        // button4.setGraphic(new ImageView(imageAccept));
        button4.setOnAction((ActionEvent e) -> {
            label.setText("Accepted button4");
        });


        Button button5 = new Button();
        // button5.setGraphic(new ImageView(imageDecline));
        button5.setOnAction((ActionEvent e) -> {
            label.setText("Declined button5");
        });

        hbox2.getChildren().add(button4);
        hbox2.getChildren().add(button5);
        hbox2.setSpacing(35);

        vbox.getChildren().add(button1);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox2);
        vbox.setSpacing(20);
        ((Group) scene.getRoot()).getChildren().add(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
