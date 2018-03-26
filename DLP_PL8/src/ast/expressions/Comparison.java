package ast.expressions;

import visitors.Visitor;

public class Comparison extends AbstractExpression{

	public String operator;
	public Expression leftOp, rightOp;
	
	public Comparison(int line, int column, Expression leftOp, String operator, Expression rightOp) {
		super(line,column);
		this.leftOp = leftOp;
		this.operator = operator;
		this.rightOp = rightOp;
	}
	
	@Override
	public String toString() {
//		return "(" + leftOp + " " + operator + " " + rightOp + ")";
		return leftOp + " " + operator + " " + rightOp;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
