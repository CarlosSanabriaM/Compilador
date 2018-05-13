package visitors.codeGeneration;

import ast.expressions.Arithmetic;
import ast.expressions.Cast;
import ast.expressions.CharLiteral;
import ast.expressions.Comparison;
import ast.expressions.Expression;
import ast.expressions.FieldAccess;
import ast.expressions.Indexing;
import ast.expressions.IntLiteral;
import ast.expressions.Logical;
import ast.expressions.RealLiteral;
import ast.expressions.UnaryMinus;
import ast.expressions.UnaryNot;
import ast.expressions.Variable;
import ast.statementsAndExpressions.Invocation;
import ast.statementsAndExpressions.PostArithmetic;
import ast.statementsAndExpressions.PreArithmetic;
import ast.types.CharType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.Type;
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
		
		cg.comparison(comp.leftOp.getType().superType(comp.rightOp.getType()), comp.operator);
		
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
		unaryNot.expression.accept(this, param);	// VALUE[[expr]]
		cg.convert(unaryNot.expression.getType(), unaryNot.getType());
		cg.not();

		return null;
	}

	@Override
	public Object visit(UnaryMinus unaryMinus, Object param) {
		unaryMinus.expression.accept(this, param);	// VALUE[[expr]]

		cg.convert(unaryMinus.expression.getType(), unaryMinus.getType());
		
		// Multiplicamos la expresion por -1
		cg.push(-1);
		cg.convert(IntType.getInstance(), unaryMinus.getType());
		cg.mul(unaryMinus.getType());
		
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

	@Override
	public Object visit(Invocation invocation, Object param) {
		int i = 0;
		
		for (Expression arg : invocation.arguments) {
			arg.accept(this, param);	// VALUE[[argi]]
			
			FunctionType functionType = (FunctionType) invocation.function.getType();
			cg.convert(arg.getType(), functionType.param.get(i++).getType());
		}
		
		cg.call(invocation.function.name);
		
		return null;
	}

	@Override
	public Object visit(PreArithmetic preArithmetic, Object param) {
		Type type = preArithmetic.expression.getType() instanceof CharType ? IntType.getInstance() : preArithmetic.getType();
		
		preArithmetic.expression.accept(addressCGVisitor, param);		// ADDRESS[[expr]] 
		preArithmetic.expression.accept(this, param);				// VALUE[[expr]]
		
		// Si la expresion es char, hay que convertirla a entero primero, para sumarle 1
		if(preArithmetic.expression.getType() instanceof CharType)
			cg.convert(CharType.getInstance(), IntType.getInstance());
		
		cg.push(1);
		cg.convert(IntType.getInstance(), type);
		 
		cg.pArithmetic(type, preArithmetic.operator);
		
		// Si la expresion es char, despues de convertirla en entero y sumarle 1, la volvemos a convertir en char
		if(preArithmetic.expression.getType() instanceof CharType)
			cg.convert(IntType.getInstance(), CharType.getInstance());
		
		cg.store(preArithmetic.getType());
		
		preArithmetic.expression.accept(this, param);				// VALUE[[expr]] 
		
		return null;
	}

	@Override
	public Object visit(PostArithmetic postArithmetic, Object param) {
		Type type = postArithmetic.expression.getType() instanceof CharType ? IntType.getInstance() : postArithmetic.getType();
		
		postArithmetic.expression.accept(this, param);				// VALUE[[expr]] 
		postArithmetic.expression.accept(addressCGVisitor, param);	// ADDRESS[[expr]] 
		postArithmetic.expression.accept(this, param);				// VALUE[[expr]]
		
		// Si la expresion es char, hay que convertirla a entero primero, para sumarle 1
		if(postArithmetic.expression.getType() instanceof CharType)
			cg.convert(CharType.getInstance(), IntType.getInstance());
		
		cg.push(1);
		cg.convert(IntType.getInstance(), type);
		 
		cg.pArithmetic(type, postArithmetic.operator);
		
		// Si la expresion es char, despues de convertirla en entero y sumarle 1, la volvemos a convertir en char
		if(postArithmetic.expression.getType() instanceof CharType)
			cg.convert(IntType.getInstance(), CharType.getInstance());
		
		cg.store(postArithmetic.getType());
		
		return null;
	}

}
