package ast.statements;

import java.util.List;

import ast.expressions.Expression;
import visitors.Visitor;

public class For extends AbstractStatement {

	public List<Statement> initializationStatements;
	public Expression condition;
	public List<Statement> incrementStatements;
	public List<Statement> body;
	
	public For(int line, int column, List<Statement> initializationStatements, Expression condition,
			List<Statement> incrementStatements, List<Statement> body) {
		super(line, column);
		this.initializationStatements = initializationStatements;
		this.condition = condition;
		this.incrementStatements = incrementStatements;
		this.body = body;
	}

	@Override
	public String toString() {
		String initStmsString = getParenthesisBodyStatementsAsString(initializationStatements);
		String incrStmsString = getParenthesisBodyStatementsAsString(incrementStatements);
		incrStmsString = incrStmsString.substring(0, incrStmsString.length()-1);
		
		String forParenthesisBody = initStmsString + " " + condition + "; " + incrStmsString;
		
		String out = "for( " + forParenthesisBody + " ) : {\n";
		for (Statement statement : body)
			out += statement + "\n";
		out += "}\n";
		
		return  out;
	}

	private String getParenthesisBodyStatementsAsString(List<Statement> parenthesisBodyStatements) {
		String out = ""; 
		
		for (Statement statement : parenthesisBodyStatements) {
			out += statement;
			
			if(out.charAt(out.length()-1) != ';')
				out += ";";
		}
			
		out = out.replaceAll(";", ",");
		out = out.substring(0, out.length()-1);
		out += ";";
		
		return out;
	}
	
	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
