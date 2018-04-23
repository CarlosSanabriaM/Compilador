package ast.types;

import ast.ASTNode;
import ast.definitions.VarDefinition;

public class RecordField implements ASTNode {

	private int line, column;
	
	private Type type;
	private String name;
	private int offset;
	
	public RecordField(int line, int column, Type type, String name, int offset) {
		this.line = line;
		this.column = column;
		this.type = type;
		this.name = name;
		this.offset = offset;
	}
	
	public RecordField(VarDefinition varDefintion) {
		this.line = varDefintion.getLine();
		this.column = varDefintion.getColumn();
		this.type = varDefintion.getType();
		this.name = varDefintion.getName();
		this.offset = varDefintion.getOffset();
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getOffset() {
		return offset;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return name + ": " + type;
	}
	
}
