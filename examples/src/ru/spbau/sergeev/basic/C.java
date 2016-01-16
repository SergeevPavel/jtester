package ru.spbau.sergeev.basic;

import ru.spbau.sergeev.jtester.annotations.Test;
import ru.spbau.sergeev.jtester.annotations.TestClass;

/**
 * @author pavel
 */

@TestClass(testUnit = "OtherTests")
public class C {
    @Test(testName = "smalltest")
    public static void foo() {
    }
}
