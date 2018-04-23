package ast.statements;

import ast.expressions.Expression;

public class Assignment implements Statement {

	private int line, column;
	private Expression variable, expression;
	
	public Assignment(int line, int column, Expression variable, Expression expression) {
		this.line = line;
		this.column = column;
		this.variable = variable;
		this.expression = expression;
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
		return variable + "=" + expression;
	}
	
}
