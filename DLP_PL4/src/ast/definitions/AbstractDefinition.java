package ast.definitions;

import ast.types.Type;

public abstract class AbstractDefinition implements Definition {

	private int line, column;
	private String name;
	private Type type;
	
	public AbstractDefinition(int line, int column, String name, Type type) {
		this.line = line;
		this.column = column;
		this.name = name;
		this.type = type;
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		return this.type;
	}
	
	//setName() y setType()

}
