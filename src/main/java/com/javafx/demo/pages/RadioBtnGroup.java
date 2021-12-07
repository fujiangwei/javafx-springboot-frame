package com.javafx.demo.pages;

import com.javafx.demo.utils.ConfigUtil;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * @author
 * @date
 */
@Slf4j
public class RadioBtnGroup {
    final ImageView icon = new ImageView();

    public void radioBtnGroup() {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Radio Button Sample");
        stage.setWidth(250);
        stage.setHeight(150);

        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("中文");
        rb1.setToggleGroup(group);
        rb1.setUserData("中文");

        RadioButton rb2 = new RadioButton("英文");
        rb2.setToggleGroup(group);
        rb2.setUserData("英文");

        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle oldToggle,
                 Toggle newToggle) -> {
                    if (group.getSelectedToggle() != null) {
                        String userDataStr = group.getSelectedToggle().getUserData().toString();
                        log.info("点击事件：{}", userDataStr);
                        if ("中文".equals(userDataStr)) {
                            ConfigUtil.set(ConfigUtil.Keys.Locale, Locale.SIMPLIFIED_CHINESE);
                        } else if ("英文".equals(userDataStr)) {
                            ConfigUtil.set(ConfigUtil.Keys.Locale, Locale.US);
                        }
                    }
                });

        HBox hbox = new HBox();
        VBox vbox = new VBox();

        vbox.getChildren().add(rb1);
        vbox.getChildren().add(rb2);
        vbox.setSpacing(10);

        hbox.getChildren().add(vbox);
        hbox.getChildren().add(icon);
        hbox.setSpacing(50);
        hbox.setPadding(new Insets(20, 10, 10, 20));

        ((Group) scene.getRoot()).getChildren().add(hbox);
        stage.setScene(scene);
        stage.show();
    }
}
