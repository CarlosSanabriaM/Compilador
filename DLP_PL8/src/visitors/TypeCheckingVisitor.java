package visitors;

import ast.expressions.Arithmetic;
import ast.expressions.Cast;
import ast.expressions.CharLiteral;
import ast.expressions.Comparison;
import ast.expressions.FieldAccess;
import ast.expressions.Indexing;
import ast.expressions.IntLiteral;
import ast.expressions.Logical;
import ast.expressions.RealLiteral;
import ast.expressions.UnaryMinus;
import ast.expressions.UnaryNot;
import ast.expressions.Variable;
import ast.statements.Assignment;
import ast.statements.Read;
import ast.statementsAndExpressions.Invocation;
import ast.types.CharType;
import ast.types.IntType;
import ast.types.RealType;

public class TypeCheckingVisitor extends AbstractVisitor {
	
	// Statements
	@Override
	public Object visit(Assignment assignment, Object param) {
		assignment.left.accept(this, param);
		assignment.right.accept(this, param);
		
		predicate(assignment.left.getLValue(), assignment, 
				"Semantical error: The left value of an assignment must be an lValue expression.");
		
		return null;
	}

	@Override
	public Object visit(Read read, Object param) {
		read.expression.accept(this, param);

		predicate(read.expression.getLValue(), read, 
				"Semantical error: Read statements must receive an lValue expression.");
		
		return null;
	}
	
	// Expressions
	@Override
	public Object visit(Arithmetic arithmetic, Object param) {
		arithmetic.leftOp.accept(this, param);
		arithmetic.rightOp.accept(this, param);
		
		arithmetic.setLValue(false);
		
		return null;
	}

	@Override
	public Object visit(Cast cast, Object param) {
		cast.castType.accept(this, param);
		cast.expression.accept(this, param);
		
		cast.setLValue(false);
		
		return null;
	}

	@Override
	public Object visit(CharLiteral charLiteral, Object param) {
		charLiteral.setLValue(false);
		charLiteral.setType(CharType.getInstance());
		
		return null;
	}

	@Override
	public Object visit(Comparison comparison, Object param) {
		comparison.leftOp.accept(this, param);
		comparison.rightOp.accept(this, param);
		
		comparison.setLValue(false);
		
		return null;
	}

	@Override
	public Object visit(FieldAccess fieldAccess, Object param) {
		fieldAccess.leftOp.accept(this, param);
		
		fieldAccess.setLValue(true);
		
		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object param) {
		indexing.leftOp.accept(this, param);
		indexing.rightOp.accept(this, param);
		
		indexing.setLValue(true);
		
		return null;
	}

	@Override
	public Object visit(IntLiteral intLiteral, Object param) {
		intLiteral.setLValue(false);
		intLiteral.setType(IntType.getInstance());
		
		return null;
	}

	@Override
	public Object visit(Logical logical, Object param) {
		logical.leftOp.accept(this, param);
		logical.rightOp.accept(this, param);
		
		logical.setLValue(false);

		return null;
	}

	@Override
	public Object visit(RealLiteral realLiteral, Object param) {
		realLiteral.setLValue(false);
		realLiteral.setType(RealType.getInstance());
		
		return null;
	}

	@Override
	public Object visit(UnaryMinus unaryMinus, Object param) {
		unaryMinus.expression.accept(this, param);
		
		unaryMinus.setLValue(false);
		
		return null;
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object param) {
		unaryNot.expression.accept(this, param);
		
		unaryNot.setLValue(false);
		
		return null;
	}

	@Override
	public Object visit(Variable variable, Object param) {
		variable.setLValue(true);
		variable.setType(variable.definition.getType());
		
		return null;
	}

	// Statements & Expressions
	@Override
	public Object visit(Invocation invocation, Object param) {
		invocation.function.accept(this, param);
		invocation.arguments.forEach( (arg) -> arg.accept(this, param) );
		
		invocation.setLValue(false);
		
		return null;
	}

}
