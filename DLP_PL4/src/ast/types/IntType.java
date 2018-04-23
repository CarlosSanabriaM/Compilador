package ast.types;

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
	
}
