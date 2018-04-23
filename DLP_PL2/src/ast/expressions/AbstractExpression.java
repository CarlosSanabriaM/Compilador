package ast.expressions;

public class AbstractExpression implements Expression{

	private int line, column;
	
	public AbstractExpression(int line, int column) {
		this.line = line;
		this.column = column;
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}
	
}
