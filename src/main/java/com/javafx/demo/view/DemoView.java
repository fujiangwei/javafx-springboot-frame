package com.javafx.demo.view;

import com.javafx.demo.pages.AlertWindow;
import com.javafx.demo.utils.ConfigUtil;
import com.jfoenix.controls.JFXDecorator;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author
 * @date
 */
@Slf4j
@FXMLView(value = "/fxml/demo.fxml", css = "", bundle = "i18n.message")
public class DemoView extends AbstractFxmlView {
    public DemoView() throws Exception {
        log.info("DemoView ResourceBundle...");
        // 反射修改默认语言
        ResourceBundle bundle = ResourceBundle.getBundle(this.getResourceBundle().get().getBaseBundleName(), ConfigUtil.defaultLocale);
        FieldUtils.writeField(this, "bundle", Optional.ofNullable(bundle), true);
        // 修改标题国际化
        GUIState.getStage().setTitle(bundle.getString("Title"));
    }

    @Override
    public Parent getView() {
        // 经过装饰的首页视图
        JFXDecorator decorator = new JFXDecorator(GUIState.getStage(), super.getView(),
                true, true, true);
        decorator.setCustomMaximize(true);
        // 会覆盖application配置的值
        // decorator.setTitle(GUIState.getStage().getTitle());
        // 修饰框右上角的图标，设置会覆盖application配置的图标
        if (StringUtils.isNotEmpty("/icon/icon.png")) {
            ImageView imageView = new ImageView(new Image("/icon/icon.png"));
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            decorator.setGraphic(imageView);
        }

        decorator.setOnCloseButtonAction(() -> {
            boolean b = AlertWindow.display("提示", "确认退出系统吗？");
            if (b) {
                System.exit(0);
            }
        });

        return decorator;
    }
}
