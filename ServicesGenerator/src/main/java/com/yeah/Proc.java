package com.yeah;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

@SupportedAnnotationTypes({"com.yeah.Proc.UserService"})
public class Proc extends AbstractProcessor {
  
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public static @interface UserService {
    String persistenceUnitName();
  }

  private String mPersistenceUnitName;
  
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement te : annotations) {
      Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(te);
      processingEnv.getMessager().printMessage(Kind.NOTE, "Found annotations: " + elementsAnnotatedWith.size());
      Element next = elementsAnnotatedWith.iterator().next();
      processingEnv.getMessager().printMessage(Kind.NOTE, "Found annotation: " + next);
      UserService sg = next.getAnnotation(UserService.class);
      mPersistenceUnitName = sg.persistenceUnitName();
      if(elementsAnnotatedWith.size()>0) {
        createSourceFiles();
        return true; 
      }
    }
    return false;
  }

  private void createSourceFiles() {
    saveResourceToProject("forgen/META-INF/mapping_servgen.xml", "META-INF/userservice_mapping.xml");
    saveResourceToProject("forgen/META-INF/services/javax.servlet.ServletContainerInitializer", "META-INF/services/javax.servlet.ServletContainerInitializer");

    saveClassToProject("org/denevell/userservice/UserServiceContainerInitializer.java.vm", "org.denevell.userservice.UserServiceContainerInitializer");
    saveClassToProject("org/denevell/userservice/JerseyApplication.java.vm", "org.denevell.userservice.JerseyApplication");
    saveClassToProject("org/denevell/userservice/ExceptionLogger.java.vm", "org.denevell.userservice.ExceptionLogger");
    saveClassToProject("org/denevell/userservice/Jrappy.java.vm", "org.denevell.userservice.Jrappy");
    saveClassToProject("org/denevell/userservice/LoggedInEntity.java.vm", "org.denevell.userservice.LoggedInEntity");
    saveClassToProject("org/denevell/userservice/LoginAuthKeysSingleton.java.vm", "org.denevell.userservice.LoginAuthKeysSingleton");
    VelocityContext vc = new VelocityContext();
    vc.put("persistenceUnitName", mPersistenceUnitName);
    saveClassToProject("org/denevell/userservice/ManifestVars.java.vm", "org.denevell.userservice.ManifestVars");
    saveClassToProject("org/denevell/userservice/PasswordSaltUtils.java.vm", "org.denevell.userservice.PasswordSaltUtils");
    saveClassToProject("org/denevell/userservice/SuccessOrError.java.vm", "org.denevell.userservice.SuccessOrError");
    saveClassToProject("org/denevell/userservice/UserEntity.java.vm", "org.denevell.userservice.UserEntity");

    saveClassToProject("org/denevell/userservice/serv/PasswordChangeRequest.java.vm", "org.denevell.userservice.serv.PasswordChangeRequest");
    saveClassToProject("org/denevell/userservice/serv/UsersListRequest.java.vm", "org.denevell.userservice.serv.UsersListRequest");
    saveClassToProject("org/denevell/userservice/serv/LoginRequest.java.vm", "org.denevell.userservice.serv.LoginRequest");
    saveClassToProject("org/denevell/userservice/serv/LogoutRequest.java.vm", "org.denevell.userservice.serv.LogoutRequest");
    saveClassToProject("org/denevell/userservice/serv/PasswordResetRequest.java.vm", "org.denevell.userservice.serv.PasswordResetRequest");
    saveClassToProject("org/denevell/userservice/serv/RegisterRequest.java.vm", "org.denevell.userservice.serv.RegisterRequest");
    saveClassToProject("org/denevell/userservice/serv/UsersAdminToggleRequest.java.vm", "org.denevell.userservice.serv.UsersAdminToggleRequest");
    saveClassToProject("org/denevell/userservice/serv/UserRequest.java.vm", "org.denevell.userservice.serv.UserRequest");

    saveClassToProject("org/denevell/userservice/model/AddLoggedInUserToPermStoreModel.java.vm", "org.denevell.userservice.model.AddLoggedInUserToPermStoreModel");
    saveClassToProject("org/denevell/userservice/model/AddModel.java.vm", "org.denevell.userservice.model.AddModel");
    saveClassToProject("org/denevell/userservice/model/AdminToggleModel.java.vm", "org.denevell.userservice.model.AdminToggleModel");
    saveClassToProject("org/denevell/userservice/model/LoginModel.java.vm", "org.denevell.userservice.model.LoginModel");
    saveClassToProject("org/denevell/userservice/model/LogoutModel.java.vm", "org.denevell.userservice.model.LogoutModel");
    saveClassToProject("org/denevell/userservice/model/PasswordChangeModel.java.vm", "org.denevell.userservice.model.PasswordChangeModel");
    saveClassToProject("org/denevell/userservice/model/PasswordResetDeleteModel.java.vm", "org.denevell.userservice.model.PasswordResetDeleteModel");
    saveClassToProject("org/denevell/userservice/model/PasswordResetRequestModel.java.vm", "org.denevell.userservice.model.PasswordResetRequestModel");
    saveClassToProject("org/denevell/userservice/model/RemoveLoggedInUserFromPermStoreModel.java.vm", "org.denevell.userservice.model.RemoveLoggedInUserFromPermStoreModel");
    saveClassToProject("org/denevell/userservice/model/UserLoggedInModel.java.vm", "org.denevell.userservice.model.UserLoggedInModel");
    saveClassToProject("org/denevell/userservice/model/UserLoggedInUserFromPermStoreModel.java.vm", "org.denevell.userservice.model.UserLoggedInUserFromPermStoreModel");
    saveClassToProject("org/denevell/userservice/model/UsersModel.java.vm", "org.denevell.userservice.model.UsersModel");
  }

  private void saveResourceToProject(String input, String output) {
    saveResourceToProject(input, output, new VelocityContext());
  }

  private void saveResourceToProject(String input, String output, VelocityContext context) {
      try {
    String mapping = getResource(input);
    FileObject fileOb = getGen(output);
    Writer openWriter = fileOb.openWriter();
    Velocity.evaluate(context, openWriter, "", mapping);
    openWriter.flush();
    openWriter.close();
      } catch (Exception e) {
        processingEnv.getMessager().printMessage(Kind.NOTE, e.toString());
      }
  }

  private void saveClassToProject(String input, String output) {
    saveClassToProject(input, output, new VelocityContext());
  }

  private void saveClassToProject(String input, String output, VelocityContext context) {
    try {
      String mapping = getResource(input);
      FileObject fileOb = getClassGen(output);
      Writer openWriter = fileOb.openWriter();
      Velocity.evaluate(context, openWriter, "", mapping);
      openWriter.flush();
      openWriter.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private FileObject getGen(String file) throws IOException {
    FileObject fileOb = processingEnv.getFiler().createResource(
      StandardLocation.SOURCE_OUTPUT, 
      "", 
      file);
    return fileOb;
  }

  private FileObject getClassGen(String file) throws Exception {
    FileObject fileOb = processingEnv.getFiler().createSourceFile(file);
    return fileOb;
  }

  private String getResource(String res) {
    InputStream mapping = this.getClass().getClassLoader().getResourceAsStream(res);
    Scanner in = new Scanner(new BufferedInputStream(mapping));
    String s, str="";
    while(in.hasNext() && (s=in.nextLine())!=null) str+=s+"\n";
    in.close();
    return str;
  }
  
  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

}
    /*
      
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

      /*
    VelocityEngine ve = new VelocityEngine();
    //ve.setProperty("resource.loader", "classpath");
    //ve.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    ve.init();
 
    VelocityContext vc = new VelocityContext();
    Template vt = ve.getTemplate("/output.vm"); 
    vt.merge(vc, bw); 

    processingEnv.getMessager().printMessage(Kind.NOTE, "Finished?"); 

      bw.flush(); 
      bw.close();
      resOut.close();

      /*
*/