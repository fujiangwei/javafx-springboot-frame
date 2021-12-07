package com.javafx.demo.pages;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * @author
 * @date
 */
public class CloseableTabPane extends BorderPane {
    private TabPane tabPane;

    public CloseableTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        StackPane sp = new StackPane();
        sp.getChildren().add(tabPane);
        MenuButton menuButton = new MenuButton("关闭选项");
        menuButton.setVisible(false);
        StackPane.setMargin(menuButton, new Insets(5));
        sp.setAlignment(Pos.TOP_RIGHT);
        // Tab全部关闭后，则不显示关闭菜单按钮
        tabPane.getTabs().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                if (tabPane.getTabs().size() == 0) {
                    menuButton.setVisible(false);
                } else {
                    menuButton.setVisible(true);
                }
            }
        });

        // 关闭选中的Tab
        MenuItem closeSelected = new MenuItem("关闭选中");
        closeSelected.setOnAction(e -> {
            for (Tab tab : tabPane.getTabs()) {
                if (tab.selectedProperty().getValue() && tab.isClosable()) {
                    tabPane.getTabs().remove(tab);
                    break;
                }
            }
        });

        menuButton.getItems().add(closeSelected);
        // 关闭除选中的其他Tab
        MenuItem closeOthers = new MenuItem("关闭其他");
        menuButton.getItems().add(closeOthers);
        closeOthers.setOnAction(e -> {
            for (Tab tab : tabPane.getTabs()) {
                if (tab.selectedProperty().getValue()) {
                    tabPane.getTabs().clear();
                    tabPane.getTabs().add(tab);
                    break;
                }
            }
        });

        // 关闭所有的Tab
        MenuItem closeAll = new MenuItem("关闭所有");
        closeAll.setOnAction(e -> {
            tabPane.getTabs().clear();
        });

        menuButton.getItems().add(closeAll);
        sp.getChildren().add(menuButton);
        super.setCenter(sp);
    }

    /**
     * 添加单个Tab
     * @param tab
     */
    public void addTab(Tab tab) {
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
    }

    /**
     * 添加Tab集合
     * @param tabs
     */
    public void addAll(ObservableList<Tab> tabs) {
        for (Tab tab : tabs) {
            tab.setClosable(true);
        }

        tabPane.getTabs().addAll(tabs);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
}