package ru.spbau.sergeev.jtester.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author pavel
 */

@Target(value = ElementType.TYPE)
public @interface TestClass {
    String testUnit();
}
