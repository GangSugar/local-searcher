package sample;

import file_scan.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Date;
import java.util.Random;

public class Controller {
    @FXML
    public TextField inputField;//这样文本框输入之后，就能够拿到文本框的内容

    @FXML
    public Label outputLabel;

    @FXML
    public GridPane rootGridPane;

    private Thread thread = null;

    @FXML
    public TableView<Student> tableView;

    @FXML
    public void zg(MouseEvent mouseEvent) {
        System.out.println(new Date()+"按钮被点击");

        String inputText = inputField.getText();
        System.out.println("用户输入了"+inputText);

        //给输出文本框设置输入的内容
        outputLabel.setText(inputText);
    }


    private int id = 1;
    private Random random = new Random(20210211);
    private String[] GENDERS = {"男","女"};
    private void addStudent(String name){
        int age = random.nextInt(20)+20;
        String gender = GENDERS[random.nextInt(2)];
        Student student = new Student(id++,name,age,gender);

        //ObservableList允许别人修改
        ObservableList<Student> items = tableView.getItems();//tableView.getItems()表示拿其中的项
        items.add(student);//将student对象添加进去
        System.out.println(student);
    }

    @FXML
    public void 点击事件(MouseEvent mouseEvent) {


        System.out.println(new Date()+"按钮被点击");

        String inputText = inputField.getText();
        System.out.println("用户输入了"+inputText);

        addStudent(inputText);
        //给输出文本框设置输入的内容
        //outputLabel.setText(inputText);

//        try {
//            int n = Integer.parseInt(inputText.trim());
//            if (thread != null){
//                thread.interrupt();//把它停掉,打断
//            }
//            thread = new Thread(() -> {
//                long r = fib(n);//计算在子线程
//
//                System.out.printf("fib(%d) = %d\n",n,r);
//                if (Thread.interrupted()){
//                    return;
//                }
//                Platform.runLater(() -> {//显示回到主线程
//                    //以事件的方式通知主线程执行该操作
//                    outputLabel.setText(String.valueOf(r));
//
//                });
//            });
//            thread.start();
//        } catch (NumberFormatException e) {
//            outputLabel.setText("请输入合法的数字");
//        }
    }

    private long fib(int n){
        if(n < 2){
            return 1;
        }
        return fib(n-1)+fib(n-2);
    }


    private Thread scanThread = null;
    @FXML
    public void 选择文件夹(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        Window window = rootGridPane.getScene().getWindow();
        File root = chooser.showDialog(window);//弹出对话框，进行文件夹的选择
        System.out.println(root);
        if (root == null){
            return;
        }
        //这样有一个不好的地方就是所有扫描全在主线程里面，但是主线程里面不能执行运行时间比较长的操作
        //因此要将这个东西都放到子线程去执行遍历的操作
        //Main.traversalDepth(root);//进行遍历
        scanThread = new Thread(() -> {
            //Main.traversalDepth(root);
            //把结果放到表里面
        });
        scanThread.setDaemon(true);//一个守护线程，是一个后台线程，Daemon是精灵的意思就是默默守护
        scanThread.start();//启动子线程
        //JVM退出条件，所有前台线程都退掉
    }
}
