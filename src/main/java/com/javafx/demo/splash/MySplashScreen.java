package com.javafx.demo.splash;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Parent;
import org.springframework.stereotype.Component;

/**
 * 启动首屏图标加载
 *
 * @author
 * @date
 */
@Component
public class MySplashScreen extends SplashScreen {
    private static final String DEFAULT_IMAGE = "/splash/javafx.png";

    public MySplashScreen() {
        super();
    }

    @Override
    public Parent getParent() {
        return super.getParent();
    }

    @Override
    public boolean visible() {
        return true;
    }

    @Override
    public String getImagePath() {
        return "/images/splash_screen.png";
    }
}
