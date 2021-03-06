package com.zg.ui;

import com.zg.model.FileMeta;
import com.zg.service.FileService;
import com.zg.task.FileScanner;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
//这里需要继承一个接口，Initializable，重写它的方法initialize()
public class AppController implements Initializable {
    @FXML
    public GridPane rootPane;
    @FXML
    public Label srcDirectory;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<FileMeta> fileTable;
    @FXML
    public TableColumn<FileMeta,String> nameColumn;
    @FXML
    public TableColumn<FileMeta,String> sizeColumn;
    @FXML
    public TableColumn<FileMeta,String> lastModifiedColumn;

    private final FileService fileService = new FileService();
    private final FileScanner fileScanner = new FileScanner();
    @FXML
    public void choose(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File root = chooser.showDialog(rootPane.getScene().getWindow());
        if (root == null) {
            return;
        }

        Thread thread = new Thread(() -> {
            fileScanner.scan(root);
        });
        thread.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 会在 FXMLLoader.load 执行时，实例化好 AppController 后调用
        StringProperty stringProperty = searchField.textProperty();
        stringProperty.addListener((observable, oldValue, newValue) -> {

            List<FileMeta> fileList = fileService.query(newValue.trim());
            //将查询结果显示出来
            Platform.runLater(() -> {
                fileTable.getItems().clear();//每次查询都要更新一下
                fileTable.getItems().addAll(fileList);
            });
        });
    }
}
