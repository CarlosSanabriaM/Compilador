package visitors.codeGeneration;

import ast.definitions.Scope;
import ast.definitions.VarDefinition;
import ast.expressions.Indexing;
import ast.expressions.Variable;
import ast.types.IntType;
import codeGeneration.CodeGenerator;

/**
 *	Para la función de código ADDRESS[[ - ]].
 *	Habrá un visit() por cada plantilla de código.
 */
public class AddressCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	private ValueCGVisitor valueCGVisitor;
	
	public AddressCGVisitor(CodeGenerator cg) {
		this.cg = cg; 
	}
	
	public void setValueCGVisitor(ValueCGVisitor valueCGVisitor) {
		this.valueCGVisitor = valueCGVisitor;
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

	@Override
	public Object visit(Indexing indexing, Object param) {
		indexing.leftOp.accept(this, param);				// ADDRESS[[leftOp]]
		indexing.rightOp.accept(valueCGVisitor, param);	// VALUE[[rightOp]]
		
		cg.convert(indexing.rightOp.getType(), IntType.getInstance());
		
		cg.push(indexing.getType().numBytes());
		cg.mul(IntType.getInstance());
		cg.add(IntType.getInstance());
		
		return null;
	}

}
