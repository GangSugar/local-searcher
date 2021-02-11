package sample;
import java.net.URL;

public class ResourceDemo {
    public static void main(String[] args) {
        URL r1 = ResourceDemo.class.getResource("root.fxml");
        URL r2 = ResourceDemo.class.getClassLoader().getResource("root.fxml");//不在同样一级目录下面必须通过getClassLoader去加载
        URL r3 = ResourceDemo.class.getResource("sample.fxml");//在同样一个目录下面，就需要这样加载
        URL r4 = ResourceDemo.class.getClassLoader().getResource("sample.fxml");
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
    }
}
