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

}
