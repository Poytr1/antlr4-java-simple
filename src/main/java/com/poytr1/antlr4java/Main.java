package com.poytr1.antlr4java;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;

/**
 * @author Poytr1
 */
class Main {
    
    static Map<String, List<String>> map = new HashMap<>();
    public static void main(String[] args) {
        System.out.println("Antlr4 Java Simple");

        map.put("classes", new ArrayList<>());
        map.put("methods", new ArrayList<>());
        map.put("fields", new ArrayList<>());
        map.put("constructors", new ArrayList<>());

        class ExtendedJava8Visitor extends Java8BaseVisitor<Void> {

            @Override
            public Void visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) { 
                map.get("classes").add(ctx.normalClassDeclaration().Identifier().getText());
                return visitChildren(ctx); 
            }
        
            @Override public Void visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) { 
                map.get("fields").add(ctx.getText());
                return visitChildren(ctx); 
            }
        
        
            @Override public Void visitConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx) { 
                map.get("methods").add(ctx.getText());
                return visitChildren(ctx);  
            }
        
            @Override public Void visitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
                map.get("constructors").add(ctx.getText());
                return visitChildren(ctx); 
            }
        }

        try {
          InputStream inputStream = Main.class.getResourceAsStream("/example1.txt");
          Lexer lexer = new Java8Lexer(CharStreams.fromStream(inputStream));
          TokenStream tokenStream = new CommonTokenStream(lexer);
          Java8Parser parser = new Java8Parser(tokenStream);
          ExtendedJava8Visitor extendedJava8Visitor = new ExtendedJava8Visitor();
          extendedJava8Visitor.visit(parser.compilationUnit());
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.print(map.toString());

   }

   
}