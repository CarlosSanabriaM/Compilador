package visitors.codeGeneration;

import ast.definitions.Scope;
import ast.definitions.VarDefinition;
import ast.expressions.Variable;
import ast.types.IntType;
import codeGeneration.CodeGenerator;

/**
 *	Para la función de código ADDRESS[[ - ]].
 *	Habrá un visit() por cada plantilla de código.
 */
public class AddressCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	
	public AddressCGVisitor(CodeGenerator cg) {
		this.cg = cg;
	}

	@Override
	public Object visit(Variable variable, Object param) {
		VarDefinition def = (VarDefinition) variable.definition;

		// Si la variable es global, el offset es su dirección tal cual		
		if(def.scope == Scope.GLOBAL)
			cg.pusha(def.offset);		
		else {
			cg.pushbp();
			cg.push(def.offset);
			cg.add(IntType.getInstance());
		}
		
		return null;
	}
	
}
