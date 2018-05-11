package ast.expressions;

import visitors.Visitor;

public class Indexing extends AbstractExpression{

	public Expression leftOp, rightOp;
	
	public Indexing(int line, int column, Expression leftOp, Expression rightOp) {
		super(line,column);
		this.leftOp = leftOp;
		this.rightOp = rightOp;
	}

	@Override
	public String toString() {
		return leftOp + "[" + rightOp + "]";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
