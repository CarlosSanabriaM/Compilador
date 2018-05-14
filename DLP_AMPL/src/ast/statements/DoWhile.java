package ast.statements;

import java.util.List;

import ast.expressions.Expression;
import visitors.Visitor;

public class DoWhile extends AbstractStatement {

	public Expression condition;
	public List<Statement> body;
	
	public DoWhile(int line, int column, Expression condition, List<Statement> body) {
		super(line, column);
		this.condition = condition;
		this.body = body;
	}

	@Override
	public String toString() {
		String out = "do : {\n";
		for (Statement statement : body)
			out += statement + "\n";
		out += "} while " + condition + " ;\n";
		
		return  out;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
