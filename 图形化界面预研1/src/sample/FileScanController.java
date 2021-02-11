package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

public class FileScanController {
    @FXML
    public GridPane rootGridPane;
    @FXML
    public TableView<FileMeta> tableView;


    private Thread scanThread = null;
    @FXML
    public void 选择文件夹(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        Window window = rootGridPane.getScene().getWindow();
        File root = chooser.showDialog(window);//弹出对话框，进行文件夹的选择
        //System.out.println(root);
        if (root == null){
            return;
        }
        //这样有一个不好的地方就是所有扫描全在主线程里面，但是主线程里面不能执行运行时间比较长的操作
        //因此要将这个东西都放到子线程去执行遍历的操作
        //Main.traversalDepth(root);//进行遍历
        scanThread = new Thread(() -> {
            //Main.traversalDepth(root);
            //把结果放到表里面
            scan(root);
        });
        scanThread.setDaemon(true);//一个守护线程，是一个后台线程，Daemon是精灵的意思就是默默守护
        scanThread.start();//启动子线程
        //JVM退出条件，所有前台线程都退掉
    }

    //调用该方法，既有文件夹，也有普通文件
    private void scan(File root){
        FileMeta fileMeta = new FileMeta(root);
        //放到tableview中
        Platform.runLater(() -> {
            tableView.getItems().add(fileMeta);//涉及到UI修改的，放到主线程当中
        });


        if (!root.isDirectory()){
            return;
        }

        File[] children = root.listFiles();
        if (children == null){
            return;
        }

        for (File child : children){
            scan(child);
        }
    }
}
