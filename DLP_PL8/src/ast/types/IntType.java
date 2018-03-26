package ast.types;

import visitors.Visitor;

public class IntType extends AbstractType {
	
	private static final IntType instance = new IntType();

	private IntType() {
		super(0, 0);
	}
	
	public static IntType getInstance() {
		return instance;
	}

	@Override
	public String toString() {
		return "int";
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Type
	@Override
	public boolean isBuiltIn() {
		return true;
	}

	@Override
	public boolean isLogical() {
		return true;
	}
	
	@Override
	public Type arithmetic(Type type) {
		if(type instanceof ErrorType ||
				type instanceof RealType ||
				type instanceof IntType)
			return type;
				
		if(type instanceof CharType)
			return this;
		
		return null;
	}
	
}
