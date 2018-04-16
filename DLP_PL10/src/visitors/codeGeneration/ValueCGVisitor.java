package visitors.codeGeneration;

import codeGeneration.CodeGenerator;

/**
 *	Para la función de código VALUE[[ - ]].
 *	Habrá un visit() por cada plantilla de código.
 */
public class ValueCGVisitor extends AbstractCGVisitor {

	private CodeGenerator cg;
	
	public ValueCGVisitor(CodeGenerator cg) {
		this.cg = cg;
	}

}
