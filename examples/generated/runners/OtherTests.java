package runners;
import ru.spbau.sergeev.jtester.TestLevel;
public class OtherTests {
public static void main(String[] args) {
final TestLevel level = TestLevel.valueOf(args[0]);
ru.spbau.sergeev.basic.CTest.run(level);
}
}
