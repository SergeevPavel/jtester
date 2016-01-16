package ru.spbau.sergeev.basic;

import ru.spbau.sergeev.jtester.annotations.Test;
import ru.spbau.sergeev.jtester.annotations.TestClass;
import ru.spbau.sergeev.jtester.TestLevel;
import ru.spbau.sergeev.jtester.annotations.TestSpeed;

/**
 * @author pavel
 */
@TestClass(testUnit = "MainTests")
public class B {
	@Test(testName = "test1")
	public static void foo() {
	}
	@TestSpeed
	@Test(testName = "test3", testLevel = TestLevel.CRITICAL)
	public static void bar() {
	}
}
