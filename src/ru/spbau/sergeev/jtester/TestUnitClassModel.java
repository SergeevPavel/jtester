package ru.spbau.sergeev.jtester;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author pavel
 */
public class TestUnitClassModel {
    static final String TEST_UNIT_CLASSES_PACKAGE = "runners";

    private String testUnitClassName;
    private String qualifiedTestUnitClassName;
    private List<String> testClasses;

    public TestUnitClassModel(String testUnitClassName, List<String> testClasses) {
        this.testUnitClassName = testUnitClassName;
        this.qualifiedTestUnitClassName = String.format("%s.%s", TEST_UNIT_CLASSES_PACKAGE, testUnitClassName);
        this.testClasses = testClasses;
    }

    public void generateTestUnitClass(Filer filter) throws IOException {
        JavaFileObject jfo = filter.createSourceFile(qualifiedTestUnitClassName);
        try (Writer writer = jfo.openWriter()) {
            writer.write(generateSource());
        }
    }

    private String generateSource() {
        StringBuilder builder = new StringBuilder();

        builder.append(generatePackageSource());
        builder.append(generateImportsSource());
        builder.append(generateClassSource());

        return builder.toString();
    }

    private String generatePackageSource() {
        return String.format("package %s;\n", TEST_UNIT_CLASSES_PACKAGE);
    }

    private String generateImportsSource() {
        return "import ru.spbau.sergeev.jtester.TestLevel;\n";
    }

    private String generateClassSource() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("public class %s {\n", testUnitClassName));
        builder.append(generateMainMethodSource());
        builder.append("}\n");

        return builder.toString();
    }

    private String generateMainMethodSource() {
        StringBuilder builder = new StringBuilder();

        builder.append("public static void main(String[] args) {\n");
        builder.append("final TestLevel level = TestLevel.valueOf(args[0]);\n");
        for (String testClass : testClasses) {
            builder.append(String.format("%sTest.run(level);\n", testClass));
        }
        builder.append("}\n");

        return builder.toString();
    }
}
