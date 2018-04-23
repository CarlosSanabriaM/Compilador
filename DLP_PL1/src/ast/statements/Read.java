package ast.statements;

import ast.expressions.Expression;

public class Read implements Statement {

	private int line, column;
	private Expression variable;
	
	public Read(int line, int column, Expression variable) {
		this.line = line;
		this.column = column;
		this.variable = variable;
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
		return "input " + variable;
	}

}
