package ast.expressions;

public class Arithmetic implements Expression{

	private int line, column;
	private String operator;
	private Expression left, right;
	
	public Arithmetic(int line, int column, Expression left, String operator, Expression right) {
		this.line = line;
		this.column = column;
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return "(" + left + operator + right + ")";
	}
	
}
