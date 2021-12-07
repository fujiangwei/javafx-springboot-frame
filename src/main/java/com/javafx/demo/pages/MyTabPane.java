package com.javafx.demo.pages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * @author
 * @date
 */
public class MyTabPane {

    public static void test() {
        Stage stage = new Stage();
        // set title for the stage
        stage.setTitle("Creating Tab");

        // create a tabpane
        TabPane tabpane = new TabPane();

        // create multiple tabs
        for (int i = 0; i < 10; i++) {

            // create Tab
            Tab tab = new Tab("Tab_" + (int) (i + 1));

            // create a label
            Label label = new Label("This is Tab:" + (int) (i + 1));

            // add label to the tab
            tab.setContent(label);

            // add tab
            tabpane.getTabs().add(tab);
        }

        // create a scene
        Scene scene = new Scene(tabpane, 600, 500);

        // set the scene
        stage.setScene(scene);

        stage.show();
    }
}
