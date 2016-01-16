package ru.spbau.sergeev.jtester;

/**
 * @author pavel
 */
public class Main {
    public static void main(String[] args) throws Exception {
        com.sun.tools.javac.Main.main(new String[]{
                "-proc:only",
                "-processor",
                "ru.spbau.sergeev.jtester.TestSpeedProcessor",
                "examples/src/ru/spbau/sergeev/examples/SimpleClass.java"});
              // "examples/src/ru/spbau/sergeev/basic/A.java",
              //  "examples/src/ru/spbau/sergeev/basic/B.java",
              //  "examples/src/ru/spbau/sergeev/basic/C.java"});
    }
}
