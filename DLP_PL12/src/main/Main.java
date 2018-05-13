package main;
import java.io.FileReader;
import java.io.IOException;

import errorHandler.EH;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import parser.Parser;
import scanner.Scanner;
import visitors.IdentificationVisitor;
import visitors.OffsetVisitor;
import visitors.TypeCheckingVisitor;
import visitors.codeGeneration.ExecuteCGVisitor;

public class Main {
	public static void main(String args[]) throws IOException {
		// At least, we need the name of the input file
		if (args.length < 1) {
	        System.err.println("Pass me the name of the input file.");
	        return;
	    }
	        
		// We create a Reader for the input file
		String inputFileName = args[0];
		FileReader fr=null;
		try {
			fr=new FileReader(inputFileName);
		} catch(IOException io) {
			System.err.println("The input file "+inputFileName+" could not be opened.");
			return;
		}
		
		// If file output is not given, by default we use "output.txt"
		String outputFileName;
		if(args.length >= 2) {
			outputFileName = args[1];
		} else {
			outputFileName = "output.txt";
		}
		
		// * Scanner and parser creation
		Scanner lexico = new Scanner(fr); // it closes the Reader
		Parser parser = new Parser(lexico);
		
		// * Parsing
		parser.run();
		
		// * Check errors 
		if(!checkErrors()) {
			// * Syntactical and Semantical analisis
			parser.getAST().accept(new IdentificationVisitor(), null);
			parser.getAST().accept(new TypeCheckingVisitor(), null);
			
			// * Check errors again 
			if(!checkErrors()) {
				// Here, the program has no errors
				
				// * Code generation
				parser.getAST().accept(new OffsetVisitor(), null);
				parser.getAST().accept(new ExecuteCGVisitor(inputFileName, outputFileName), null);
				
				// * Show AST
				IntrospectorModel model=new IntrospectorModel("Program",parser.getAST());
				new IntrospectorTree("Introspector", model);
			}
		}
		
	}
	
	/**
	 * If there are errors, prints them and return true.
	 * If not, returns false.
	 */
	private static boolean checkErrors() {
		// * Check errors 
		if(EH.getEH().hasErrors()){
			// * Show errors
			EH.getEH().showErrors(System.err);
			return true;
		} 
		else return false;
	}

}