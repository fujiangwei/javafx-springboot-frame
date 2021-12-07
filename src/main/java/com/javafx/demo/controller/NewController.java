package com.javafx.demo.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author
 * @date
 */
@Slf4j
@FXMLController
public class NewController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void test() {
        log.info("NewController test");
    }
}
