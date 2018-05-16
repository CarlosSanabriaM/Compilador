package ast.statements;

import ast.ASTNode;

public interface Statement extends ASTNode{

	/**
	 * Set if the statement assigns value to some expression.
	 */
	void setAssignsValue(boolean assignsValue);
	
	/**
	 * Returns true if the statement assigns value to some expression.
	 */
	boolean getAssignsValue();
	
	/**
	 * Set if the statement isnt a loop or a switch and has a break statement.
	 */
	void setIsntLoopOrSwitchAndHasBreak(boolean isntLoopOrSwitchAndHasBreak);
	
	/**
	 * Returns true if the statement isnt a loop or a switch and has a break statement.
	 */
	boolean getIsntLoopOrSwitchAndHasBreak();
	
}
