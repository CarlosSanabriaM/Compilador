package ast.expressions;

public class IntLiteral extends AbstractExpression{

	private int value;
	
	public IntLiteral(int line, int column, int value) {
		super(line, column);
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
