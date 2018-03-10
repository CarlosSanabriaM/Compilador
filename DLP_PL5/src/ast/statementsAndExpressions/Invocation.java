package ast.statementsAndExpressions;

import java.util.Iterator;
import java.util.List;

import ast.expressions.Expression;
import ast.expressions.Variable;
import ast.statements.AbstractStatement;

public class Invocation extends AbstractStatement implements Expression{

	private Variable function;
	private List<Expression> arguments;
	
	public Invocation(int line, int column, Variable function, List<Expression> arguments) {
		super(line, column);
		this.function = function;
		this.arguments = arguments;
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public Variable getFunction() {
		return function;
	}

	public void setFunction(Variable function) {
		this.function = function;
	}

	public void setArguments(List<Expression> arguments) {
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		String out = function.getName() + "(";
		
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
	
}
