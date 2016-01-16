package ru.spbau.sergeev.basic;

import ru.spbau.sergeev.jtester.TestLevel;
import ru.spbau.sergeev.jtester.annotations.Test;
import ru.spbau.sergeev.jtester.annotations.TestClass;
import ru.spbau.sergeev.jtester.annotations.TestSpeed;

/**
 * @author pavel
 */
@TestClass(testUnit = "MainTests")
public class A {
    @TestSpeed
    @Test(testName = "test1", testLevel = TestLevel.MEDIUM)
    public static void foo() {
        throw new RuntimeException("test failed");
    }

    @TestSpeed
    public static void bar() {
    }
}
