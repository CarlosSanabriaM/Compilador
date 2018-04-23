
import java.io.FileReader;
import java.io.IOException;
import scanner.Scanner;
import parser.Parser;

public class Main {

	public static void main(String args[]) throws IOException {
	    if (args.length<1) {
	        System.err.println("Pass me the name of the input file.");
	        return;
	    }
	        
		FileReader fr=null;
		try {
			fr=new FileReader(args[0]);
		} catch(IOException io) {
			System.err.println("The file "+args[0]+" could not be opened.");
			return;
		}
		
		Scanner scanner = new Scanner(fr);
		Parser parser = new Parser(scanner);
		int token;
		while ((token=scanner.yylex())!=0) {
		    System.out.println("Line: "+scanner.getLine()+
		            ", column: "+scanner.getColumn()+
		            ", token: "+token+
		            ", semantic value: "+scanner.getYylval()+".");
		}
	}
}