package ast.expressions;

public class UnaryMinus implements Expression {

	private int line, column;
	private Expression expression;
	
	public UnaryMinus(int line, int column, Expression expression) {
		this.line = line;
		this.column = column;
		this.expression = expression;
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public String toString() {
		return "-" + expression;
	}
	
}
