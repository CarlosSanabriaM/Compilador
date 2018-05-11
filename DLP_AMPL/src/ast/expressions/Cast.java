package ast.expressions;

import ast.types.Type;
import visitors.Visitor;

public class Cast extends AbstractExpression {

	public Type castType;
	public Expression expression;
	
	public Cast(int line, int column, Type castType, Expression expression) {
		super(line, column);
		this.castType = castType;
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "(" + castType + ") " + expression;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
