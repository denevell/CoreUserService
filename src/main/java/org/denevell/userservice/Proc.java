package org.denevell.userservice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

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

    FileObject isr = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", "output.vm");
    /*
    Reader is = isr.openReader(true);
    BufferedReader brr = new BufferedReader(is);
    String s = "";
    processingEnv.getMessager().printMessage(Kind.NOTE, "Printing file: "); 
    while((s = brr.readLine())!=null) { 
      processingEnv.getMessager().printMessage(Kind.NOTE, s); 
    }
    Velocity.init("velocity.properties");
    VelocityEngine ve = new VelocityEngine();
    ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
    ve.init(); 
    VelocityContext con = new VelocityContext();
    Velocity.evaluate(con, bw, "", "Sup"); 
    //System.out.println(bw.toString());
    */

    //Properties props = new Properties();
    //URL url = getClass().getClassLoader().getResource("/velocity.properties");
    //phrops.load(url.openStream());

    VelocityEngine ve = new VelocityEngine();
    ve.init();

    VelocityContext vc = new VelocityContext();

    Template vt = ve.getTemplate("output.vm"); 
    StringWriter stringWriter = new StringWriter();
    vt.merge(vc, isr.openWriter()); 

    processingEnv.getMessager().printMessage(Kind.NOTE, stringWriter.toString()); 

      bw.flush(); 
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
      throw new RuntimeException(e1);
    }
  }
  
  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

}