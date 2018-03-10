package ast.expressions;

public class Comparison extends AbstractExpression{

	private String operator;
	private Expression leftOp, rightOp;
	
	public Comparison(int line, int column, Expression leftOp, String operator, Expression rightOp) {
		super(line,column);
		this.leftOp = leftOp;
		this.operator = operator;
		this.rightOp = rightOp;
	}
	
	public String getOperator() {
		return operator;
	}

	public Expression getLeftOp() {
		return leftOp;
	}

	public Expression getRightOp() {
		return rightOp;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setLeftOp(Expression leftOp) {
		this.leftOp = leftOp;
	}

	public void setRightOp(Expression rightOp) {
		this.rightOp = rightOp;
	}

	@Override
	public String toString() {
//		return "(" + leftOp + " " + operator + " " + rightOp + ")";
		return leftOp + " " + operator + " " + rightOp;
	}
	
}
