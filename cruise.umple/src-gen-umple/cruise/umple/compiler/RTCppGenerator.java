/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE ${last.version} modeling language!*/

package cruise.umple.compiler;
import java.util.*;
import java.io.*;
import cruise.umple.util.*;
import cruise.umple.compiler.exceptions.*;
import cruise.umple.cpp.generator.UmpleCppPoliciesProcessor;
import cruise.umple.cpp.core.ContentsDescriptor;

/**
 * @umplesource Generator.ump 249
 * @umplesource Generator_CodeRTCpp.ump 11
 */
// line 249 "../../../../src/Generator.ump"
// line 11 "../../../../src/Generator_CodeRTCpp.ump"
public class RTCppGenerator implements CodeGenerator
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RTCppGenerator Attributes
  private UmpleModel model;
  private String output;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RTCppGenerator()
  {
    model = null;
    output = "";
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setModel(UmpleModel aModel)
  {
    boolean wasSet = false;
    model = aModel;
    wasSet = true;
    return wasSet;
  }

  public boolean setOutput(String aOutput)
  {
    boolean wasSet = false;
    output = aOutput;
    wasSet = true;
    return wasSet;
  }

  /**
   * Contains various aspects from an Umple file (.ump), such as classes, attributes, associations and methods.  Generated output is based
   * off of what's contained in here.
   */
  public UmpleModel getModel()
  {
    return model;
  }

  public String getOutput()
  {
    return output;
  }

  public void delete()
  {}

  @umplesourcefile(line={17},file={"Generator_CodeRTCpp.ump"},javaline={80},length={21})
   public void generate(){
    try {
  		final StringBuilder code = new StringBuilder();
  		new UmpleCppPoliciesProcessor() {
			@Override
			public void handleGeneratedContents(List<ContentsDescriptor> contentsDescriptor) {
				try {
					for(ContentsDescriptor descriptor:contentsDescriptor){
						generateContents(model, code, descriptor.getFileName(), descriptor.getContents(), descriptor.getPath());
					}
				} catch (IOException e) {
					throw new UmpleCompilerException("There was a problem with generating classes. " + e, e);
				}
				
			}
		}.generateRootElement(model);
		model.setCode(code.toString());
	} catch (Exception e) {
		throw new UmpleCompilerException("There was a problem with generating classes. " + e, e);
	}
  }


  /**
   * Allows independent code generation tools
   * Allows independent code generation tools
   * Different generators will do different things regarding where the files are put, etc.
   * Different generators will do different things regarding where the files are put, etc.
   */
  @umplesourcefile(line={23},file={"Generator.ump"},javaline={102},length={2})
  @Override
  public void prepare(){
          return ;
  }


  /**
   * Allows independent code generation tools
   * Allows independent code generation tools
   * Different generators will do different things regarding where the files are put, etc.
   * Different generators will do different things regarding where the files are put, etc.
   */
  @umplesourcefile(line={24},file={"Generator.ump"},javaline={115},length={2})
  @Override
  public void postpare(){
          return ;
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "output" + ":" + getOutput()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "model" + "=" + (getModel() != null ? !getModel().equals(this)  ? getModel().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  //  @umplesourcefile(line={37},file={"Generator_CodeRTCpp.ump"},javaline={140},length={26})
  @umplesourcefile(line={38},file={"Generator_CodeRTCpp.ump"},javaline={141},length={25})
  protected void generateContents (UmpleModel model ,StringBuilder model_code, String filename, String content, String owingFolder) throws IOException 
  {
    String path = model.getUmpleFile().getPath() + "/";
		 owingFolder= owingFolder.replace(".", "::").replace("::", "/");
		 String qualifiedPath = path+ owingFolder;
		 File folder = new File(qualifiedPath);
		 if (!folder.exists()) {
			 folder.mkdirs();
		 }
		 
		 File folderFile = new File(qualifiedPath+ "/"+ filename); 	
		 
		 if (!folderFile.exists()) {
		 	folderFile.createNewFile();
		 }
		 
		 model.getGeneratedCode().put(filename,content);
		 
		 model_code.append(content);
		 model_code.append("\n\n");
		 
		 BufferedWriter output = new BufferedWriter(new FileWriter(folderFile));
		 output.write(content);
		 output.close();
  }

  
}