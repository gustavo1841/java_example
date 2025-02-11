package org.example.image;

import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.util.List;

public class ChangeName {

    public static void main(String[] args) {
        List<File> list = FileUtil.loopFiles("E:\\新建文件夹\\d");
        int index = 95;
        for(int i=0;i<list.size();i++){
            //原名称
            String oldFileName  = list.get(i).getName();
            //原后缀名
            String suffix = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
            //原名称（无后缀）
            String fileName = oldFileName.substring(0,oldFileName.lastIndexOf(".") );
            //新名称
            String newFileName = index+".jpg";
            //重命名 路径+新名称
            list.get(i).renameTo(new File("E:\\新建文件夹\\a\\"+newFileName));
            index++;
        }
        for(int i=95;i<=205;i++){
            String template = "- /medias/featureimages/%s.jpg";
            String format = String.format(template, i);
            System.out.println(format);
        }
    }
}
