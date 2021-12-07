package com.javafx.demo.pages;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author
 * @date
 */
public class MenuPage {

    final ImageView pic = new ImageView();
    final Label name = new Label();
    final Label binName = new Label();
    final Label description = new Label();
    private int currentIndex = -1;

    final PageData[] pages = new PageData[]{
            new PageData("/icon/icon",
                    "The apple is the pomaceous fruit of the apple tree, species Malus "
                            + "domestica in the rose family (Rosaceae). It is one of the most "
                            + "widely cultivated tree fruits, and the most widely known of "
                            + "the many members of genus Malus that are used by humans. "
                            + "The tree originated in Western Asia, where its wild ancestor, "
                            + "the Alma, is still found today.",
                    "Malus domestica"),
            new PageData("/icon/icon",
                    "The hawthorn is a large genus of shrubs and trees in the rose "
                            + "family, Rosaceae, native to temperate regions of the Northern "
                            + "Hemisphere in Europe, Asia and North America. "
                            + "The name hawthorn was "
                            + "originally applied to the species native to northern Europe, "
                            + "especially the Common Hawthorn C. monogyna, and the unmodified "
                            + "name is often so used in Britain and Ireland.",
                    "Crataegus monogyna"),
            new PageData("/icon/icon",
                    "The ivy is a flowering plant in the grape family (Vitaceae) native"
                            + " to eastern Asia in Japan, Korea, and northern and eastern China."
                            + " It is a deciduous woody vine growing to 30 m tall or more given "
                            + "suitable support,  attaching itself by means of numerous small "
                            + "branched tendrils tipped with sticky disks.",
                    "Parthenocissus tricuspidata"),
            new PageData("/icon/icon",
                    "The quince is the sole member of the genus Cydonia and is native"
                            + " to warm-temperate southwest Asia in the Caucasus region. The "
                            + "immature fruit is green with dense grey-white pubescence, most "
                            + "of which rubs off before maturity in late autumn when the fruit "
                            + "changes color to yellow with hard, strongly perfumed flesh.",
                    "Cydonia oblonga")
    };

    final Map.Entry<String, Effect>[] effects = new Map.Entry[]{
            new AbstractMap.SimpleEntry<>("Sepia Tone", new SepiaTone()),
            new AbstractMap.SimpleEntry<>("Glow", new Glow()),
            new AbstractMap.SimpleEntry<>("Shadow", new DropShadow())
    };

    public void test() {
        Stage stage = new Stage();
        stage.setTitle("Menu Sample");
        Scene scene = new Scene(new VBox(), 400, 350);
        scene.setFill(Color.OLDLACE);

        name.setFont(new Font("Verdana Bold", 22));
        binName.setFont(new Font("Arial Italic", 10));
        pic.setFitHeight(150);
        pic.setPreserveRatio(true);
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);

        shuffle();

        MenuBar menuBar = new MenuBar();

        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(name, binName, pic, description);

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Shuffle");
        // MenuItem add = new MenuItem("Shuffle", new ImageView(new Image("/icon/icon.jpg")));
        add.setOnAction((ActionEvent t) -> {
            shuffle();
            vbox.setVisible(true);
        });
        // menuFile.getItems().addAll(add);

        MenuItem clear = new MenuItem("Clear");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction((ActionEvent t) -> {
            vbox.setVisible(false);
        });
        // menuFile.getItems().addAll(clear);

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        // menuFile.getItems().addAll(exit);

        // 创建一个分隔菜单项，并通过getItems()方法将其添加进去，以将Exit菜单隔离开
        menuFile.getItems().addAll(add, clear, new SeparatorMenuItem(), exit);

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");

        // Picture Effect菜单
        /*Menu menuEffect = new Menu("Picture Effect");

        // 通过setUserData()为每个Radio Menu Item定义了一个视觉特效。
        // 当Toggle Group中的菜单项被选中时，对应的特效就会被应用在图片上。当No Effects菜单项被选中时，setEffect()方法被赋值为null，没有特效应用在图片上。
        final ToggleGroup groupEffect = new ToggleGroup();
        for (Map.Entry<String, Effect> effect : effects) {
            RadioMenuItem itemEffect = new RadioMenuItem(effect.getKey());
            itemEffect.setUserData(effect.getValue());
            itemEffect.setToggleGroup(groupEffect);
            menuEffect.getItems().add(itemEffect);
        }

        // No Effects菜单
        final MenuItem noEffects = new MenuItem("No Effects");
        noEffects.setOnAction((ActionEvent t) -> {
            pic.setEffect(null);
            Toggle selectedToggle = groupEffect.getSelectedToggle();
            if (null != selectedToggle) {
                // 清除选中项
                selectedToggle.setSelected(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("暂无选中");
                alert.show();
            }
        });

        // 处理菜单项的选中事件
        groupEffect.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle oldToggle, Toggle newToggle) {
                if (groupEffect.getSelectedToggle() != null) {
                    Effect effect = (Effect) groupEffect.getSelectedToggle().getUserData();
                    pic.setEffect(effect);
                }
            }
        });*/
        Menu menuEffect = new Menu("Picture Effect");
        final ToggleGroup groupEffect = new ToggleGroup();
        for (Map.Entry<String, Effect> effect : effects) {
            RadioMenuItem itemEffect = new RadioMenuItem(effect.getKey());
            itemEffect.setUserData(effect.getValue());
            itemEffect.setToggleGroup(groupEffect);
            menuEffect.getItems().add(itemEffect);
        }
        final MenuItem noEffects = new MenuItem("No Effects");
        // 禁用
        noEffects.setDisable(true);
        noEffects.setOnAction((ActionEvent t) -> {
            pic.setEffect(null);
            groupEffect.getSelectedToggle().setSelected(false);
            noEffects.setDisable(true);
        });

        groupEffect.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle oldToggle,
                 Toggle newToggle) -> {
                    if (groupEffect.getSelectedToggle() != null) {
                        Effect effect = (Effect) groupEffect.getSelectedToggle().getUserData();
                        pic.setEffect(effect);
                        noEffects.setDisable(false);
                    } else {
                        noEffects.setDisable(true);
                    }
                });

        // 向Edit菜单添加菜单项
        menuEdit.getItems().addAll(menuEffect, noEffects);

        // --- Menu View
        Menu menuView = new Menu("View");

        CheckMenuItem titleView = createMenuItem("Title", name);
        CheckMenuItem binNameView = createMenuItem("Binomial name", binName);
        CheckMenuItem picView = createMenuItem("Picture", pic);
        CheckMenuItem descriptionView = createMenuItem("Description", description);
        menuView.getItems().addAll(titleView, binNameView, picView, descriptionView);

        // 上下文菜单
        // 当你无法分配任何UI空间给一个需要的功能时，你可以使用上下文菜单。
        // 上下文菜单是一个弹出窗口，会由一次鼠标点击事件触发显示出来。一个上下文菜单可以包含一个或者多个菜单项。
        final ContextMenu cm = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("Copy Image");
        cmItem1.setOnAction((ActionEvent e) -> {
            // ClipBoard并添加图像作为其内容
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putImage(pic.getImage());
            clipboard.setContent(content);
        });

        cm.getItems().add(cmItem1);
        pic.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                cm.show(pic, e.getScreenX(), e.getScreenY());
            }
        });

        // 添加到menuBar
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void shuffle() {
        int i = currentIndex;
        while (i == currentIndex) {
            i = (int) (Math.random() * pages.length);
        }
        pic.setImage(pages[i].image);
        name.setText(pages[i].name);
        binName.setText("(" + pages[i].binNames + ")");
        description.setText(pages[i].description);
        currentIndex = i;
    }

    private class PageData {
        public String name;
        public String description;
        public String binNames;
        public Image image;

        public PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;
            image = new Image(getClass().getResourceAsStream(name + ".jpg"));
        }
    }

    private static CheckMenuItem createMenuItem(String title, final Node node) {
        CheckMenuItem cmi = new CheckMenuItem(title);
        cmi.setSelected(true);
        cmi.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean oldVal,
                 Boolean newVal) -> {
                    node.setVisible(newVal);
                });
        return cmi;
    }
}
