package ast.types;

import visitors.Visitor;

public class ArrayType extends AbstractType {
	
	public int size;
	public Type of;
	
	public ArrayType(int line, int column, int size, Type of) {
		super(line, column);
		this.size = size;
		this.of = of;
	}

	@Override
	public String toString() {
		return "[" + size + "]" + of;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
