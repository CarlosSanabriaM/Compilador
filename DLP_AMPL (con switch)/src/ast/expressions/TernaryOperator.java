package ast.expressions;

import visitors.Visitor;

public class TernaryOperator extends AbstractExpression{

	public Expression condition;
	public Expression trueExpression;
	public Expression falseExpression;
	
	public TernaryOperator(int line, int column, Expression condition, Expression trueExpression,
			Expression falseExpression) {
		
		super(line, column);
		this.condition = condition;
		this.trueExpression = trueExpression;
		this.falseExpression = falseExpression;
	}

	@Override
	public String toString() {
		return condition + " ? " + trueExpression + " : " + falseExpression;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
