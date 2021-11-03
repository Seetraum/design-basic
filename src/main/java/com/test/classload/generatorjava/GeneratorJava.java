package com.test.classload.generatorjava;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import javax.lang.model.element.Modifier;

public class GeneratorJava {

  public static void main(String[] args) {
    ClassName className = ClassName.get("com.test.annotationscan.scan","ExaminerProvider");
    ClassName annotation = ClassName.get("org.springframework.stereotype","Controller");
    ClassName annotation2 = ClassName.get("org.springframework.stereotype","Controller");
    CodeBlock typeBlock = CodeBlock.builder()
        .add("$L", "B") // 这里不能调用addStatement,因为addStatement会添加分号，与语法不符
        .build();
    TypeSpec.Builder testGeneratorBuild = TypeSpec.classBuilder("TestGenerator")
                                          .addModifiers(Modifier.PUBLIC)
                                          .addAnnotation(annotation)
                                          .addAnnotation(AnnotationSpec.builder(annotation2)
                                              .addMember("accept", "$S", "application/json; charset=utf-8")
                                              .addMember("userAgent", "$S", "Square Cash")
                                              .addMember("type",typeBlock)
                                              .build())
                                          .superclass(className);
    ClassName override = ClassName.get("java.lang", "Override");

    ClassName string = ClassName.get("java.lang", "String");

    ParameterSpec conditions = ParameterSpec.builder(string, "conditions").build();
    MethodSpec examinerChain = MethodSpec.methodBuilder("examinerChain")
        .addAnnotation(override)
        .addModifiers(Modifier.PUBLIC)
        .addParameter(conditions)
        .returns(Map.class)
        .addStatement("Map<String,Event> eventMap = new HashMap<>()")
        .addStatement("return eventMap")
        //.addStatement("super.onCreate(savedInstanceState)")
        //.addStatement("setContentView(R.layout.activity_main)")
        .build();

    TypeSpec TestGenerator = testGeneratorBuild.addMethod(examinerChain)
        .build();

    JavaFile file = JavaFile.builder("com.test.classload", TestGenerator).build();
    try {
      //file.writeTo(Path.of("D:\\project\\design-basic\\src\\main\\java"), StandardCharsets.UTF_8);
      file.writeTo(System.out);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
