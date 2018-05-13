package ast.statementsAndExpressions;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;
import ast.statements.Statement;
import visitors.Visitor;

public class PostArithmetic extends AbstractExpression implements Statement{

	public Expression expression;
	public String operator;
	
	public PostArithmetic(int line, int column, Expression expression, String operator) {
		super(line, column);
		this.expression = expression;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return expression + operator;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
