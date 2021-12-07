package com.javafx.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.javafx.demo.pages.*;
import com.javafx.demo.utils.ResourceBundleUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import javafx.stage.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author
 * @date
 */
@Slf4j
@FXMLController
public class DemoController implements Initializable {

    /**
     * BING_SEARCH_URL
     */
    public static final String BING_SEARCH_URL = "https://cn.bing.com/";

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button fileChoose;

    @FXML
    private Button dateTip;

    @FXML
    private Button btnGroup;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TabPane tabPaneMain;

    private CloseableTabPane closeableTabPane;

    /**
     * 初始化
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("Initializable  initialize : location.getPath:{},baseBundleName:{}", location.getPath(), resources.getBaseBundleName());
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        String indexPage = ResourceBundleUtil.getStringValue("IndexPage");
        Tab indexTab = new Tab(indexPage);
        TextArea notePad = new TextArea("欢迎来到首页！");
        notePad.setFocusTraversable(true);
        indexTab.setContent(notePad);
        // 不允许删除
        indexTab.setClosable(false);
        // 进入首页
        tabPaneMain.getTabs().add(indexTab);
        // web页面嵌入
        addWebView("Microsoft Bing", BING_SEARCH_URL, null);
        // 选中首页
        tabPaneMain.getSelectionModel().select(indexTab);
        closeableTabPane = new CloseableTabPane(tabPaneMain);
        // 覆盖默认的
        borderPane.setCenter(closeableTabPane);
        // tabPaneMain.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    }

    /**
     * 内嵌网页视图
     *
     * @param title
     * @param url
     * @param iconPath
     */
    public void addWebView(String title, String url, String iconPath) {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        if (url.startsWith("http")) {
            webEngine.load(url);
        } else {
            webEngine.load(getClass().getResource(url).toExternalForm());
        }
        Tab tab = new Tab(title);
        if (StringUtils.isNotEmpty(iconPath)) {
            ImageView imageView = new ImageView(new Image(iconPath));
            imageView.setFitHeight(18);
            imageView.setFitWidth(18);
            tab.setGraphic(imageView);
        }
        tab.setContent(browser);
        tab.setClosable(false);
        tabPaneMain.getTabs().add(tab);
        tabPaneMain.getSelectionModel().select(tab);
    }

    public void dateTip(ActionEvent actionEvent) {
        log.info("dateTip...");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("当前时间：" + DateUtil.now());
        alert.show();
    }

    public void fileChoose(ActionEvent actionEvent) {
        log.info("fileChoose...");
        Window window = rootPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);

        String fileName = file == null ? "" : file.getName();
        String fileAbsolutePath = file == null ? "" : file.getAbsolutePath();

        if (file != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("文件路径：" + fileAbsolutePath);
            alert.show();
        }
    }

    public void btnGroup(ActionEvent actionEvent) {
        log.info("btnGroup...");
        BtnGroup btnGroup = new BtnGroup();
        btnGroup.btnGroup();
    }

    public void radioBtnGroup(ActionEvent actionEvent) {
        log.info("radioBtnGroup...");
        RadioBtnGroup radioBtnGroup = new RadioBtnGroup();
        radioBtnGroup.radioBtnGroup();
    }

    public void login(ActionEvent actionEvent) {
        log.info("login...");
        LoginPage.test();
    }

    public void menu(ActionEvent actionEvent) {
        log.info("menu...");
        new MenuPage().test();
    }

    public void tabPane(ActionEvent actionEvent) {
        log.info("tabPane...");
        new MyTabPane().test();
    }

    public void tabPane2(ActionEvent actionEvent) {
        log.info("tabPane2...");
        new MyTabPane2().test();
    }

    public void fileSelect(ActionEvent actionEvent) {
        fileChoose(actionEvent);
    }

    public void about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("这是关于");
        alert.show();
    }

    public void exit(ActionEvent actionEvent) {
        boolean b = AlertWindow.display("提示", "确认退出系统吗？");
        if (b) {
            System.exit(0);
        }
    }

    public void mtimeTip(ActionEvent actionEvent) {
        dateTip(actionEvent);
    }

    public void mLogin(ActionEvent actionEvent) {
        login(actionEvent);
    }

    public void mMenu(ActionEvent actionEvent) {
        menu(actionEvent);
    }

    public void mTabPane(ActionEvent actionEvent) {
        tabPane(actionEvent);
    }

    public void mTabPane2(ActionEvent actionEvent) {
        tabPane2(actionEvent);
    }

    public void mBtnGroup(ActionEvent actionEvent) {
        btnGroup(actionEvent);
    }

    public void mBtnGroup2(ActionEvent actionEvent) {
        radioBtnGroup(actionEvent);
    }

    public void mOpenNew(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("打开新页面测试");
        // 加载fxml文件
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/new.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(DemoController.class.getResource("/fxml/new.fxml"));
        fxmlLoader.setResources(null);
        fxmlLoader.load();

        Parent root = fxmlLoader.getRoot();
        // 控制器
        NewController newController = fxmlLoader.getController();

        // 获取屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.width * 0.74;
        double screenHeight = screenSize.height * 0.8;
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        // 解决屏幕缩放问题
        if (screenWidth > bounds.getWidth() || screenHeight > bounds.getHeight()) {
            screenWidth = bounds.getWidth();
            screenHeight = bounds.getHeight();
        }

        // Scene scene = new Scene(root, 900, 500);
        Scene scene = new Scene(root, screenWidth, screenHeight);

        // 场景装饰器
        /*JFXDecorator decorator = new JFXDecorator(stage, fxmlLoader.getRoot(), true, true, true);
        decorator.setCustomMaximize(true);
        decorator.setTitle("打开新的页面");
        if (StringUtils.isNotEmpty("")) {
            ImageView imageView = new ImageView(new Image("/icon/icon.png"));
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            decorator.setGraphic(imageView);
        }

        Scene scene = new Scene(decorator, screenWidth, screenHeight);*/

        // 绑定样式
        scene.getStylesheets().add(DemoController.class.getResource("/css/jfoenix-components.css").toExternalForm());
        /*final ObservableList<String> stylesheets = scene.getStylesheets();
        URL cssUrl = DemoController.class.getResource("/css/jfoenix-components.css");
        if (cssUrl != null) {
            stylesheets.addAll(cssUrl.toExternalForm());
        }*/

        // 舞台设置场景
        stage.setScene(scene);

        // 设置图标
        if (StringUtils.isNotEmpty("/icon/icon.png")) {
            stage.getIcons().add(new Image("/icon/icon.png"));
        }

        // 设置可缩放
        stage.setResizable(true);
        // 设置窗口模式
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void mOpenNew2(ActionEvent actionEvent) throws Exception {
        // 加载fxml文件
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/new.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(DemoController.class.getResource("/fxml/new2.fxml"));
        fxmlLoader.setResources(null);
        fxmlLoader.load();

        // 控制器
        New2Controller new2Controller = fxmlLoader.getController();

        Parent root = fxmlLoader.getRoot();

        VBox dialogContainer = new VBox(root);
        VBox.setVgrow(root, Priority.ALWAYS);

        dialogContainer.setPadding(new Insets(5));
        dialogContainer.setSpacing(5);

        Stage stage = new Stage();
        // 分割线
        dialogContainer.getChildren().add(new Separator());
        // 按钮
        ButtonBar buttonBar = new ButtonBar();
        ButtonType[] buttonTypes = new ButtonType[]{ButtonType.OK, ButtonType.CANCEL};
        Map<ButtonType, BiConsumer<ActionEvent, Stage>> buttonHandlers = new HashMap<>();
        buttonBar.getButtons().addAll(
                Stream.of(buttonTypes)
                        .map(buttonType -> {
                            final Button button = new Button(buttonType.getText());
                            final ButtonBar.ButtonData buttonData = buttonType.getButtonData();
                            ButtonBar.setButtonData(button, buttonData);
                            button.setDefaultButton(buttonData.isDefaultButton());
                            button.setCancelButton(buttonData.isCancelButton());
                            button.setOnAction(event -> {
                                BiConsumer<ActionEvent, Stage> handler = buttonHandlers.get(buttonType);
                                if (handler != null) {
                                    handler.accept(event, stage);
                                }
                            });

                            return button;
                        })
                        .collect(Collectors.toList())
        );

        buttonHandlers.put(ButtonType.OK, (e1, stage1) -> {
            log.info("点击了确定");
            new2Controller.setting();
            stage1.close();
        });
        buttonHandlers.put(ButtonType.CANCEL, (e2, stage2) -> {
            log.info("点击了取消");
            stage2.close();
        });

        dialogContainer.getChildren().add(buttonBar);

        stage.setTitle("设置");
        stage.setScene(new Scene(dialogContainer));
        stage.setResizable(false);

        stage.getIcons().add(new Image("/icon/icon.png"));

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }

    public void newTab(ActionEvent event) {
        // ObservableList<Tab> paneMainTabs = tabPaneMain.getTabs();
        ObservableList<Tab> paneMainTabs = closeableTabPane.getTabPane().getTabs();
        String newTabName = ResourceBundleUtil.getStringValue("NewTab");

        if (event != null) {
            Tab tab;
            TextArea textArea;
            FilteredList<Tab> filtered = paneMainTabs.filtered((curTab) -> StringUtils.equals(curTab.getText(), newTabName));
            // 已存在的直接跳转选中
            if (filtered.isEmpty()) {
                tab = new Tab(newTabName);
                textArea = new TextArea();
                textArea.setText("这是新的Tab页" + newTabName);
                textArea.setFocusTraversable(true);
                tab.setContent(textArea);
                paneMainTabs.add(tab);
            } else {
                tab = filtered.get(0);
            }
            log.info(tab.getText() + " select");
            tabPaneMain.getSelectionModel().select(tab);
        }
    }

    public void newTab2(ActionEvent event) {
        ObservableList<Tab> paneMainTabs = tabPaneMain.getTabs();
        String newTabName = ResourceBundleUtil.getStringValue("NewTab2");

        if (event != null) {
            Tab tab;
            TextArea textArea;
            FilteredList<Tab> filtered = paneMainTabs.filtered((curTab) -> StringUtils.equals(curTab.getText(), newTabName));
            // 已存在的直接跳转选中
            if (filtered.isEmpty()) {
                tab = new Tab(newTabName);
                textArea = new TextArea();
                textArea.setText("这是新的Tab页" + newTabName);
                textArea.setFocusTraversable(true);
                tab.setContent(textArea);
                paneMainTabs.add(tab);
            } else {
                tab = filtered.get(0);
            }
            log.info(tab.getText() + " select");
            tabPaneMain.getSelectionModel().select(tab);
        }
    }

    boolean isClickedInAPane = false;
    Scene scene;

    private int flag = 90;
    private int reduceNum = 10;
    private int addNum = 10;

    public void openPic(ActionEvent event) {

        /*// 舞台
        Stage stage = new Stage();
        String openPic = ResourceBundleUtil.getStringValue("OpenPic");
        // 分隔Pane
        SplitPane sp = new SplitPane();

        ViewerPane viewerPane = new ViewerPane();
        FolderPane folderPane = new FolderPane(viewerPane);

        sp.getItems().addAll(folderPane, viewerPane);
        sp.setDividerPositions(0.275);
        SplitPane.setResizableWithParent(folderPane, false);

        Scene scene = new Scene(sp, 1024, 600);

        stage.setScene(scene);
        stage.setTitle(openPic);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();*/

        /*Stage stage = new Stage();
        BorderPane pane = new BorderPane();

        BorderPane apane = new BorderPane();
        apane.setPrefSize(50, 50);
        apane.setBackground(new Background(
                new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(0))));
        Button b = new Button("Test");
        apane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DemoController.this.isClickedInAPane = true;
                System.out.println("apane clicked!");
            }
        });

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getPickResult());

                System.out.println(event.isConsumed());
                if (DemoController.this.isClickedInAPane) {
                    System.out.println("i see!!!");
                } else {
                    DemoController.this.isClickedInAPane = false;
                    System.out.println("button clicked!");
                }

            }
        });

        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("button clicked!");
            }
        });

        TitledToolBar toolBar = new TitledToolBar();

        Button b1 = new Button("", new ImageView(new Image("/icons/delete.png", 16, 16, true, true)));
        b1.setId("1");
        b1.getStyleClass().add("left-pill");
        b1.setPrefSize(30, 30);
        Button b2 = new Button("2");
        b2.getStyleClass().add("center-pill");
        b2.setPrefSize(30, 30);
        Button b3 = new Button("3");
        b3.getStyleClass().add("right-pill");
        b3.setPrefSize(30, 30);

        HBox navButtons = new HBox(0, b1, b2, b3);

        toolBar.addLeftItems(navButtons);
        ProgressIndicator pi = new ProgressIndicator();
        pi.setPrefSize(30, 30);
        toolBar.addRightItems(new SearchBox(), pi);

        pane.setTop(toolBar);

        scene = new Scene(pane, 800, 600);
        setStylesheets();
        stage.setScene(scene);
        stage.show();*/

        Stage stage = new Stage();
        stage.setHeight(700);
        stage.setWidth(800);
        Image image = new Image("file:src/images/seePic.jpg");
        stage.getIcons().add(image);
        stage.setTitle("图片查看");
        VBox gp = new VBox();
        StackPane ap = new StackPane();
        ScrollPane sp = new ScrollPane();
        sp.setPrefHeight(570);
        sp.setPrefWidth(800);
        ap.setPrefHeight(570);
        ap.setPrefWidth(800);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //从不显示垂直ScrollBar
        sp.setPannable(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setContent(ap);
        //
        Label label = new Label();
        ap.setAlignment(label, Pos.CENTER);
        Image seeImage = new Image("/icon/icon.png");
        ImageView iv26 = new ImageView(seeImage);
        if (seeImage.getHeight() > 570) {
            iv26.setFitHeight(570);
        }
        if (seeImage.getWidth() > 800) {
            iv26.setFitWidth(800);
        }
        label.setGraphic(iv26);
        ap.getChildren().add(label);
        //设置下面的部分
        HBox hbox = new HBox();
        Button add = new Button();

        add.setPrefWidth(40);
        Image image1 = new Image("/icon/icon.png");
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(image1.getHeight() / 1.3);
        iv1.setFitWidth(image1.getWidth() / 1.3);
        add.setGraphic(iv1);
        add.setStyle("-fx-background-color: rgba(255,255,255,0.1)");
        add.setOnMouseClicked(mouseEvent -> {
            iv26.setFitHeight(iv26.getFitHeight() + addNum);
            iv26.setFitWidth(iv26.getFitWidth() + addNum);
            label.setGraphic(iv26);
            ap.setAlignment(label, Pos.CENTER);
        });
        add.setOnMouseEntered(mouseEvent -> add.setStyle("-fx-background-color: rgba(190,190,190,0.4)"));
        add.setOnMouseExited(mouseEvent -> add.setStyle("-fx-background-color: rgba(255,255,255,0)"));
        Button reduce = new Button();
        reduce.setPrefWidth(40);
        Image image2 = new Image("/icon/icon.png");
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(image2.getHeight() / 1.3);
        iv2.setFitWidth(image2.getWidth() / 1.3);
        reduce.setGraphic(iv2);
        reduce.setStyle("-fx-background-color: rgba(255,255,255,0)");

        reduce.setOnMouseClicked(mouseEvent -> {
            iv26.setFitHeight(iv26.getFitHeight() - reduceNum);
            iv26.setFitWidth(iv26.getFitWidth() - reduceNum);
            label.setGraphic(iv26);
            ap.setAlignment(label, Pos.CENTER);
        });
        reduce.setOnMouseEntered(mouseEvent -> reduce.setStyle("-fx-background-color: rgba(190,190,190,0.4)"));
        reduce.setOnMouseExited(mouseEvent -> reduce.setStyle("-fx-background-color: rgba(255,255,255,0)"));
        Button radio = new Button();
        radio.setPrefWidth(40);
        Image image3 = new Image("/icon/icon.png");
        ImageView iv3 = new ImageView(image3);
        iv3.setFitHeight(image3.getHeight() / 1.3);
        iv3.setFitWidth(image3.getWidth() / 1.3);
        radio.setGraphic(iv3);
        radio.setStyle("-fx-background-color: rgba(255,255,255,0)");
        radio.setOnMouseClicked(mouseEvent -> {
            radio.setStyle("-fx-background-color: rgba(190,190,190,0.4)");
            if (label.getHeight() > 570) {
                label.setPrefHeight(570);
            }
            label.setRotate(flag);
            flag += 90;
        });
        radio.setOnMouseEntered(mouseEvent -> radio.setStyle("-fx-background-color: rgba(190,190,190,0.4)"));
        radio.setOnMouseExited(mouseEvent -> radio.setStyle("-fx-background-color: rgba(255,255,255,0)"));
        hbox.setSpacing(40);
        hbox.getChildren().addAll(add, radio, reduce);
        hbox.setPadding(new Insets(30, 0, 0, 280));
        gp.getChildren().addAll(sp, hbox);
        gp.setStyle("-fx-background-color: white");
        Scene scene = new Scene(gp);
        stage.setScene(scene);
        stage.show();
    }

    private void setStylesheets() {
        final String EXTERNAL_STYLESHEET = "http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600";
        scene.getStylesheets().setAll("/css/EnsembleStylesCommon.css");
        Thread backgroundThread = new Thread(() -> {
            try {
                URL url = new URL(EXTERNAL_STYLESHEET);
                try (
                        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        Reader newReader = Channels.newReader(rbc, "ISO-8859-1");
                        BufferedReader bufferedReader = new BufferedReader(newReader)
                ) {
                    // Checking whether we can read a line from this url
                    // without exception
                    bufferedReader.readLine();
                }
                Platform.runLater(() -> {
                    // when succeeded add this stylesheet to the scene
                    scene.getStylesheets().add(EXTERNAL_STYLESHEET);
                });
            } catch (MalformedURLException ex) {

            } catch (IOException ex) {

            }
        }, "Trying to reach external styleshet");
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
}
