package ast.statements;

import ast.expressions.Expression;

public class Assignment extends AbstractStatement {

	private Expression left, right;
	
	public Assignment(int line, int column, Expression left, Expression right) {
		super(line,column);
		this.left = left;
		this.right = right;
	}
	
	public Expression getLeft() {
		return left;
	}
	
	public Expression getRight() {
		return right;
	}

	public void setLeft(Expression left) {
		this.left = left;
	}

	public void setRight(Expression right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return left + " = " + right + ";";
	}
	
}
