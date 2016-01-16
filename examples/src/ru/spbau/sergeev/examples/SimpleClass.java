package ru.spbau.sergeev.examples;

import ru.spbau.sergeev.jtester.annotations.Test;
import ru.spbau.sergeev.jtester.annotations.TestClass;
import ru.spbau.sergeev.jtester.TestLevel;
import ru.spbau.sergeev.jtester.annotations.TestSpeed;

/**
 * @author pavel
 */

@TestClass(testUnit = "simple")
public class SimpleClass {
    private static int staticField;
    private boolean ordinalField;

    @TestSpeed
    @Test(testName = "simpleTestMethod")
    public static void simpleMethod() throws InterruptedException {
        Thread.sleep(1234);
    }

    @TestSpeed
    @Test(testName = "anotherMethod", testLevel = TestLevel.CRITICAL)
    public static void anotherMethod() throws InterruptedException {
        Thread.sleep(789);

    }

    @Test(testName = "LowTest", testLevel = TestLevel.LOW)
    public static void lowTest() {

    }

    @Test(testName = "MediumTest", testLevel = TestLevel.MEDIUM)
    public static void mediumTest() {

    }

    @Test(testName = "CriticalTest", testLevel = TestLevel.CRITICAL)
    public static void criticalTest() {

    }
}
