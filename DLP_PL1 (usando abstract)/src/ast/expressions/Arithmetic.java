package ast.expressions;

public class Arithmetic extends AbstractExpression{

	private String operator;
	private Expression left, right;
	
	public Arithmetic(int line, int column, Expression left, String operator, Expression right) {
		super(line,column);
		this.left = left;
		this.operator = operator;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "(" + left + operator + right + ")";
	}
	
}
