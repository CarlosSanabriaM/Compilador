package visitors.codeGeneration;

import ast.expressions.Arithmetic;
import ast.expressions.Cast;
import ast.expressions.CharLiteral;
import ast.expressions.Comparison;
import ast.expressions.FieldAccess;
import ast.expressions.Indexing;
import ast.expressions.IntLiteral;
import ast.expressions.Logical;
import ast.expressions.RealLiteral;
import ast.expressions.UnaryNot;
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
	}

	public void setAddressCGVisitor(AddressCGVisitor addressCGVisitor) {
		this.addressCGVisitor = addressCGVisitor;
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
	public Object visit(Arithmetic arithm, Object param) {
		arithm.leftOp.accept(this, param);	// VALUE[[leftOp]]
		cg.convert(arithm.leftOp.getType(), arithm.getType());
		
		arithm.rightOp.accept(this, param); 	// VALUE[[rightOp]]
		cg.convert(arithm.rightOp.getType(), arithm.getType());
		
		cg.arithmetic(arithm.getType(), arithm.operator);
		
		return null;
	}
	
	@Override
	public Object visit(Comparison comp, Object param) {
		comp.leftOp.accept(this, param);		// VALUE[[leftOp]]
		cg.convert(comp.leftOp.getType(), comp.leftOp.getType().superType(comp.rightOp.getType()));
		
		comp.rightOp.accept(this, param);	// VALUE[[rightOp]]
		cg.convert(comp.rightOp.getType(), comp.rightOp.getType().superType(comp.leftOp.getType()));
		
		cg.comparison(comp.getType(), comp.operator);
		
		return null;
	}
	
	@Override
	public Object visit(Logical logic, Object param) {
		logic.leftOp.accept(this, param);	// VALUE[[leftOp]]
		cg.convert(logic.leftOp.getType(), logic.getType());
		
		logic.rightOp.accept(this, param);	// VALUE[[rightOp]]
		cg.convert(logic.rightOp.getType(), logic.getType());
		
		cg.logical(logic.operator);
		
		return null;
	}

	@Override
	public Object visit(Cast cast, Object param) {
		cast.expression.accept(this, param);	// VALUE[[expr]]
		cg.convert(cast.expression.getType(), cast.castType);
		
		return null;
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object param) {
		unaryNot.expression.accept(this, param);
		cg.not();

		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object param) {
		indexing.accept(addressCGVisitor, param);	// ADDRESS[[indexing]]
		cg.load(indexing.getType());
		
		return null;
	}

	@Override
	public Object visit(FieldAccess fieldAccess, Object param) {
		fieldAccess.accept(addressCGVisitor, param);	// ADDRESS[[fieldAccess]]
		cg.load(fieldAccess.getType());
		
		return null;
	}

}
