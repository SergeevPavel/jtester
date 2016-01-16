package ru.spbau.sergeev.jtester;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import ru.spbau.sergeev.jtester.annotations.TestSpeed;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author pavel
 */
@SupportedAnnotationTypes({"ru.spbau.sergeev.jtester.annotations.TestSpeed"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestSpeedProcessor extends AbstractProcessor {
    private JavacProcessingEnvironment javacProcessingEnvironment;
    private TreeMaker maker;
    private JavacElements utils;
    private String START_TIME_VARIABLE_NAME = "startTimeMAGIC";

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.javacProcessingEnvironment = (JavacProcessingEnvironment) processingEnvironment;
        this.maker = TreeMaker.instance(javacProcessingEnvironment.getContext());
        this.utils = javacProcessingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element rootElement : roundEnv.getRootElements()) {
            if (!(rootElement instanceof TypeElement)) {
                return false;
            }
            TypeElement clz = (TypeElement) rootElement;
            for (Element method : clz.getEnclosedElements()) {
                TestSpeed annotation = method.getAnnotation(TestSpeed.class);
                if (!(method instanceof MethodSymbol) || annotation == null) {
                    continue;
                }
                final String methodName = method.getSimpleName().toString();
                final String className = clz.getQualifiedName().toString();
                final String outputPrefix = String.format("%s.%s", className, methodName);
                injectTimeMeasurementCode(method, outputPrefix);
            }
        }
        return true;
    }

    private void injectTimeMeasurementCode(Element method, String outputPrefix) {
        JCTree node = utils.getTree(method);
        if (!(node instanceof JCMethodDecl)) return;
        final JCMethodDecl methodDecl = (JCMethodDecl) node;
        List<JCStatement> statements = methodDecl.body.stats;
        List<JCStatement> newStatements = List.nil();
        JCVariableDecl varDecl = generateStoreStartTime();
        newStatements = newStatements.append(varDecl);
        newStatements = newStatements.append(generateOldMethodBodyWrapper(statements, varDecl, outputPrefix));
        methodDecl.body.stats = newStatements;
    }

    private JCVariableDecl generateStoreStartTime() {
        JCExpression currentTimeMethodCall = generateCurrentTimeMethodCall();
        return maker.VarDef(maker.Modifiers(Flags.FINAL), utils.getName(START_TIME_VARIABLE_NAME),
                maker.TypeIdent(TypeTag.LONG), currentTimeMethodCall);
    }

    private JCExpression generateCurrentTimeMethodCall() {
        JCExpression system = maker.Ident(utils.getName("System"));
        JCExpression currentTimeMethod = maker.Select(system, utils.getName("currentTimeMillis"));
        return maker.Apply(List.<JCExpression>nil(), currentTimeMethod,
                List.<JCExpression>nil());
    }

    private JCStatement generateOldMethodBodyWrapper(List<JCStatement> oldStatements, JCVariableDecl var, String outputPrefix) {
        List<JCStatement> tryBlock = List.nil();
        for (JCStatement statement : oldStatements) {
            tryBlock = tryBlock.append(statement);
        }
        JCBlock finallyBlock = generateShowTimeBlock(var, outputPrefix);
        return maker.Try(maker.Block(0, tryBlock), List.<JCCatch>nil(), finallyBlock);
    }

    private JCBlock generateShowTimeBlock(JCVariableDecl var, String outputPrefix) {
        JCExpression printlnExpression = maker.Ident(utils.getName("System"));
        printlnExpression = maker.Select(printlnExpression, utils.getName("out"));
        printlnExpression = maker.Select(printlnExpression, utils.getName("println"));

        JCExpression currentTimeMethodCall = generateCurrentTimeMethodCall();
        JCExpression elapsedTime = maker.Binary(Tag.MINUS, currentTimeMethodCall, maker.Ident(var.name));

        JCExpression formatExpression = maker.Ident(utils.getName("String"));
        formatExpression = maker.Select(formatExpression, utils.getName("format"));


        String formatLine = outputPrefix + " %s";
        List<JCExpression> formatArgs = List.nil();
        formatArgs = formatArgs.append(maker.Literal(formatLine));
        formatArgs = formatArgs.append(elapsedTime);
        JCExpression format = maker.Apply(List.<JCExpression>nil(), formatExpression, formatArgs);

        List<JCExpression> printlnArgs = List.nil();
        printlnArgs = printlnArgs.append(format);
        JCExpression println = maker.Apply(List.<JCExpression>nil(), printlnExpression, printlnArgs);

        List<JCStatement> statements = List.nil();
        statements = statements.append(maker.Exec(println));

        return maker.Block(0, statements);
    }
}
