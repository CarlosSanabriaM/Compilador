package ast.types;

import visitors.Visitor;

public class VoidType extends AbstractType {
	
	private static final VoidType instance = new VoidType();

	private VoidType() {
		super(0, 0);
	}
	
	public static VoidType getInstance() {
		return instance;
	}

	@Override
	public String toString() {
		return "void";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 

	@Override
	public String getDirectiveInfo() {
		return "void";
	}
	
}
