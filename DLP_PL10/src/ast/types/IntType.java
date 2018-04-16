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

	@Override
	public Type promotesTo(Type type) {		
		if(type instanceof ErrorType ||
				type instanceof RealType ||
				type instanceof IntType)
			return type;
		
		// Int NO promociona a Char (Java si lo permite)
		
		return null;
	}
	
	@Override
	public Type canBeCast(Type type) {
		if(type instanceof ErrorType ||
				type.isBuiltIn())
			return type;
		
		return null;
	}

	@Override
	public Type logical() {
		return this;
	}
	
	@Override
	public Type logical(Type type) {
		if(type.isLogical())
			return this;
		
		return null;
	}

	@Override
	public int numBytes() {
		return 2;
	}
	
}
