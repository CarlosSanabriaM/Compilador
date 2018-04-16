package ast.definitions;

import java.util.List;

import ast.statements.Statement;
import ast.types.FunctionType;
import visitors.Visitor;

public class FunDefinition extends AbstractDefinition {

	public List<Statement> statements;
	
	public int bytesLocalVariables;

	public FunDefinition(int line, int column, String name, FunctionType type, List<Statement> statements) {
		super(line, column, name, type);
		this.statements = statements;
	}
	
	@Override
	public String toString() {
		String out = "\n";
		out += "def " + getName() + getType() + " {\n";
		for (Statement statement : statements)
			out += statement + "\n";
		out += "}\n";
		
		return out;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
