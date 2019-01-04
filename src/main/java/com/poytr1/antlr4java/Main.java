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

import org.json.JSONObject;
/**
 * @author Poytr1
 */
class Main {
    
    static Map<String, List<String>> map = new HashMap<>();
    public static void main(String[] args) {
        System.out.println("Antlr4 Java Simple");

        ArrayList<Map<String, Object>> elements = new ArrayList<>();
        class ExtendedJava8Visitor extends Java8BaseVisitor<Void> {

            @Override
            public Void visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) { 
                String name = ctx.normalClassDeclaration().Identifier().getText();
                int line = ctx.normalClassDeclaration().Identifier().getSymbol().getLine();
                int start = ctx.normalClassDeclaration().Identifier().getSymbol().getCharPositionInLine();  
                int end = ctx.normalClassDeclaration().Identifier().getSymbol().getCharPositionInLine() + 
                    ctx.normalClassDeclaration().Identifier().getSymbol().getStopIndex() - ctx.normalClassDeclaration().Identifier().getSymbol().getStartIndex();  
                addElement(name, line, start, end, "class", elements);
                return visitChildren(ctx); 
            }
        
            @Override public Void visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) { 
                String name = ctx.getText();
                int line = ctx.start.getLine();
                int start = ctx.start.getCharPositionInLine();
                int end = ctx.stop.getCharPositionInLine();
                addElement(name, line, start, end, "field", elements);
                return visitChildren(ctx); 
            }
        
        
            @Override public Void visitConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx) { 
                String name = ctx.getText();
                int line = ctx.start.getLine();
                int start = ctx.start.getCharPositionInLine();
                int end = ctx.stop.getCharPositionInLine();
                addElement(name, line, start, end, "constructor", elements);
                return visitChildren(ctx);  
            }
        
            @Override public Void visitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
                String name = ctx.getText();
                int line = ctx.start.getLine();
                int start = ctx.start.getCharPositionInLine();
                int end = ctx.stop.getCharPositionInLine();
                addElement(name, line, start, end, "method", elements);
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
    
        System.out.print(elements.toString());

   }

   private static void addElement(String name, int line, int start, int end, String type, List<Map<String, Object>> elements) {
    JSONObject element = new JSONObject();
    JSONObject startPos = new JSONObject();
    JSONObject endPos = new JSONObject();
    element.put("name", name);
    startPos.put("line", line);
    startPos.put("column", start);
    endPos.put("line", line);
    endPos.put("column", end);
    element.put("start", startPos);
    element.put("end", endPos);
    element.put("type", type);
    elements.add(element.toMap());
    }

}
