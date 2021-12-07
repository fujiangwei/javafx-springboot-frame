/**
 *
 */
package com.javafx.demo.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * 显示目录及进行目录树操作
 * @author
 * @date
 *
 */
public class FolderPane extends BorderPane {
    /**
     * 标题区域
     */
    private HBox titleBox;
    /**
     * 显示标题的Label
     */
    private Label titleLabel;
    /**
     * 目录树
     */
    private TreeView<File> folderTree;

    /**
     * 关联的ViewerPane
     */
    private ViewerPane viewerPane;

    public FolderPane(ViewerPane viewerPane) {
        this.viewerPane = viewerPane;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 初始化标题区域
        titleBox = new HBox();
        titleBox.setPadding(new Insets(15));

        titleLabel = new Label("我的电脑", new ImageView(new Image("/icons/computer.png", 24, 24, true, true)));
        titleLabel.setFont(new Font("Microsoft YaHei", 18.0));
        titleLabel.setTextFill(Color.BLUE);

        titleBox.getChildren().add(titleLabel);

        // 初始化目录树区域 创建根节点，对应的目录是虚拟的
        FolderTreeItem root = new FolderTreeItem(new File("ROOT"), true);
        // 加载根节点的所有子节点
        root.loadChildren();

        // root.setExpanded(true);
        // 以root为根节点创建目录树
        folderTree = new TreeView<>(root);
        // 目录树不显示根节点
        folderTree.setShowRoot(false);
        // 目录树使用单选模式
        folderTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        folderTree.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));

        // 设置节点的CELL绘制方式
        folderTree.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
            @Override
            public TreeCell<File> call(TreeView<File> treeView) {
                return new TreeCell<File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        // empty 表示有无数据与cell关联
                        super.updateItem(item, empty);
                        setFont(new Font("Microsoft YaHei", 14.0));

                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            ImageView icon = null;
                            if (((FolderTreeItem) getTreeItem().getParent()).isRoot) {
                                // 磁盘
                                icon = new ImageView(new Image("/icons/tree_harddisk.png", 16, 16, true, true));
                            } else {
                                // 目录
                                icon = new ImageView(new Image("/icons/folder.png", 16, 16, true, true));
                            }
                            FileSystemView fsv = FileSystemView.getFileSystemView();
                            String text = fsv.getSystemDisplayName(item);
                            setText(text);
                            setGraphic(icon);
                        }
                    }

                };
            }
        });

        // 设置TreeView的选中节点变化的事件处理
        folderTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<File>>() {

            @Override
            public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue,
                                TreeItem<File> newValue) {
                if (newValue == null) {
                    return;
                }
                // 将选中目录设置到viewerPane
                viewerPane.setSelectedFolder(newValue.getValue());
            }

        });

        // 加载根节点的所有子节点的子节点
        for (TreeItem<File> item : root.getChildren()) {
            ((FolderTreeItem) item).loadChildren();
        }

        // 设置第1个节点为默认选中节点
        // folderTree.getSelectionModel().clearAndSelect(0);

        // 组装
        this.setTop(titleBox);
        this.setCenter(folderTree);
    }

    /**
     * 内部类，代表目录节点的Item
     */
    private class FolderTreeItem extends TreeItem<File> {
        /**
         * 代表当前节点对象的子节点是否已经加载
         */
        private boolean notChildrenLoaded = true;
        /**
         * 该节点的目录对象是否是顶层
         */
        private boolean isRoot;

        public FolderTreeItem(File file, boolean isRoot) {
            super(file);
            this.isRoot = isRoot;
        }

        /**
         * 加载当前节点的子节点
         */
        public void loadChildren() {
            if (notChildrenLoaded) {
                notChildrenLoaded = false;

                File[] children = null;

                if (!isRoot) {
                    File dir = this.getValue();
                    children = dir.listFiles();
                } else {
                    children = File.listRoots();
                }
                // 如果获得的目录内容为null, 直接返回
                if (children == null) {
                    return;
                }
                // 依次处理所有子目录及文件
                for (File child : children) {
                    if (child.isDirectory() && (isRoot || !child.isHidden())) {
                        FolderTreeItem item = new FolderTreeItem(child, false);

                        // 为TreeItem增加事件处理，处理的事件是TreeItem展开，
                        // 当展开一个TreeItem时，加载其所有子节点的子节点
                        item.addEventHandler(FolderTreeItem.branchExpandedEvent(),
                                new EventHandler<TreeModificationEvent<File>>() {

                                    @Override
                                    public void handle(TreeModificationEvent<File> event) {
                                        for (TreeItem<File> child : event.getSource().getChildren()) {
                                            ((FolderTreeItem) child).loadChildren();
                                        }
                                    }

                                });

                        this.getChildren().add(item);
                    }
                }
            }
        }
    }
}
