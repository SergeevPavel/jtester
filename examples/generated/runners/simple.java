package runners;
import ru.spbau.sergeev.jtester.TestLevel;
public class simple {
public static void main(String[] args) {
final TestLevel level = TestLevel.valueOf(args[0]);
ru.spbau.sergeev.examples.SimpleClassTest.run(level);
}
}
