package ast.statements;

import java.util.List;

import ast.expressions.Expression;

public class While extends AbstractStatement {

	private Expression condition;
	private List<Statement> body;
	
	public While(int line, int column, Expression condition, List<Statement> body) {
		super(line, column);
		this.condition = condition;
		this.body = body;
	}

	public Expression getCondition() {
		return condition;
	}

	public List<Statement> getBody() {
		return body;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public void setBody(List<Statement> body) {
		this.body = body;
	}

	@Override
	public String toString() {
		String out = "while " + condition + " : {\n";
		for (Statement statement : body)
			out += statement + "\n";
		out += "}\n";
		
		return  out;
	}
	
}
