package codeGeneration;

import java.io.FileWriter;
import java.io.IOException;

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
	
	public void pusha(int address) {
		println("pusha " + address);
	}
	
	public void pushbp() {
		println("push bp"); //TODO - pusha bp??
	}
	
	public void push(char value) {
		println("pushb " + value);
	}
	
	public void push(int value) {
		println("pushi " + value);
	}
	
	public void push(double value) {
		println("pushf " + value);
	}
	
	public void call(String functionName) {
		println("call " + functionName);
	}
	
	public void callMain() {
		call("main");
	}
	
	public void halt() {
		println("halt");
	}

	public void out(Type type) {
		println("out" + type.suffix());
	}

	public void in(Type type) {
		println("in" + type.suffix());
	}

	public void store(Type type) {
		println("store" + type.suffix());	
	}
	
	public void b2i() {
		println("b2i");	
	}
	
	public void i2b() {
		println("i2b");	
	}
	
	public void i2f() {
		println("i2f");	
	}
	
	public void f2i() {
		println("f2i");	
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
	
}
