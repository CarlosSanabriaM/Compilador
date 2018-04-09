package ast.statements;

import ast.expressions.Expression;
import visitors.Visitor;

public class Return extends AbstractStatement {

	public Expression expression;

	public Return(int line, int column, Expression expression) {
		super(line, column);
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "return " + expression +";";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
