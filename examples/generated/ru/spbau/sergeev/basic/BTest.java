package ru.spbau.sergeev.basic;
import ru.spbau.sergeev.jtester.TestLevel;
public class BTest {
public static void run(TestLevel level) {
System.out.println("B");
if (TestLevel.LOW.compareTo(level) <= 0) {try {
ru.spbau.sergeev.basic.B.foo();
System.out.println("test1 passed");
} catch (Exception e) {
System.out.println("test1 " + e.getMessage() + "");
}
}
if (TestLevel.CRITICAL.compareTo(level) <= 0) {try {
ru.spbau.sergeev.basic.B.bar();
System.out.println("test3 passed");
} catch (Exception e) {
System.out.println("test3 " + e.getMessage() + "");
}
}
}
}
