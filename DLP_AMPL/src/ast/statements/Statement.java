package ast.statements;

import ast.ASTNode;

public interface Statement extends ASTNode{

	/**
	 * Set if the statement assigns value to some expression
	 */
	void setAssignsValue(boolean assignsValue);
	
	/**
	 * Returns true if the statement assigns value to some expression
	 */
	boolean getAssignsValue();
	
}
