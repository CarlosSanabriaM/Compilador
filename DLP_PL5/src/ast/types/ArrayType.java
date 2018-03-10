package ast.types;

public class ArrayType extends AbstractType {
	
	private int size;
	private Type of;
	
	public ArrayType(int line, int column, int size, Type of) {
		super(line, column);
		this.size = size;
		this.of = of;
	}

	public int getSize() {
		return size;
	}

	public Type getOf() {
		return of;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setOf(Type of) {
		this.of = of;
	}

	@Override
	public String toString() {
		return "[" + size + "]" + of;
	}
	
}
