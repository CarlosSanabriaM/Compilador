package ast.expressions;

public class Indexing extends AbstractExpression{

	private Expression leftOp, rightOp;
	
	public Indexing(int line, int column, Expression leftOp, Expression rightOp) {
		super(line,column);
		this.leftOp = leftOp;
		this.rightOp = rightOp;
	}

	public Expression getLeftOp() {
		return leftOp;
	}

	public Expression getRightOp() {
		return rightOp;
	}

	public void setLeftOp(Expression leftOp) {
		this.leftOp = leftOp;
	}

	public void setRightOp(Expression rightOp) {
		this.rightOp = rightOp;
	}

	@Override
	public String toString() {
		return leftOp + "[" + rightOp + "]";
	}
	
}
