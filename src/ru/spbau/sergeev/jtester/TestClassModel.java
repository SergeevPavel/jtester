package ru.spbau.sergeev.jtester;

import ru.spbau.sergeev.jtester.annotations.Test;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

/**
 * @author pavel
 */
public class TestClassModel {
    static final String NAME_SUFFIX = "Test";

    final TypeElement clazz;
    final String testClassName;
    final String qualifiedTestClassName;
    final String packageName;

    public TestClassModel(TypeElement clazz, PackageElement packageOfClazz) {
        this.clazz = clazz;
        this.testClassName = clazz.getSimpleName() + NAME_SUFFIX;
        this.qualifiedTestClassName = clazz.getQualifiedName() + NAME_SUFFIX;
        this.packageName = packageOfClazz.getQualifiedName().toString();
    }

    public void generateTestClass(Filer filer) throws IOException {
        JavaFileObject jfo = filer.createSourceFile(qualifiedTestClassName);
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
        return String.format("package %s;\n", packageName);
    }

    private String generateImportsSource() {
        StringBuilder builder = new StringBuilder();

        builder.append("import ru.spbau.sergeev.jtester.TestLevel;\n");

        return builder.toString();
    }

    private String generateClassSource() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("public class %s {\n", testClassName));
        builder.append(generateRunMethodSource());
        builder.append("}\n");

        return builder.toString();
    }

    private String generateRunMethodSource() {
        StringBuilder builder = new StringBuilder();

        builder.append("public static void run(TestLevel level) {\n");
        builder.append(String.format("System.out.println(\"%s\");\n", clazz.getSimpleName()));

        for (Element method : clazz.getEnclosedElements()) {
            if (!method.getKind().equals(ElementKind.METHOD)) {
                continue;
            }

            final Test testAnnotation = method.getAnnotation(Test.class);
            if (testAnnotation != null) {
                String testSource = generateOneTestSource(testAnnotation.testName(), testAnnotation.testLevel(),
                        method.getSimpleName().toString());
                builder.append(testSource);
            }
        }

        builder.append("}\n");

        return builder.toString();
    }

    private String generateOneTestSource(String testName, TestLevel testLevel, String methodName) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("if (TestLevel.%s.compareTo(level) <= 0) {", testLevel));
        builder.append("try {\n");
        builder.append(String.format("%s.%s();\n", clazz.getQualifiedName(), methodName));
        builder.append(String.format("System.out.println(\"%s passed\");\n", testName));
        builder.append("} catch (Exception e) {\n");
        builder.append(String.format("System.out.println(\"%s \" + e.getMessage() + \"\");\n", testName));
        builder.append("}\n");
        builder.append("}\n");

        return builder.toString();
    }
}
