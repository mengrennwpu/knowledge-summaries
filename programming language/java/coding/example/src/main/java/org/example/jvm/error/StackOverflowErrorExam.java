package org.example.jvm.error;

/**
 * 通过死递归实现StackOverflowError
 */
public class StackOverflowErrorExam {

    public void stackOverflowMethod(){
        stackOverflowMethod();
    }

    public static void main(String[] args) {
        StackOverflowErrorExam exam = new StackOverflowErrorExam();
        exam.stackOverflowMethod();
    }

}
