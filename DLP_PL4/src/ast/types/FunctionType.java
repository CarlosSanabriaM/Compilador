package ast.types;

import java.util.Iterator;
import java.util.List;

import ast.definitions.VarDefinition;

public class FunctionType extends AbstractType {

	private List<VarDefinition> param;
	private Type returnType;
	
	public FunctionType(int line, int column, List<VarDefinition> param, Type returnType) {
		super(line, column);
		this.param = param;
		this.returnType = returnType;
	}

	public List<VarDefinition> getParam() {
		return param;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setParam(List<VarDefinition> param) {
		this.param = param;
	}

	public void setReturnType(Type returnType) {
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
	
}
