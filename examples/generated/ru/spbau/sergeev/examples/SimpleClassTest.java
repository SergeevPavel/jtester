package ru.spbau.sergeev.examples;
import ru.spbau.sergeev.jtester.TestLevel;
public class SimpleClassTest {
public static void run(TestLevel level) {
System.out.println("SimpleClass");
if (TestLevel.LOW.compareTo(level) <= 0) {try {
ru.spbau.sergeev.examples.SimpleClass.simpleMethod();
System.out.println("simpleTestMethod passed");
} catch (Exception e) {
System.out.println("simpleTestMethod " + e.getMessage() + "");
}
}
if (TestLevel.CRITICAL.compareTo(level) <= 0) {try {
ru.spbau.sergeev.examples.SimpleClass.anotherMethod();
System.out.println("anotherMethod passed");
} catch (Exception e) {
System.out.println("anotherMethod " + e.getMessage() + "");
}
}
if (TestLevel.LOW.compareTo(level) <= 0) {try {
ru.spbau.sergeev.examples.SimpleClass.lowTest();
System.out.println("LowTest passed");
} catch (Exception e) {
System.out.println("LowTest " + e.getMessage() + "");
}
}
if (TestLevel.MEDIUM.compareTo(level) <= 0) {try {
ru.spbau.sergeev.examples.SimpleClass.mediumTest();
System.out.println("MediumTest passed");
} catch (Exception e) {
System.out.println("MediumTest " + e.getMessage() + "");
}
}
if (TestLevel.CRITICAL.compareTo(level) <= 0) {try {
ru.spbau.sergeev.examples.SimpleClass.criticalTest();
System.out.println("CriticalTest passed");
} catch (Exception e) {
System.out.println("CriticalTest " + e.getMessage() + "");
}
}
}
}
