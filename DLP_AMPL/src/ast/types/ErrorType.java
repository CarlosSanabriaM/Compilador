package ast.types;

import java.util.List;

import ast.ASTNode;
import errorHandler.EH;
import visitors.Visitor;

public class ErrorType extends AbstractType {

	public String message;
	
	public ErrorType(int line, int column, String message) {
		super(line, column);
		this.message = message;
		
		EH.getEH().addError(this);
	}

	public ErrorType(ASTNode node, String message) {
		this(node.getLine(), node.getColumn(), message);
	}

	@Override
	public String toString() {
		return "Error at line " + getLine() + " and column "+ getColumn() +":\n\t"+ message;
	}

	// Visitor --> En todos los visit retornamos this
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Type
	@Override
	public Type arithmetic(Type type) {
		return this;
	}

	@Override
	public Type arithmetic() {
		return this;
	}
	
	@Override
	public Type pArithmetic() {
		return this;
	}

	@Override
	public Type comparison(Type type) {
		return this;
	}

	@Override
	public Type logical(Type type) {
		return this;
	}

	@Override
	public Type logical() {
		return this;
	}

	@Override
	public Type promotesTo(Type type) {
		return this;
	}

	@Override
	public Type canBeCast(Type type) {
		return this;
	}

	@Override
	public Type dot(String fieldName) {
		return this;
	}

	@Override
	public Type squareBrackets(Type type) {
		return this;
	}

	@Override
	public Type parenthesis(List<Type> types) {
		return this;
	}

	@Override
	public Type superType(Type type) {
		return this;
	} 

	@Override
	public Type rightfulSuperType(Type type) {		
		return this;
	}
	
}
