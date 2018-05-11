package ast.expressions;

import visitors.Visitor;

public class UnaryNot extends AbstractExpression{

	public Expression expression;
	
	public UnaryNot(int line, int column, Expression expression) {
		super(line,column);
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "!" + expression;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
