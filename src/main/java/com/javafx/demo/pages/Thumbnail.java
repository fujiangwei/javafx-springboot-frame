package com.javafx.demo.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;

/**
 * 缩略图类，继承BorderPane
 *
 * <pre>
 * 文件名用Label显示，放置在BorderPane的BOTTOM；
 * 图片用Canvas绘制；CheckBox用于控制是否被选中；
 * Canvas和CheckBox放置在一个Pane上；
 * Pane放在BorderPane的CENTER。
 * </pre>
 *
 * @author
 * @date
 */
public class Thumbnail extends BorderPane {
    /**
     * 缩略图尺寸
     */
    public static final double THUMBNAIL_WIDTH = 160;
    public static final double THUMBNAIL_HEIGHT = 120;

    /**
     * 缩略图关联的文件
     */
    private File imageFile;

    /**
     * 缩略图所在的父容器
     */
    private ViewerPane viewerPane;

    /**
     * 缩略图的选中状态
     */
    private CheckBox selectedBox;
    /**
     * 缩略图的文件名
     */
    private Label nameLabel;
    /**
     * 绘制缩略图的画布
     */
    private Canvas canvas;
    /**
     * 组合selectedBox和canvas的pane
     */
    private Pane imagePane;

    public Thumbnail(File imageFile, ViewerPane viewerPane) {
        super();
        this.imageFile = imageFile;
        this.viewerPane = viewerPane;
        buildThumbnail();
    }

    public boolean rename(String newName) {
        String absName = imageFile.getParent() + "/" + newName;
        File dest = new File(absName);
        if (imageFile.renameTo(dest)) {
            imageFile = dest;
            this.nameLabel.setText(imageFile.getName());

            return true;
        }
        return false;
    }

	/**
	 * 构造缩略图
	 */
	private void buildThumbnail() {
        // 1. 选择框，创建选择框
        selectedBox = new CheckBox();
		// 初始为不可见
        selectedBox.setVisible(false);
        selectedBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    viewerPane.selectedThumbnailsProperty().add(Thumbnail.this);
                } else {
                    viewerPane.selectedThumbnailsProperty().remove(Thumbnail.this);
                }
            }
        });

        // 2. 画布Canvas，创建绘制图片的画布
        canvas = new Canvas(Thumbnail.THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
		// 获得画笔对象
        GraphicsContext gc = canvas.getGraphicsContext2D();
		// 读取图片文件，按照缩略图大小读取，并保持宽高比例
        Image image = new Image(imageFile.toURI().toString(), Thumbnail.THUMBNAIL_WIDTH - 2,
                Thumbnail.THUMBNAIL_HEIGHT - 2, true, true);

        // 计算绘制图片的坐标
        double x = (Thumbnail.THUMBNAIL_WIDTH - image.getWidth()) / 2;
        double y = (Thumbnail.THUMBNAIL_HEIGHT - image.getHeight()) / 2;
        // gc.setStroke(Color.BLACK);
        // gc.strokeRect(0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
		// 绘制图片到Canvas
        gc.drawImage(image, x, y);

        // 3. 文件名Label，使用文件名创建Label
        nameLabel = new Label(imageFile.getName());
        nameLabel.prefWidthProperty().bind(canvas.widthProperty());
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setTooltip(new Tooltip(imageFile.getName()));

        // 4. 绘图的Pane，创建绘图Pane
        imagePane = new Pane();
        imagePane.setStyle("-fx-background-color: lightgray;");
        // 绘图Pane放置Canvas
        imagePane.getChildren().add(canvas);
		// 绘图Pane放置selectBox
        imagePane.getChildren().add(selectedBox);

        // 5. 组合缩略图的BorderPane
        this.setStyle("-fx-background-color: white; -fx-hover: lightblue;");
        this.setMaxWidth(Thumbnail.THUMBNAIL_WIDTH);
        this.setHover(true);
        this.setCenter(imagePane);
        this.setBottom(nameLabel);

        // 6. 鼠标事件
        this.setMouseTransparent(false);
        // 6.1 进入缩略图
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedBox.setVisible(true);
                Thumbnail.this.setStyle(
                        "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
            }
        });

        // 6.2 离开缩略图
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedBox.setVisible(selectedBox.isSelected());
                Thumbnail.this.setStyle("-fx-background-color: transparent");
            }
        });

        // 6.3 鼠标单击，选中当前缩略图，取消其他缩略图的选中状态
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) { // 左键
                    ObservableSet<Thumbnail> thumbnails = viewerPane.selectedThumbnailsProperty().get();
                    // System.out.println(thumbnails.contains(Thumbnail.this));
                    if (thumbnails.contains(Thumbnail.this)) {
                        thumbnails.retainAll(FXCollections.observableSet(Thumbnail.this));
                    } else {
                        thumbnails.clear();
                        thumbnails.add(Thumbnail.this);
                    }
                } else {

                }
            }
        });

    }

    public ViewerPane getViewerPane() {
        return viewerPane;
    }

    public void setViewerPane(ViewerPane viewerPane) {
        this.viewerPane = viewerPane;
    }

    public File getImageFile() {
        return imageFile;
    }

	/**
	 * 选中状态
	 * @return
	 */
	public boolean isSelected() {
        return selectedBox.isSelected();
    }

    public void setSelected(boolean selected) {
        selectedBox.setSelected(selected);
        selectedBox.setVisible(selected);
    }

}
