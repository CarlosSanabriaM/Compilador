package ast.types;

import ast.ASTNode;
import errorHandler.EH;
import visitors.Visitor;

public class ErrorType extends AbstractType {

	public String message;
	
	public ErrorType(int line, int column, String message) {
		super(line, column);
		this.message = message;
		
		EH.getEH().addError(this);
	}

	public ErrorType(ASTNode node, String message) {
		this(node.getLine(), node.getColumn(), message);
	}

	@Override
	public String toString() {
		return "Error at line " + getLine() + " and column "+ getColumn() +":\n\t"+ message;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
