package main;
import java.io.FileReader;
import java.io.FileWriter;
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
		FileReader fr=null;
		try {
			fr=new FileReader(args[0]);
		} catch(IOException io) {
			System.err.println("The input file "+args[0]+" could not be opened.");
			return;
		}
		
		// We create a Writer for the output file
		String outputFileName;
		if(args.length >= 2) {
			outputFileName = args[1];
		} else { // If file output is not given, by default we use "output.txt"
			outputFileName = "output.txt";
		}
		
		FileWriter fw=null;
		try {
			fw=new FileWriter(outputFileName);
		} catch(IOException io) {
			System.err.println("The output file "+outputFileName+" could not be opened.");
			fr.close(); // if error opening Writer, close Reader!
			return;
		}
		
		// * Scanner and parser creation
		Scanner lexico = new Scanner(fr); // it closes the Reader
		Parser parser = new Parser(lexico);
		
		// * Parsing
		parser.run();
		
		// * Check errors 
		if(!checkErrors()) {
			// * Visitors
			parser.getAST().accept(new IdentificationVisitor(), null);
			parser.getAST().accept(new TypeCheckingVisitor(), null);
			parser.getAST().accept(new OffsetVisitor(), null);
			parser.getAST().accept(new ExecuteCGVisitor(fw), null);
			
			// * Check errors again 
			if(!checkErrors()) {
				// * Show AST
				IntrospectorModel model=new IntrospectorModel("Program",parser.getAST());
				new IntrospectorTree("Introspector", model);
			}
		}
		
		// We close the output file
		if(fw != null)
			fw.close(); // TODO se hace aqui??
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