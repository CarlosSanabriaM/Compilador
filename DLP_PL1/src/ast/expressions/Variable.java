package ast.expressions;

public class Variable implements Expression {

	private int line, column;
	private String name;

	public Variable(int line, int column, String name) {
		this.line = line;
		this.column = column;
		this.name = name;
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
		return name;
	}

}
