package ast.statementsAndExpressions;

import java.util.Iterator;
import java.util.List;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;
import ast.expressions.Variable;
import ast.statements.Statement;
import visitors.Visitor;

public class Invocation extends AbstractExpression implements Statement{

	public Variable function;
	public List<Expression> arguments;
	
	public Invocation(int line, int column, Variable function, List<Expression> arguments) {
		super(line, column);
		this.function = function;
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		String out = function.name + "(";
		
		Iterator<Expression> itr = arguments.iterator();
		while(itr.hasNext()) {
			Expression argument = itr.next();
			out += argument + ", ";
			if(!itr.hasNext())
				out = out.substring(0, out.length()-2);
		}
		
		out += ")";
		
		return  out; 
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
