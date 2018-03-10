package ast.definitions;

import ast.statements.Statement;
import ast.types.Type;

public class VarDefinition extends AbstractDefinition implements Statement{

	private int scope;
	private int offset;
	
	public VarDefinition(int line, int column, String name, Type type) {
		super(line, column, name, type);
	}

	public int getScope() {
		return scope;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return getName() + ": " + getType() + ";";
	}

}
