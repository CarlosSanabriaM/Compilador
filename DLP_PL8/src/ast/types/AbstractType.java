package ast.types;

import java.util.List;

public abstract class AbstractType implements Type {

	private int line, column;
	
	public AbstractType(int line, int column) {
		this.line = line;
		this.column = column;
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public boolean isBuiltIn() {
		return false;
	}

	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public Type arithmetic(Type type) {
		return null;
	}

	@Override
	public Type arithmetic() {
		return null;
	}

	@Override
	public Type comparison(Type type) {
		return null;
	}

	@Override
	public Type logical(Type type) {
		return null;
	}

	@Override
	public Type logical() {
		return null;
	}

	@Override
	public Type promotesTo(Type type) {
		return null;
	}

	@Override
	public Type canBeCast(Type type) {
		return null;
	}

	@Override
	public Type dot(String fieldName) {
		return null;
	}

	@Override
	public Type squareBrackets(Type type) {
		return null;
	}

	@Override
	public Type parenthesis(List<Type> types) {
		return null;
	}

}
