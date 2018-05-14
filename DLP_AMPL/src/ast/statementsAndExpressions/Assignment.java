package ast.statementsAndExpressions;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;
import ast.statements.Statement;
import visitors.Visitor;

public class Assignment extends AbstractExpression implements Statement{

	public Expression left, right;
	
	public Assignment(int line, int column, Expression left, Expression right) {
		super(line,column);
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left + " = " + right;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
