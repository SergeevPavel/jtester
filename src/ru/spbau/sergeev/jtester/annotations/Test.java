package ru.spbau.sergeev.jtester.annotations;

import ru.spbau.sergeev.jtester.TestLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author pavel
 */

@Target(value = ElementType.METHOD)
public @interface Test {
    String testName();
    TestLevel testLevel() default TestLevel.LOW;
}
