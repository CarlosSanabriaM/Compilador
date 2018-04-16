package visitors.codeGeneration;

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

}
