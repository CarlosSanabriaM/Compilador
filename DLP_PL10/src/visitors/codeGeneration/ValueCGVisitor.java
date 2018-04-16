package visitors.codeGeneration;

import ast.expressions.CharLiteral;
import ast.expressions.IntLiteral;
import ast.expressions.RealLiteral;
import ast.expressions.Variable;
import codeGeneration.CodeGenerator;

/**
 *	Para la función de código VALUE[[ - ]].
 *	Habrá un visit() por cada plantilla de código.
 */
public class ValueCGVisitor extends AbstractCGVisitor {

	private CodeGenerator cg;
	private AddressCGVisitor addressCGVisitor;
	
	public ValueCGVisitor(CodeGenerator cg) {
		this.cg = cg;
		
		addressCGVisitor = new AddressCGVisitor(cg);//TODO - crear aqui o recibirlo como parametro del ctor??
	}

	@Override
	public Object visit(CharLiteral charLiteral, Object param) {
		cg.push(charLiteral.value);
		
		return null;
	}

	@Override
	public Object visit(IntLiteral intLiteral, Object param) {
		cg.push(intLiteral.value);
		
		return null;
	}

	@Override
	public Object visit(RealLiteral realLiteral, Object param) {
		cg.push(realLiteral.value);
		
		return null;
	}
	
	@Override
	public Object visit(Variable variable, Object param) {
		variable.accept(addressCGVisitor, param); // ADDRESS[[variable]]
		cg.load(variable.getType());
		
		return null;
	}

}
