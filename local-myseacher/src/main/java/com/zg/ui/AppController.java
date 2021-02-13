package com.zg.ui;

import com.zg.model.FileMeta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class AppController {
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


    @FXML
    public void choose(MouseEvent mouseEvent) {
    }
}
