package org.denevell.userservice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.EnumSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

import com.squareup.javawriter.JavaWriter;

@UserService
@SupportedAnnotationTypes({"org.denevell.userservice.UserService"})
public class Proc extends AbstractProcessor {
  
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement te : annotations) {
      if(roundEnv.getElementsAnnotatedWith(te).size()>0) {
        createSourceFile();
        return true;
      }
    }
    return false;
  }

  private void createSourceFile() {
    try {
      FileObject fileOb = processingEnv.getFiler().createResource(
          StandardLocation.SOURCE_OUTPUT, 
          "", 
          "sup.yeah");
      Writer resOut = fileOb.openWriter();
      BufferedWriter bw = new BufferedWriter(resOut);
      bw.write("ZZZZ");
      bw.close();
      resOut.close();

      JavaFileObject jfo = processingEnv.getFiler().createSourceFile("com.something.Yeah");
      Writer out = jfo.openWriter();
      JavaWriter writer = new JavaWriter(out);
      writer.emitPackage("com.something");
      writer.beginType("Yeah", "class", EnumSet.of(Modifier.PUBLIC)).emitEmptyLine();
      writer.emitSingleLineComment("So, then...");
      writer.endType();
      writer.close();
      out.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }
  
  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

}