package ast.errors;

import ast.ASTNode;
import ast.types.Type;

public class ErrorType implements Type {

	private int line, column;
	private String message;
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		// return "Se ha producido un error en la línea " + line + " , columna "+ column +". "+ message;
		return "Error at line " + line + " and column "+ column +":\n\t"+ message;
	}
	
}
