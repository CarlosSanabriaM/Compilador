package ast.expressions;

import ast.ASTNode;

public interface Expression extends ASTNode{
	
	/**
	 * Set if the expression can be used in the left part of an assignment
	 * or can be used in a Read statement
	 */
	void setLValue(boolean lValue);
	
	/**
	 * Returns true if the expression can be used in the left part of an assignment
	 * or can be used in a Read statement
	 */
	boolean getLValue();
	
}
