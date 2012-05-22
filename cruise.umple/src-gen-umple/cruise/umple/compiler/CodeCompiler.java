/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.15.0.1751 modeling language!*/

package cruise.umple.compiler;
import java.util.*;
import java.io.*;
import cruise.umple.compiler.*;
import cruise.umple.compiler.exceptions.*;
import cruise.umple.util.StringFormatter;

public class CodeCompiler
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CodeCompiler()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  public static String console;

	public static boolean compile(UmpleModel model, String entryClass) {
		boolean error_flag = true;
		for (UmpleElement currentElement : model.getUmpleElements())
		{
			if ("external".equals(currentElement.getModifier()))
			{
				continue;
			}
			if (entryClass.equals("-") || entryClass.equals(currentElement.getName())) {
				error_flag = error_flag && compileJava(currentElement, model);
			}
		}
		return error_flag;
	}

	private static boolean compileJava(UmpleElement aClass, UmpleModel model) {
		String path="";
		for (GenerateTarget gt : model.getGenerates()) {
			if (gt.getLanguage().equals("Java")) {
				path = StringFormatter.addPathOrAbsolute( 
						model.getUmpleFile().getPath() +"/"+ gt.getPath(), "") + 
					aClass.getPackageName().replace(".", File.separator);
			}
		}
		String filename = path + File.separator + aClass.getName() + ".java";
		//System.out.println("filename: "+filename);
		boolean error_exist = true;
		try {
			Process p = Runtime.getRuntime().exec("javac "+filename);
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getErrorStream())); 
			String line=reader.readLine(); 
			while (line!=null) {
				get_original_line(line, reader, model, aClass);
				line = reader.readLine();
				error_exist = false;
			}
		} catch (IOException e) {
			println(e.getMessage());
		}
		return error_exist;
	}

	private static void get_original_line(String file, BufferedReader reader, UmpleModel model, UmpleElement aClass) {
		//System.out.println("file:  "+file);
		StringTokenizer st = new StringTokenizer(file, ":"); 
		if (st.countTokens()<3) {
			return;
		}
		String code_file = st.nextToken(); //NOT USED
		st.nextToken(); // Line number in java file NOT USED
		String error_type = st.nextToken(); // Error type
		String umpFile_name = aClass.getUmpFile();

		String class_name = "";
		try {
			class_name = code_file.substring(code_file.lastIndexOf('/')+1, code_file.lastIndexOf('.'));
		} catch (Exception e) {
			// Unexpected error message
			println("Unexpected error message: "+ file);
			return;
		}

		if (!class_name.equals(aClass.getName())) {
			for (UmpleElement currentElement : model.getUmpleElements())
			{
				if (currentElement.getName().equals(class_name)) {
					get_original_line(file, reader, model, currentElement);
					return;
				}
			}
		}

		int line_num;

		// Here to find the actually java code to match with
		try {
			// Find the ^ symbol in error message to know where the current error message end
			String error_msg="";
			String java_code = reader.readLine();
			String java_code_trim = java_code.trim();
			String pos_locator;
			while(true) {
				pos_locator = reader.readLine();
				if (pos_locator == null) {
					return;
				}
				if (pos_locator.trim().equals("^")) {
					break;
				}
				error_msg = error_msg + java_code + "\n";
				java_code = pos_locator;
			}

			BufferedReader umpFile = new BufferedReader(new FileReader(umpFile_name));
			String line;
			line_num=0;
			while(true) {
				line = umpFile.readLine();
				if (line == null) {
					break;
				}
				line_num++;
				if (line.trim().equals(java_code_trim)) {
					break;
				}
			}

			println(getSimpleFileName(umpFile_name)+":"+line_num+": Generic Java Error"+error_type);
			//println(error_msg+java_code+"\n"+pos_locator);

			umpFile.close();
		} catch (IOException e) {
		}
	}

	private static void println(String output)
	{
		console += output + "\n";
		System.out.println(output);
	}

	public static String getSimpleFileName(String fileName)
	{

		int lastIndex = fileName.lastIndexOf("/");
		if (lastIndex == -1)
		{
			return fileName;
		}
		else
		{
			return fileName.substring(lastIndex+1, fileName.length());
		}
	}
}