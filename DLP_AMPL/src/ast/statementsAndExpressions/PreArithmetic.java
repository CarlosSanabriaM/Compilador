package ast.statementsAndExpressions;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;
import ast.statements.Statement;
import visitors.Visitor;

public class PreArithmetic extends AbstractExpression implements Statement{

	public Expression expression;
	public String operator;
	
	public PreArithmetic(int line, int column, Expression expression, String operator) {
		super(line, column);
		this.expression = expression;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return operator + expression;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
