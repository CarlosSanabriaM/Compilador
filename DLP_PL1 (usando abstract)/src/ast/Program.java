package ast;

import java.util.List;

import ast.statements.Statement;

public class Program implements ASTNode {

	private List<Statement> statements;
	private int line, column;

	public Program(int line, int column, List<Statement> statements) {
		this.statements = statements;
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
		for (Statement statement : statements) {
			out+= statement + ";\n";
		}
		return out;
	}

}
