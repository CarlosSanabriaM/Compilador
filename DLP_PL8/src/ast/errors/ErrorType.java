package ast.errors;

import ast.ASTNode;
import ast.types.Type;
import visitors.Visitor;

public class ErrorType implements Type {

	private int line, column;
	public String message;
	
	public ErrorType(int line, int column, String message) {
		this.line = line;
		this.column = column;
		this.message = message;
		
		EH.getEH().addError(this);
	}

	public ErrorType(ASTNode node, String message) {
		this(node.getLine(), node.getColumn(), message);
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
		return "Error at line " + line + " and column "+ column +":\n\t"+ message;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
