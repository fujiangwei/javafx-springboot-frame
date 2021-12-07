package com.javafx.demo.controller;

import com.javafx.demo.utils.ConfigUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @date
 */
@Slf4j
@FXMLController
public class New2Controller implements Initializable {

    @FXML
    private CheckBox saveStageBoundLocation;

    @FXML
    private CheckBox exitShowAlertCheckBox;

    @FXML
    private CheckBox saveStageBoundSize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            exitShowAlertCheckBox.setSelected(ConfigUtil.getBoolean(ConfigUtil.Keys.ConfirmExit, true));
            saveStageBoundSize.setSelected(ConfigUtil.getBoolean(ConfigUtil.Keys.NotepadEnabled, true));
            saveStageBoundLocation.setSelected(ConfigUtil.getBoolean(ConfigUtil.Keys.RememberWindowLocation, true));
        } catch (Exception e) {
            log.error("加载配置失败：", e);
        }
    }

    /**
     * 设置确定操作
     */
    public void setting() {
        try {
            log.info("setting");
            try {
                ConfigUtil.set(ConfigUtil.Keys.ConfirmExit, exitShowAlertCheckBox.isSelected());
                ConfigUtil.set(ConfigUtil.Keys.NotepadEnabled, saveStageBoundSize.isSelected());
                ConfigUtil.set(ConfigUtil.Keys.RememberWindowLocation, saveStageBoundLocation.isSelected());
            } catch (Exception e) {
                log.error("保存配置失败：", e);
            }
        } catch (Exception e) {
            log.error("保存配置失败：", e);
        }
    }
}
