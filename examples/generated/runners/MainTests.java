package runners;
import ru.spbau.sergeev.jtester.TestLevel;
public class MainTests {
public static void main(String[] args) {
final TestLevel level = TestLevel.valueOf(args[0]);
ru.spbau.sergeev.basic.BTest.run(level);
ru.spbau.sergeev.basic.ATest.run(level);
}
}
