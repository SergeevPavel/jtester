package ru.spbau.sergeev.basic;
import ru.spbau.sergeev.jtester.TestLevel;
public class ATest {
public static void run(TestLevel level) {
System.out.println("A");
if (TestLevel.MEDIUM.compareTo(level) <= 0) {try {
ru.spbau.sergeev.basic.A.foo();
System.out.println("test1 passed");
} catch (Exception e) {
System.out.println("test1 " + e.getMessage() + "");
}
}
}
}
