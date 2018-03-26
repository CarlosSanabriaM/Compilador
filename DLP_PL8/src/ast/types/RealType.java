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
	
	// Type
	@Override
	public boolean isBuiltIn() {
		return true;
	}

	@Override
	public Type arithmetic(Type type) {
		if(type instanceof ErrorType ||
				type instanceof RealType)
			return type;
				
		if(type instanceof IntType || 
				type instanceof CharType)
			return this;
		
		return null;
	}

	@Override
	public Type arithmetic() {
		return this;
	}

	@Override
	public Type comparison(Type type) {
		if(type instanceof ErrorType)
			return type;
		
		if(type.isBuiltIn())
			return IntType.getInstance();

		return null;
	}

}
