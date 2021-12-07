package com.javafx.demo.pages;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * @author
 * @date
 */
public class MyTabPane2 {
    /**
     * counter of tabs
     */
    int counter = 0;

    public void test() {
        Stage stage = new Stage();
        // set title for the stage
        stage.setTitle("Creating Tab2");

        // create a tabpane
        TabPane tabpane = new TabPane();

        // create multiple tabs
        for (int i = 0; i < 5; i++) {
            // create Tab
            Tab tab = new Tab("Tab_" + (int)(counter + 1));
            // create a label
            Label label = new Label("This is Tab:"
                    + (int)(counter + 1));
            counter++;
            // add label to the tab
            tab.setContent(label);
            // add tab
            tabpane.getTabs().add(tab);
        }

        // create a tab which
        // when pressed creates a new tab
        Tab newTab = new Tab();
        // action event
        EventHandler<Event> event = (e) -> {
            if (newTab.isSelected()) {
                // create Tab
                Tab tab = new Tab("Tab_" + (int)(counter + 1));
                // create a label
                Label label = new Label("This is Tab:"
                        + (int)(counter + 1));
                counter ++;
                // add label to the tab
                tab.setContent(label);
                // add tab
                tabpane.getTabs().add(tabpane.getTabs().size() - 1, tab);
                // select the last tab
                tabpane.getSelectionModel().select(tabpane.getTabs().size() - 2);
            }
        };
        // set event handler to the tab
        newTab.setOnSelectionChanged(event);

        // add newtab
        tabpane.getTabs().add(newTab);

        // create a scene
        Scene scene = new Scene(tabpane, 600, 500);

        // set the scene
        stage.setScene(scene);
        stage.show();
    }
}
