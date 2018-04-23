package ast.expressions;

public class FieldAccess extends AbstractExpression {

	private Expression leftOp;
	private String name;
	
	public FieldAccess(int line, int column, Expression leftOp, String name) {
		super(line, column);
		this.leftOp = leftOp;
		this.name = name;
	}

	public Expression getLeftOp() {
		return leftOp;
	}

	public String getName() {
		return name;
	}

	public void setLeftOp(Expression leftOp) {
		this.leftOp = leftOp;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return leftOp + "." + name;
	}
	
}
