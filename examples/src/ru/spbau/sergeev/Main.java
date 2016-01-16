package ru.spbau.sergeev;

import ru.spbau.sergeev.jtester.TestLevel;

/**
 * @author pavel
 */
public class Main {
    public static void main(String[] args) {
       // ru.spbau.sergeev.examples.SimpleClassTest.run(TestLevel.CRITICAL);
       ru.spbau.sergeev.basic.ATest.run(TestLevel.LOW);
       ru.spbau.sergeev.basic.BTest.run(TestLevel.LOW);
       ru.spbau.sergeev.basic.CTest.run(TestLevel.LOW);
    }
}
