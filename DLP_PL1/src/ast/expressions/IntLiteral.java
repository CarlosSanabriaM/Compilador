package ast.expressions;

public class IntLiteral implements Expression{

	private int line, column;
	private int value;
	
	public IntLiteral(int line, int column, int value) {
		this.line = line;
		this.column = column;
		this.value = value;
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
		return String.valueOf(value);
	}

}
