package ast.types;

import visitors.Visitor;

public class CharType extends AbstractType {
	
	private static final CharType instance = new CharType();

	private CharType() {
		super(0, 0);
	}
	
	public static CharType getInstance() {
		return instance;
	}

	@Override
	public String toString() {
		return "char";
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
				type instanceof IntType ) 
			return type;
		
		// El resultado de 'a' + 'a' es un entero 
		if(type instanceof CharType)
			return IntType.getInstance();
		
		return null;
	}

	@Override
	public Type arithmetic() {
		return IntType.getInstance();
	}
	
	@Override
	public Type pArithmetic() {
		// c: char; c = 'a'; c++; // c = 'b'
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
				type.isBuiltIn())
			return type;
		
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
		return IntType.getInstance();
	}

	@Override
	public Type logical(Type type) {
		if(type.isLogical())
			return IntType.getInstance();
		
		return null;
	}
	
	@Override
	public Type superType(Type type) {
		// Si tenemos 'a' > 'a', hay que convertir ambos a int
		// ya que no existe el operador gtb, por ejemplo.
		if(type instanceof CharType)
			return IntType.getInstance();
		
		if(type instanceof ErrorType ||
				type.isBuiltIn())
			return type;
		
		return null;
	}
	
	@Override
	public Type rightfulSuperType(Type type) {		
		if(type instanceof ErrorType ||
				type.isBuiltIn())
			return type;
		
		return null;
	}
	

	@Override
	public int numBytes() {
		return 1;
	}

	
	@Override
	public char suffix() {
		return 'b';
	} 
	
	@Override
	public String getDirectiveInfo() {
		return "char";
	}
	
}
