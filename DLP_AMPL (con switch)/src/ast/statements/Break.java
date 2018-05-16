package ast.statements;

import visitors.Visitor;

public class Break extends AbstractStatement {
	
	public Break(int line, int column) {
		super(line, column);
	}

	@Override
	public String toString() {
		return "break;";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
