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

	//Type
	@Override
	public Type parenthesis(List<Type> types) {
		// Comprobamos que coincida el número de los parámetros
		if(types.size() != param.size())
			return null;
		
		// Comprobamos uno a uno que el tipo del parámetro que me pasan 
		// promocione al tipo del parametro declarado
		Iterator<VarDefinition> itrParam = param.iterator();
		Iterator<Type> itrTypes = types.iterator();
		
		while(itrParam.hasNext() && itrTypes.hasNext()) { // Son del mismo tamaño, da igual comprobar solo uno o ambos
		   Type declaredParamType = itrParam.next().getType();
		   Type passedParamType = itrTypes.next();
		   
		   if(passedParamType.promotesTo(declaredParamType) == null)
			   return null;
		}
		
		// Si todo es correcto, devuelvo el tipo de retorno
		return returnType;
	} 

	@Override
	public int numBytes() {
		return 0; // XXX ??
	}
		
}
