package com.zg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Main.class.getClassLoader().getResource("app.fxml");
        if (url == null){
            throw new RuntimeException("app.fxml 文件没有找到");
        }
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        primaryStage.setTitle("本地文件搜索项目");
        primaryStage.setWidth(1000);//宽度
        primaryStage.setHeight(900);//高度

        primaryStage.setScene(scene);//将场景设置到舞台上面
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
