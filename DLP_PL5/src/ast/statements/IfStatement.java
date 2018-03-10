package ast.statements;

import java.util.List;

import ast.expressions.Expression;

public class IfStatement extends AbstractStatement {

	private Expression condition;
	private List<Statement> ifBody;
	private List<Statement> elseBody;
	
	public IfStatement(int line, int column, Expression condition, 
			List<Statement> ifBody, List<Statement> elseBody) {
		
		super(line, column);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}

	public Expression getCondition() {
		return condition;
	}

	public List<Statement> getIfBody() {
		return ifBody;
	}

	public List<Statement> getElseBody() {
		return elseBody;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public void setIfBody(List<Statement> ifBody) {
		this.ifBody = ifBody;
	}

	public void setElseBody(List<Statement> elseBody) {
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
	
}
