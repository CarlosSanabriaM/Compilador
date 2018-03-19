package ast.types;

public abstract class AbstractType implements Type {

	private int line, column;
	
	public AbstractType(int line, int column) {
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
