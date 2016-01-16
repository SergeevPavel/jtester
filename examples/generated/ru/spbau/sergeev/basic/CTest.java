package ru.spbau.sergeev.basic;
import ru.spbau.sergeev.jtester.TestLevel;
public class CTest {
public static void run(TestLevel level) {
System.out.println("C");
if (TestLevel.LOW.compareTo(level) <= 0) {try {
ru.spbau.sergeev.basic.C.foo();
System.out.println("smalltest passed");
} catch (Exception e) {
System.out.println("smalltest " + e.getMessage() + "");
}
}
}
}
