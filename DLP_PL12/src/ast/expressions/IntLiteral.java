package ast.expressions;

import visitors.Visitor;

public class IntLiteral extends AbstractExpression{

	public int value;
	
	public IntLiteral(int line, int column, int value) {
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
