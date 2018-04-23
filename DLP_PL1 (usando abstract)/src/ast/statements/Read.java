package ast.statements;

import ast.expressions.Expression;

public class Read extends AbstractStatement{

	private Expression variable;
	
	public Read(int line, int column, Expression variable) {
		super(line, column);
		this.variable = variable;
	}
	
	@Override
	public String toString() {
		return "input " + variable;
	}

}
