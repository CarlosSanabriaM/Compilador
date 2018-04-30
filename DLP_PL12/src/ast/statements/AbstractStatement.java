package ast.statements;

public abstract class AbstractStatement implements Statement {

	private int line, column;
	
	public AbstractStatement(int line, int column) {
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
