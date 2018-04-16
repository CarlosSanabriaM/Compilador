package codeGeneration;

import java.io.FileWriter;
import java.io.IOException;

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
	
}
