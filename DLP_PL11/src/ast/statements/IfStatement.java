package ast.statements;

import java.util.List;

import ast.expressions.Expression;
import visitors.Visitor;

public class IfStatement extends AbstractStatement {

	public Expression condition;
	public List<Statement> ifBody;
	public List<Statement> elseBody;
	
	public IfStatement(int line, int column, Expression condition, 
			List<Statement> ifBody, List<Statement> elseBody) {
		
		super(line, column);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}

	@Override
	public String toString() {
		String out = "if " + condition + " : {\n";
		for (Statement statement : ifBody)
			out += statement + "\n";
		out += "}\n";
		
		if(!elseBody.isEmpty()) {
			out += "else {\n";
			for (Statement statement : elseBody)
				out += statement + "\n";
			out += "}\n";
		}
		
		return  out;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
