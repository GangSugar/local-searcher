package sample;

import java.io.File;

public class FileMeta {
    private File file;

    public FileMeta(File file) {
        this.file = file;
    }

    public String getDirectory(){
        if (file.isDirectory()){
            return "文件夹";
        }else {
            return "普通文件";
        }
    }

    public String getPath(){
        return file.getAbsolutePath();
    }

    @Override
    public String toString() {
//        return "FileMeta{" +
//                "file=" + file +
//                '}';
        return file.toString();
    }
}
