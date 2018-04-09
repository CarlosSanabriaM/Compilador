package ast.statements;

import ast.expressions.Expression;
import visitors.Visitor;

public class Assignment extends AbstractStatement {

	public Expression left, right;
	
	public Assignment(int line, int column, Expression left, Expression right) {
		super(line,column);
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left + " = " + right + ";";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
