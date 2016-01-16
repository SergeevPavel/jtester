package ru.spbau.sergeev.jtester;

import ru.spbau.sergeev.jtester.annotations.TestClass;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

/**
 * @author pavel
 */

@SupportedAnnotationTypes("ru.spbau.sergeev.jtester.annotations.TestClass")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    private Map<String, List<String>> testUnits = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
    }

    private void registerTestUnit(TypeElement element) {
        final TestClass testClassAnnotation = element.getAnnotation(TestClass.class);
        String testUnitClassName = testClassAnnotation.testUnit();
        if (!testUnits.containsKey(testUnitClassName)) {
            testUnits.put(testUnitClassName, new ArrayList<>());
        }
        testUnits.get(testUnitClassName).add(element.getQualifiedName().toString());
    }

    private void generateTestUnitClasses() {
        for (Map.Entry<String, List<String>> entry : testUnits.entrySet()) {
            TestUnitClassModel model = new TestUnitClassModel(entry.getKey(), entry.getValue());
            try {
                model.generateTestUnitClass(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "TestUnitClass creation error.");
            }
        }
        testUnits.clear();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(TestClass.class)) {
            if (!element.getKind().equals(ElementKind.CLASS)) {
                messager.printMessage(Diagnostic.Kind.NOTE,
                        String.format("Wrong TestClass annotation usage on %s. Annotation ignored.",
                                element.getSimpleName()));
                continue;
            }

            final TypeElement clazz = (TypeElement) element;
            registerTestUnit(clazz);
            final PackageElement packageOfClazz = elementUtils.getPackageOf(clazz);
            final TestClassModel model = new TestClassModel(clazz, packageOfClazz);
            try {
                model.generateTestClass(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "TestClass creation error.");
            }
        }
        generateTestUnitClasses();
        return true;
    }

}
