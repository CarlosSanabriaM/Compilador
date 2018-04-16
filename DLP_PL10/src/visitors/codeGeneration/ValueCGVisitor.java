package visitors.codeGeneration;

import ast.expressions.Arithmetic;
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

	@Override
	public Object visit(Arithmetic arithmetic, Object param) {
		arithmetic.leftOp.accept(this, param); 	// VALUE[[leftOp]]
		cg.convert(arithmetic.leftOp.getType(), arithmetic.getType());
		
		arithmetic.rightOp.accept(this, param); 	// VALUE[[rightOp]]
		cg.convert(arithmetic.rightOp.getType(), arithmetic.getType());
		
		cg.arithmetic(arithmetic.getType(), arithmetic.operator);
		
		return null;
	}

}
