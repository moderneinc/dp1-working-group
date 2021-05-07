package io.moderne.dp1;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.tree.J;

import static java.util.Collections.singletonList;

public class AddWebclientFactory extends Recipe {
    @Override
    public String getName() {
        return "Add webclient factory";
    }

    @Override
    public String getDisplayName() {
        return "Add webclient factory for oauth2.";
    }

    @Override
    protected TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<>() {
            @Override
            public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method, ExecutionContext ctx) {
                J.MethodDeclaration m = super.visitMethodDeclaration(method, ctx);
                if (m.isConstructor() && m.getParameters().iterator().next() instanceof J.Empty) {
                    m = template("(WebclientFactory webclientFactory)")
                            .javaParser(JavaParser.fromJavaVersion()
                                    .dependsOn(singletonList(Parser.Input.fromString("package dp1; public class WebclientFactory {}")))
                                    .build())
                            .imports("dp1.WebclientFactory")
                            .build()
                            .withTemplate(m, m.getCoordinates().replaceParameters());
                    maybeAddImport("dp1.WebclientFactory");
                }
                return m;
            }
        };
    }
}
