package org.example.jvm.error;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 运行参数：-verbose:gc -Xmn5M -Xms10M -Xmx10M -XX:MaxPermSize=1M -XX:+PrintGC
 * 1. 通过String.intern()方法模拟持久代溢出
 * 2. 通过加载类模拟
 */
public class OOMPermGenSpaceExam {

    public static void main(String[] args){
        OOMPermGenSpaceExam exam = new OOMPermGenSpaceExam();
        exam.intern();
//        exam.loadClasses();
    }

    public void intern(){
        List<String> list = new ArrayList<>();
        while(true){
            list.add(UUID.randomUUID().toString().intern());
        }
    }

    public void loadClasses() throws Exception{
        List<ClassLoader> loaders = new ArrayList<>();
        while (true){
            ClassLoader loader = new URLClassLoader(new java.net.URL[]{new File("/tmp").toURI().toURL()});
            loaders.add(loader);
            loader.loadClass("org.example.jvm.error.OOMPermGenSpaceExam");
        }
    }

}
