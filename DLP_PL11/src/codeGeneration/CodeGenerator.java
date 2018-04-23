package codeGeneration;

import java.io.FileWriter;
import java.io.IOException;

import ast.definitions.VarDefinition;
import ast.types.Type;

public class CodeGenerator {

	private FileWriter out;

	public CodeGenerator(FileWriter out) {
		this.out = out;
	}
	
	/**
	 * Writes the given string to the file specified
	 * in the FileWriter.
	 */
	private void println(String str) {
		try {
			out.write(str + "\n");
		} catch (IOException e) {
			e.printStackTrace(); //TODO - ???
		}
	}
	
	/**
	 * Writes the given string to the file specified
	 * in the FileWriter, adding a "\t".
	 */
	private void printlnt(String str) {
		println("\t" + str);
	}
	
	public void directive(String str) {
		println("\n#"+ str);
	}
	
	public void sourceDirective(String inputFileName) {
		directive("source\t\""+ inputFileName + "\"\n");
	}
	
	public void lineDirective(int line) {
		directive("line " + line);
	}
	
	public void comment(String str) {
		printlnt("' * "+ str);
	}
	
	public void varDefinitionComment(VarDefinition varDefinition) {
		String type = varDefinition.getType().getClass().getSimpleName();
		String name = varDefinition.getName();
		int offset = varDefinition.offset;

		comment(type +" "+ name +" (offset "+ offset +")");
	}
	
	public void pusha(int address) {
		printlnt("pusha " + address);
	}
	
	public void pushbp() {
		printlnt("push bp"); //TODO - pusha bp??
	}
	
	public void push(char value) {
		// Convertimos el char a entero
		printlnt("pushb " + (int) value);
	}
	
	public void push(int value) {
		printlnt("pushi " + value);
	}
	
	public void push(double value) {
		printlnt("pushf " + value);
	}
	
	public void call(String functionName) {
		printlnt("call " + functionName);
	}
	
	public void callMain() {
		println("\n' Invocation to the main function");
		println("call main");
	}
	
	public void halt() {
		println("halt\n");
	}

	public void in(Type type) {
		printlnt("in" + type.suffix());
	}

	public void out(Type type) {
		printlnt("out" + type.suffix());
	}
	
	public void load(Type type) {
		printlnt("load" + type.suffix());
	}
	
	public void store(Type type) {
		printlnt("store" + type.suffix());	
	}
	
	public void b2i() {
		printlnt("b2i");	
	}
	
	public void i2b() {
		printlnt("i2b");	
	}
	
	public void i2f() {
		printlnt("i2f");	
	}
	
	public void f2i() {
		printlnt("f2i");	
	}

	/**
	 * In case its needed, this method adds the conversions
	 * to transform 'type1' to 'type2'
	 */
	public void convert(Type type1, Type type2) {
		char t1 = type1.suffix();
		char t2 = type2.suffix();
		
		switch (t1) {
		case 'b':
			if(t2 == 'i') b2i();
			else if(t2 == 'f'){
				b2i(); i2f();
			}
			break;
		case 'i':
			if(t2 == 'b') i2b();
			else if(t2 == 'f') i2f();
			break;
		case 'f':
			if(t2 == 'i') f2i();
			else if(t2 == 'b'){
				f2i(); i2b();
			}
			break;
		}
		
	}
	
	public void add(Type type) {
		printlnt("add" + type.suffix());	
	}

	public void arithmetic(Type type, String operator) {
		switch (operator) {
		case "+":
			add(type);
			break;
		case "-":
			printlnt("sub" + type.suffix());
			break;
		case "*":
			printlnt("mul" + type.suffix());
			break;
		case "/":
			printlnt("div" + type.suffix());
			break;			
		case "%":
			printlnt("mod" + type.suffix());
			break;			
		}
		
	}

	public void comparison(Type type, String operator) {
		switch (operator) {
		case ">":
			printlnt("gt" + type.suffix());
			break;
		case ">=":
			printlnt("ge" + type.suffix());
			break;
		case "<":
			printlnt("lt" + type.suffix());
			break;
		case "<=":
			printlnt("le" + type.suffix());
			break;			
		case "==":
			printlnt("eq" + type.suffix());
			break;			
		case "!=":
			printlnt("ne" + type.suffix());
			break;			
		}
		
	}

	public void logical(String operator) {
		switch (operator) {
		case "&&":
			printlnt("and");
			break;
		case "||":
			printlnt("or");
			break;			
		}
		
	}

	public void not() {
		printlnt("not");
	}
	
	public void label(String name) {
		println("\n" + name + ":");
	}

	public void enter(int bytesLocalVariables) {
		printlnt("enter " + bytesLocalVariables);
	}

	public void ret(int bytesReturnType, int bytesLocalVariables, int bytesParameters) {
		printlnt("ret\t" + bytesReturnType + ", " + bytesLocalVariables + ", " + bytesParameters);
	}
	
}
