package ast.expressions;

import visitors.Visitor;

public class CharLiteral extends AbstractExpression{

	public char value;
	
	public CharLiteral(int line, int column, char value) {
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
