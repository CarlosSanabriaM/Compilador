package ast.types;

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

}
