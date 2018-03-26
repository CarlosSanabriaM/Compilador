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
import ast.statements.IfStatement;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.While;
import ast.statements.Write;
import ast.statementsAndExpressions.Invocation;
import ast.types.CharType;
import ast.types.ErrorType;
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
		//TODO
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement, Object param) {
		ifStatement.condition.accept(this, param);
		ifStatement.ifBody.forEach( (stm) -> stm.accept(this, param));
		ifStatement.elseBody.forEach( (stm) -> stm.accept(this, param));
		
		//predicate (ifStatement.condition.getType().isLogical())
		if(! ifStatement.condition.getType().isLogical()) {
			ifStatement.condition.setType(
					new ErrorType(ifStatement.condition, 
							"The if condition '"+ ifStatement.condition +"' is not logical."));
		}
				
		return null;
	}
	
	@Override
	public Object visit(Read read, Object param) {
		read.expression.accept(this, param);

		predicate(read.expression.getLValue(), read, 
				"Semantical error: Read statements must receive an lValue expression.");
		//TODO
		return null;
	}
	
	@Override
	public Object visit(Return _return, Object param) {
		_return.expression.accept(this, param);
		//TODO
		return null;
	}

	@Override
	public Object visit(While _while, Object param) {
		_while.condition.accept(this, param);
		_while.body.forEach( (stm) -> stm.accept(this, param) );

		//predicate (_while.condition.getType().isLogical())
		if(! _while.condition.getType().isLogical()) {
			_while.condition.setType(
					new ErrorType(_while.condition, 
							"The while condition '"+ _while.condition +"' is not logical."));
		}
		
		return null;
	}

	@Override
	public Object visit(Write write, Object param) {
		write.expression.accept(this, param);
		//TODO
		return null;
	}
	
	// Expressions
	@Override
	public Object visit(Arithmetic arithmetic, Object param) {
		arithmetic.leftOp.accept(this, param);
		arithmetic.rightOp.accept(this, param);
		
		arithmetic.setLValue(false);
		
		//predicate (arithmetic.leftOp.getType().arithmetic(arithmetic.rightOp.getType()) != null)
		arithmetic.setType( arithmetic.leftOp.getType().arithmetic( arithmetic.rightOp.getType() ) );
		if(arithmetic.getType() == null)
			arithmetic.setType( new ErrorType(arithmetic, 
					"At least one of the types of this expressions '" + arithmetic.leftOp +"', '"
							+ arithmetic.rightOp +"' can't be used in a arithmetic expression.") );
		
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
		
		// predicate (comparison.leftOp.getType().comparison(comparison.rightOp.getType()) != null)
		comparison.setType( comparison.leftOp.getType().comparison(comparison.rightOp.getType()) );
		if(comparison.getType() == null)
			comparison.setType( new ErrorType(comparison, 
					"At least one of the types of this expressions '" + comparison.leftOp +"', '"
							+ comparison.rightOp +"' can't be used in a comparison expression.") );
		
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
		
		// predicate (unaryMinus.expression.getType().arithmetic() != null)
		unaryMinus.setType(unaryMinus.expression.getType().arithmetic());
		if(unaryMinus.getType() == null)
			unaryMinus.setType( new ErrorType(unaryMinus.expression, 
					"The type of this expression '" + unaryMinus.expression + "' can't be used in a Unary Minus expression.") );
		
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
