package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;

public class Main extends Application {
    private static Parent build(){
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //输入文本框
        TextField textField = new TextField();
        gridPane.add(textField,0,0);

        //点击按钮
        Button button = new Button();
        button.setText("click me");
        gridPane.add(button,0,1);

        //输出文本框
        Label label = new Label();
        label.setPrefWidth(200);
        label.setStyle("-fx-border-color: black; -fx-border-width: 1;-fx-border-radius: 5");
        gridPane.add(label,0,2);


        //自己绑定逻辑
        Controller controller = new Controller();
        controller.inputField = textField;
        controller.outputLabel = label;

//        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                controller.点击事件(event);
//            }
//        });
        button.setOnMouseClicked(controller::点击事件);




        //关于"表的实验
        TableView<Student> tableView = new TableView<>();
        gridPane.add(tableView,0,3);


        //表中的列
        TableColumn<Student,String> idColumn = new TableColumn<>();
        idColumn.setText("#ID");//设置列的名称
//        idColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> param) {
//                return param.getValue().getIdValue();
//            }
//        });
        idColumn.setCellValueFactory(param -> param.getValue().getIdValue());

        TableColumn<Student,String> nameColumn = new TableColumn<>();
        nameColumn.setText("姓名");//设置列的名称
        nameColumn.setCellValueFactory(param -> param.getValue().getNameValue());

        TableColumn<Student,String> ageColumn = new TableColumn<>();
        ageColumn.setText("年龄");//设置列的名称
        ageColumn.setCellValueFactory(param -> param.getValue().getAgeValue());

        TableColumn<Student,String> genderColumn = new TableColumn<>();
        genderColumn.setText("性别");//设置列的名称
        genderColumn.setCellValueFactory(param -> param.getValue().getGenderValue());
        //将设置好的列标签添加到表当中
        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(ageColumn);
        tableView.getColumns().add(genderColumn);
        //关于表的实验结束


        controller.tableView = tableView;

        return gridPane;
    }

    /**
     * stage：舞台
     * scene：场景
     * Control：控件
     * Layout：布局（1.绝对布局，2.相对布局，3.网格布局(GridPane)）
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader.load(URL);
//        URL r1 = this.getClass().getResource("sample.fxml");
//        URL r2 = this.getClass().getClassLoader().getResource("sample.fxml");
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));//将Main和fxml关联起来
        //Parent root = build();

        Parent root = FXMLLoader.load(getClass().getResource("file-scan.fxml"));//将Main和fxml关联起来

        primaryStage.setTitle("文件搜索项目");//设置标题
        primaryStage.setScene(new Scene(root, 600, 550));//设置场景,将sample.fxml内容当作一个场景设置到primaryStage这个舞台上面
        primaryStage.show();//将这个舞台显示出来
    }


    public static void main(String[] args) {
        launch(args);//必须写的
    }
}
