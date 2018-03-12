package ast.expressions;

public abstract class AbstractExpression implements Expression{

	private int line, column;
	private boolean lValue;
	
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

	@Override
	public void setLValue(boolean lValue) {
		this.lValue = lValue;
	}

	@Override
	public boolean getLValue() {
		return lValue;
	}
	
}
