package org.example.jvm.error;

import java.util.ArrayList;
import java.util.List;

/**
 *  仿真OOM
 *  1. 通过运行 java -verbose:gc -Xmn10M -Xms20M -Xmx20M -XX:+PrintGC OOMExam
 */
public class OOMHeapSpaceExam {

    public static void main(String[] args) {
        List<byte[]> buffer = new ArrayList<>();
        buffer.add(new byte[10 * 1024 * 1024]);
    }

}
