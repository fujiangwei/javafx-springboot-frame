package com.javafx.demo;

import com.javafx.demo.pages.AlertWindow;
import com.javafx.demo.splash.MySplashScreen;
import com.javafx.demo.utils.ConfigUtil;
import com.javafx.demo.utils.StageUtils;
import com.javafx.demo.view.DemoView;
import com.jfoenix.controls.JFXDecorator;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.GUIState;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * @author
 * @date
 */
@SpringBootApplication
@Slf4j
public class JavafxDemoApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        // 初始化语言环境
        String localeString = ConfigUtil.get(ConfigUtil.Keys.Locale, "");
        if (StringUtils.isNotEmpty(localeString)) {
            String[] locale1 = localeString.split("_");
            ConfigUtil.defaultLocale = new Locale(locale1[0], locale1[1]);
        }
        launch(JavafxDemoApplication.class, DemoView.class, new MySplashScreen(), args);
    }

    @Override
    public void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
        super.beforeInitialView(stage, ctx);

        JFXDecorator decorator = new JFXDecorator(stage, new AnchorPane(), true, true, true);
        decorator.setCustomMaximize(true);
        decorator.setTitle("");
        if (StringUtils.isNotEmpty("")) {
            ImageView imageView = new ImageView(new Image("/icon/icon.png"));
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            decorator.setGraphic(imageView);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.width * 0.74;
        double screenHeight = screenSize.height * 0.8;
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        // 解决屏幕缩放问题
        if (screenWidth > bounds.getWidth() || screenHeight > bounds.getHeight()) {
            screenWidth = bounds.getWidth();
            screenHeight = bounds.getHeight();
        }

        Scene scene = new Scene(decorator, screenWidth, screenHeight);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        URL cssUrl = getClass().getResource("/css/jfoenix-main.css");
        if (cssUrl != null) {
            stylesheets.addAll(cssUrl.toExternalForm());
        }

        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                boolean b = AlertWindow.display("提示", "确认退出系统吗？");
                if (b) {
                    System.exit(0);
                } else {
                    event.consume();
                }
            }
        });
        // 重新设置新的场景
        GUIState.setScene(scene);
        Platform.runLater(() -> {
            log.info("Platform.runLater");
            // 运行后更新javafx默认的舞台风格
            StageUtils.updateStageStyle(GUIState.getStage());
        });
    }

    /**
     * 首屏启动时的应用图标加载默认图标
     *
     * @return
     */
    @Override
    public Collection<Image> loadDefaultIcons() {
        return Collections.singletonList(new Image(getClass().getResource("/icon/icon.png").toExternalForm()));
    }
}
