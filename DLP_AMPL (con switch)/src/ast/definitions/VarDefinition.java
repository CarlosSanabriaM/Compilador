package ast.definitions;

import ast.statements.Statement;
import ast.types.Type;
import visitors.Visitor;

public class VarDefinition extends AbstractDefinition implements Statement{

	public int scope;
	public int offset;
	private boolean assignsValue;
	private boolean isntLoopOrSwitchAndHasBreak;
	
	public VarDefinition(int line, int column, String name, Type type) {
		super(line, column, name, type);
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return getName() + ": " + getType() + ";";
	}
	
	@Override
	public void setAssignsValue(boolean assignsValue) {
		this.assignsValue = assignsValue;
	}

	@Override
	public boolean getAssignsValue() {
		return this.assignsValue;
	}
	
	public boolean getIsntLoopOrSwitchAndHasBreak() {
		return isntLoopOrSwitchAndHasBreak;
	}

	public void setIsntLoopOrSwitchAndHasBreak(boolean isntLoopOrSwitchAndHasBreak) {
		this.isntLoopOrSwitchAndHasBreak = isntLoopOrSwitchAndHasBreak;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
