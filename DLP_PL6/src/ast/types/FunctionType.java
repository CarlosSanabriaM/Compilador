package ast.types;

import java.util.Iterator;
import java.util.List;

import ast.definitions.VarDefinition;
import visitors.Visitor;

public class FunctionType extends AbstractType {

	public List<VarDefinition> param;
	public Type returnType;
	
	public FunctionType(int line, int column, List<VarDefinition> param, Type returnType) {
		super(line, column);
		this.param = param;
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		String out = "(";
		
		Iterator<VarDefinition> itr = param.iterator();
		while(itr.hasNext()) {
			VarDefinition param = itr.next();
			out += param + ", ";
			if(!itr.hasNext())
				out = out.substring(0, out.length()-2);
		}
		
		out += ")";
		out += ": " + returnType;
		
		return out;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
