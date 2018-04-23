package ast.types;

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
	
}
