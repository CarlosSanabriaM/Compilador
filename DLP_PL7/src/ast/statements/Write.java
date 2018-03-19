package ast.statements;

import ast.expressions.Expression;
import visitors.Visitor;

public class Write extends AbstractStatement {

	public Expression expression;
	
	public Write(int line, int column, Expression expression) {
		super(line,column);
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "print " + expression + ";";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
