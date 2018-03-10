package ast;

import java.util.List;

import ast.definitions.Definition;

public class Program implements ASTNode {

	private List<Definition> definitions;
	private int line, column;

	public Program(int line, int column, List<Definition> definitions) {
		this.definitions = definitions;
		this.line = line;
		this.column = column;
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}
	
	public List<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<Definition> definitions) {
		this.definitions = definitions;
	}

	@Override
	public String toString() {
		String out = "";
		for (Definition definition : definitions) {
			out+= definition + "\n";
		}
		return out;
	}

}
