package ast.expressions;

import visitors.Visitor;

public class RealLiteral extends AbstractExpression{

	public double value;
	
	public RealLiteral(int line, int column, double value) {
		super(line, column);
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 

}
