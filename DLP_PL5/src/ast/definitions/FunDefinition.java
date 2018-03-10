package ast.definitions;

import java.util.List;

import ast.statements.Statement;
import ast.types.FunctionType;

public class FunDefinition extends AbstractDefinition {

	
	private List<Statement> statements;

	public FunDefinition(int line, int column, String name, FunctionType type, List<Statement> statements) {
		super(line, column, name, type);
		this.statements = statements;
	}
	
	public List<Statement> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement> statements) {
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

}
