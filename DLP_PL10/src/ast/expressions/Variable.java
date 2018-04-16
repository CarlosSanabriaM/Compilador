package ast.expressions;

import ast.definitions.Definition;
import visitors.Visitor;

public class Variable extends AbstractExpression {

	public String name;
	public Definition definition; // TODO - VarDefinition??

	public Variable(int line, int column, String name) {
		super(line, column);
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
