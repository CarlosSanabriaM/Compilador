package ast.statements;

import ast.expressions.Expression;

public class Assignment extends AbstractStatement {

	private Expression variable, expression;
	
	public Assignment(int line, int column, Expression variable, Expression expression) {
		super(line,column);
		this.variable = variable;
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return variable + "=" + expression;
	}
	
}
