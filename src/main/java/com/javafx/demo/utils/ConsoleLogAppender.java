package com.javafx.demo.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.tools.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 日志打印控制台
 * @author:
 * @date:
 */
@Data
@Slf4j
public class ConsoleLogAppender extends OutputStreamAppender<ILoggingEvent> {
    public final static List<TextArea> TEXTAREA_LIST = new ArrayList<>();

    @Override
    public void start() {
        OutputStream targetStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                for (TextArea textArea : TEXTAREA_LIST) {
                    textArea.appendText(b + "\n");
                }
            }

            @Override
            public void write(byte[] b) throws IOException {
                for (TextArea textArea : TEXTAREA_LIST) {
                    textArea.appendText(new String(b) + "\n");
                }
            }
        };
        setOutputStream(targetStream);
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (eventObject.getLevel() == Level.ERROR) {
            try {
                Window window = Utils.getWindow(null);
                double x = window.getX() + window.getWidth() / 2;
                double y = window.getY() + window.getHeight();
                String message = "发生错误:\n" + eventObject.getFormattedMessage();
                Tooltip tooltip = new Tooltip(message);
                tooltip.setAutoHide(true);
                tooltip.setOpacity(0.9d);
                tooltip.setWrapText(true);
                tooltip.show(window, x, y);
                tooltip.setAnchorX(tooltip.getAnchorX() - tooltip.getWidth() / 2);
                tooltip.setAnchorY(tooltip.getAnchorY() - tooltip.getHeight());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> tooltip.hide());
                    }
                }, 3000);
            } catch (Exception e) {
                log.error("ConsoleLogAppender 异常");
            }
        }
        super.append(eventObject);
    }
}
