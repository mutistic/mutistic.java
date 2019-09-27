package com.mutistic.file.move;

import java.io.File;

/**
 * @author yinyc
 * @version 1.0 2019/9/26
 */
public class MoveFile {

  public static void main(String[] args) {
    String directoryPath = "C:\\document\\baidu\\Java并发编程";
    String prefix = "Java并发编程";
    renFileName(directoryPath, prefix);
  }


  /**
   * 文件重命名
   *
   * @param directoryPath
   * @param prefixName
   */
  public static void renFileName(String directoryPath, String prefixName) {
    File directoryFile = new File(directoryPath);
    // 判断是否是目录
    if (!directoryFile.isDirectory()) {
      return;
    }

    // 文件名新目录
    String newFilePath = null;
    // 获取目录下的所有文件
    int i = 44;
    for (File file : directoryFile.listFiles()) {
      // 只循环一次
//      if (file.isDirectory()) {
//        for (File listFile : file.listFiles()) {
//          renFileName(listFile, directoryPath, prefixName + (i < 10 ? ("0"+i) : i));
//        }
//      } else {
//        renFileName(file, directoryPath, prefixName+ (i < 10 ? ("0"+i) : i));
//      }
      renFileName(file, directoryPath, prefixName +"_"+ (i < 10 ? ("0"+i) : i));
      i++;
    }
    System.out.println("文件重命名成功！");
  }

  public static void renFileName(File file, String directoryPath, String prefixName) {
    // 判断是否是mp4格式文件
    if (! (file.getName().contains(".mp4") || file.getName().contains(".mkv"))) {
      return;
    }
//    if(file.getName().substring())

    // renameTo 重命名
    String name = file.getName();
    name = name.replace("并发-", "");
    if(name.contains(" ")){
      name = name.replace(name.substring(0, name.indexOf(" ")+1), "");
    }
//    if(name.contains("-")){
//      name = name.replace(name.substring(0, name.indexOf("-")+1), "");
//    }
    name = name.replace(" ", "");
    file.renameTo(new File(directoryPath + "\\" + prefixName + "_" + name));
    System.out.println(directoryPath + "\\" + prefixName + "_" + name);
  }


}
