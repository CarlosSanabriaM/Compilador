package ast;

import java.util.List;

import ast.definitions.Definition;
import visitors.Visitor;

public class Program implements ASTNode {

	public List<Definition> definitions;
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

	@Override
	public String toString() {
		String out = "";
		for (Definition definition : definitions) {
			out+= definition + "\n";
		}
		return out;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 

}
