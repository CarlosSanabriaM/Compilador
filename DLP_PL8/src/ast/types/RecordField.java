package ast.types;

import ast.ASTNode;
import ast.definitions.VarDefinition;
import visitors.Visitor;

public class RecordField implements ASTNode {

	private int line, column;
	
	private Type type;
	public String name;
	public int offset;
	
	public RecordField(int line, int column, Type type, String name) {
		this.line = line;
		this.column = column;
		this.type = type;
		this.name = name;
	}
	
	public RecordField(VarDefinition varDefintion) {
		this.line = varDefintion.getLine();
		this.column = varDefintion.getColumn();
		this.type = varDefintion.getType();
		this.name = varDefintion.getName();
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
	public String toString() {
		return name + ": " + type;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	// equals y hashCode por nombre del campo
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecordField other = (RecordField) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
