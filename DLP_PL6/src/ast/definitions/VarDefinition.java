package ast.definitions;

import ast.statements.Statement;
import ast.types.Type;
import visitors.Visitor;

public class VarDefinition extends AbstractDefinition implements Statement{

	public int scope;
	public int offset;
	
	public VarDefinition(int line, int column, String name, Type type) {
		super(line, column, name, type);
	}

	@Override
	public String toString() {
		return getName() + ": " + getType() + ";";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
