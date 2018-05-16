package ast.expressions;

import visitors.Visitor;

public class FieldAccess extends AbstractExpression {

	public Expression leftOp;
	public String name;
	
	public FieldAccess(int line, int column, Expression leftOp, String name) {
		super(line, column);
		this.leftOp = leftOp;
		this.name = name;
	}

	@Override
	public String toString() {
		return leftOp + "." + name;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
