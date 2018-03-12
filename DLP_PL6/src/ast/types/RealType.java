package ast.types;

import visitors.Visitor;

public class RealType extends AbstractType {
	
	private static final RealType instance = new RealType();

	private RealType() {
		super(0, 0);
	}
	
	public static RealType getInstance() {
		return instance;
	}

	@Override
	public String toString() {
		return "double";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
