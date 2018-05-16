package visitors;

import java.util.LinkedList;
import java.util.List;

import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
import ast.expressions.Arithmetic;
import ast.expressions.Cast;
import ast.expressions.CharLiteral;
import ast.expressions.Comparison;
import ast.expressions.FieldAccess;
import ast.expressions.Indexing;
import ast.expressions.IntLiteral;
import ast.expressions.Logical;
import ast.expressions.RealLiteral;
import ast.expressions.TernaryOperator;
import ast.expressions.UnaryMinus;
import ast.expressions.UnaryNot;
import ast.expressions.Variable;
import ast.statements.Break;
import ast.statements.DoWhile;
import ast.statements.For;
import ast.statements.IfStatement;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.Statement;
import ast.statements.While;
import ast.statements.Write;
import ast.statementsAndExpressions.Assignment;
import ast.statementsAndExpressions.Invocation;
import ast.statementsAndExpressions.PostArithmetic;
import ast.statementsAndExpressions.PreArithmetic;
import ast.types.CharType;
import ast.types.ErrorType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.Type;
import ast.types.VoidType;

public class TypeCheckingVisitor extends AbstractVisitor {
	
	// Definitions
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		boolean hasReturn = false;
		boolean hasAStatementThatIsntLoopOrSwitchAndHasBreak = false;
		
		funDefinition.getType().accept(this, param);

		// A las sentencias de la función, le pasamos como parámetro el tipo de retorno de la funcion.
		FunctionType functionType = (FunctionType) funDefinition.getType();
		Type returnType = functionType.returnType;
		
		// Visitamos las sentencias de la función
		for (Statement stm : funDefinition.statements) {
			if((boolean) stm.accept(this, returnType))
				hasReturn = true;
			
			if(stm.getIsntLoopOrSwitchAndHasBreak())
				hasAStatementThatIsntLoopOrSwitchAndHasBreak = true;
		}

		// Si la función NO es void y no retorna un valor, mostramos un error
		if( !(functionType.returnType instanceof VoidType) && !hasReturn)
			new ErrorType(funDefinition, 
					"Flow control error: The function '" + funDefinition.getName() + "' isn't "
							+ "void and doens't have a return statement.");

		// Si hay alguna sentencia que no es un bucle o un switch y que tiene un break, mostramos un error
		if(hasAStatementThatIsntLoopOrSwitchAndHasBreak)
			new ErrorType(funDefinition, 
					"Flow control error: The function '" + funDefinition.getName() + "' has "
							+ "at least one statement that isn't a loop or a switch "
							+ "and has a break statement.");
		
		return null;
	}

	// Definitions y statements
	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		varDefinition.setAssignsValue(false);
		varDefinition.setIsntLoopOrSwitchAndHasBreak(false);
		
		varDefinition.getType().accept(this, param);

		// No tiene return
		return false;
	}

	// Statements
	@Override
	public Object visit(Assignment assignment, Object param) {
		assignment.left.accept(this, param);
		assignment.right.accept(this, param);
		
		assignment.setLValue(false);
		assignment.setAssignsValue(true);
		assignment.setIsntLoopOrSwitchAndHasBreak(false);
		
		predicate(assignment.left.getLValue(), assignment, 
				"Semantical error: The left value of an assignment must be an lValue expression.");
		
		// predicate (assignment.right.getType().promotesTo(assignment.left.getType()) != null)
		assignment.setType( assignment.right.getType().promotesTo(assignment.left.getType()) );
		if(assignment.getType() == null)
			assignment.setType( new ErrorType(assignment, 
					"Semantical error: The expression '"+ assignment.right +"' can't be assisgned to '"+ assignment.left +
					"' because their types are not compatible "
					+ "(the right type doesn't promote to the left type).") );
		
		// No tiene return
		return false;
	}

	@Override
	public Object visit(IfStatement ifStatement, Object param) {
		boolean ifBodyHasReturn = false, elseBodyHasReturn = false; 
		boolean ifBodyHasBreak = false, elseBodyHasBreak = false; 
		
		ifStatement.setAssignsValue(false);
		
		ifStatement.condition.accept(this, param);
		
		//predicate (ifStatement.condition.getType().isLogical())
		if(! ifStatement.condition.getType().isLogical() && ! (ifStatement.condition.getType() instanceof ErrorType) )
			ifStatement.condition.setType(
					new ErrorType(ifStatement.condition, 
							"Semantical error: The if condition '"+ ifStatement.condition +"' is not logical."));
		
		for (Statement stm : ifStatement.ifBody) {
			if((boolean) stm.accept(this, param))
				ifBodyHasReturn = true;
			
			if(stm.getIsntLoopOrSwitchAndHasBreak())
				ifBodyHasBreak = true;
		}

		for (Statement stm : ifStatement.elseBody) {
			if((boolean) stm.accept(this, param))
				elseBodyHasReturn = true;
			
			if(stm.getIsntLoopOrSwitchAndHasBreak())
				elseBodyHasBreak = true;
		}
		
		// Si en el ifBody o en el elseBOdy hay un stm que no es un bucle o un switch y que tiene break
		// entonces el ifStatement también tiene break
		ifStatement.setIsntLoopOrSwitchAndHasBreak(ifBodyHasBreak || elseBodyHasBreak);
				
		// Si en el ifBody hay return y en el elseBody hay return, el ifStatement tiene return
		return ifBodyHasReturn && elseBodyHasReturn;
	}
	
	@Override
	public Object visit(Read read, Object param) {
		read.expression.accept(this, param);

		read.setAssignsValue(true);
		read.setIsntLoopOrSwitchAndHasBreak(false);
		
		predicate(read.expression.getLValue(), read, 
				"Semantical error: Read statements must receive an lValue expression.");
		
		// No tiene return
		return false;
	}
	
	@Override
	public Object visit(Return _return, Object param) {
		_return.expression.accept(this, param);
		
		_return.setAssignsValue(false);
		_return.setIsntLoopOrSwitchAndHasBreak(false);
		
		Type functionReturnType = (Type) param;
		
		// Las funciones void no pueden retornar valor.
		if(functionReturnType instanceof VoidType)
			_return.expression.setType( new ErrorType(_return.expression, 
					"Semantical error: Void functions can't return any value.") );	
		
		// El tipo del return ha de coincidir con el tipo de retorno declarado en la definición de la función
		else{
			_return.expression.setType( _return.expression.getType().promotesTo(functionReturnType) );
			
			if(_return.expression.getType() == null)
				_return.expression.setType( new ErrorType(_return.expression, 
						"Semantical error: The return expression '"+ _return.expression +"' is not valid. "
								+ "Its type isn't compatible with the return type "
										+ "declared in the function definition: '" + functionReturnType +"'.") );
		}
		
		// Es un return, así que tiene return
		return true;
	}

	@Override
	public Object visit(While _while, Object param) {
		_while.setAssignsValue(false);
		_while.setIsntLoopOrSwitchAndHasBreak(false);
		
		_while.condition.accept(this, param);

		//predicate (_while.condition.getType().isLogical())
		if(! _while.condition.getType().isLogical() && ! (_while.condition.getType() instanceof ErrorType) )
			_while.condition.setType( new ErrorType(_while.condition, 
					"Semantical error: The while condition '"+ _while.condition +"' is not logical."));
		
		_while.body.forEach( (stm) -> stm.accept(this, param) );
		
		// No tiene return
		return false;
	}
	
	@Override
	public Object visit(DoWhile doWhile, Object param) {
		doWhile.setAssignsValue(false);
		doWhile.setIsntLoopOrSwitchAndHasBreak(false);
		
		doWhile.condition.accept(this, param);
		
		//predicate (doWhile.condition.getType().isLogical())
		if(! doWhile.condition.getType().isLogical() && ! (doWhile.condition.getType() instanceof ErrorType) )
			doWhile.condition.setType( new ErrorType(doWhile.condition, 
					"Semantical error: The do while condition '"+ doWhile.condition +"' is not logical."));
		
		doWhile.body.forEach( (stm) -> stm.accept(this, param) );
		
		// No tiene return
		return false;
	}
	
	@Override
	public Object visit(For _for, Object param) {
		_for.setAssignsValue(false);
		_for.setIsntLoopOrSwitchAndHasBreak(false);
		
		_for.initializationStatements.forEach( (stm) -> stm.accept(this, param) );
		_for.condition.accept(this, param);
		_for.incrementStatements.forEach( (stm) -> stm.accept(this, param) );
		
		//predicate (initializationStatementsi.assignsValue)
		for (Statement stm : _for.initializationStatements) {
			predicate(stm.getAssignsValue(), stm, 
					"Semantical error: The initialization statement '"+ stm +"' doesn't assign value.");
		}
		
		//predicate (_for.condition.getType().isLogical())
		if(! _for.condition.getType().isLogical() && ! (_for.condition.getType() instanceof ErrorType) )
			_for.condition.setType( new ErrorType(_for.condition, 
					"Semantical error: The for condition '"+ _for.condition +"' is not logical."));

		//predicate (incrementStatementsi.assignsValue)
		for (Statement stm : _for.incrementStatements) {
			predicate(stm.getAssignsValue(), stm, 
					"Semantical error: The increment statement '"+ stm +"' doesn't assign value.");
		}
				
		_for.body.forEach( (stm) -> stm.accept(this, param) );
		
		// No tiene return
		return false;
	}

	@Override
	public Object visit(Write write, Object param) {
		write.expression.accept(this, param);
		write.setIsntLoopOrSwitchAndHasBreak(false);
		
		// predicate (write.expression.getType().isBuiltIn())
		if(! write.expression.getType().isBuiltIn() && ! (write.expression.getType() instanceof ErrorType) )
			write.expression.setType( new ErrorType(write.expression, 
					"Semantical error: The write expression '"+ write.expression +"' is not valid. "
						+ "Its type must be a simple type (char, int or real).") );	
		
		// No tiene return
		return false;
	}
	
	@Override
	public Object visit(Break _break, Object param) {
		_break.setAssignsValue(false);
		_break.setIsntLoopOrSwitchAndHasBreak(true);
		
		// No tiene return
		return false;
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
					"Semantical error: At least one of the types of these expressions '" + arithmetic.leftOp +"', '"
							+ arithmetic.rightOp +"' can't be used in a arithmetic expression.") );
		
		return null;
	}

	@Override
	public Object visit(Cast cast, Object param) {
		cast.castType.accept(this, param);
		cast.expression.accept(this, param);
		
		cast.setLValue(false);
		
		// predicate (cast.expression.getType().canBeCast(cast.castType) != null)
		cast.setType(cast.expression.getType().canBeCast(cast.castType));
		if(cast.getType() == null)
			cast.setType( new ErrorType(cast, 
					"Semantical error: The expression '"+ cast.expression +"' "
							+ "can't be casted to the type '"+ cast.castType +"'.") );
		
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
					"Semantical error: At least one of the types of these expressions '" + comparison.leftOp +"', '"
							+ comparison.rightOp +"' can't be used in a comparison expression.") );
		
		return null;
	}

	@Override
	public Object visit(FieldAccess fieldAccess, Object param) {
		fieldAccess.leftOp.accept(this, param);
		
		fieldAccess.setLValue(true);
		
		// predicate (fieldAccess.leftOp.getType().dot(fieldAccess.name) != null)
		fieldAccess.setType(fieldAccess.leftOp.getType().dot(fieldAccess.name));
		if(fieldAccess.getType() == null)
			fieldAccess.setType( new ErrorType(fieldAccess, 
					"Semantical error: The expression '"+ fieldAccess.leftOp +"' isn't a struct, "
							+ "or is a struct but the field '"+ fieldAccess.name +"' isn't defined inside it.") );
		
		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object param) {
		indexing.leftOp.accept(this, param);
		indexing.rightOp.accept(this, param);
		
		indexing.setLValue(true);
		
		// predicate (indexing.leftOp.getType().squareBrackets(indexing.rightOp.getType()) != null)
		indexing.setType(indexing.leftOp.getType().squareBrackets(indexing.rightOp.getType()));
		if(indexing.getType() == null)
			indexing.setType( new ErrorType(indexing, 
					"Semantical error: The expression '"+ indexing.leftOp +"' isn't an array, "
							+ "or is an array but the expression '"+ indexing.rightOp +"' "
									+ "isn't of a valid type for accesing the array.") );

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

		//predicate (logical.leftOp.getType().logical(logical.rightOp.getType()) != null)
		logical.setType( logical.leftOp.getType().logical( logical.rightOp.getType() ) );
		if(logical.getType() == null)
			logical.setType( new ErrorType(logical, 
					"Semantical error: At least one of the types of these expressions '" + logical.leftOp +"', '"
							+ logical.rightOp +"' can't be used in a logical expression.") );
		
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
					"Semantical error: The type of this expression '" + unaryMinus.expression + "' "
							+ "can't be used in a Unary Minus expression.") );
		
		return null;
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object param) {
		unaryNot.expression.accept(this, param);
		
		unaryNot.setLValue(false);
		
		//predicate (unaryNot.expression.getType().logical() != null)
		unaryNot.setType(unaryNot.expression.getType().logical());
		if(unaryNot.getType() == null)
			unaryNot.setType( new ErrorType(unaryNot.expression, 
					"Semantical error: The type of this expression '" + unaryNot.expression + "' "
							+ "can't be used in a Unary Not expression (is not logical).") );
		
		return null;
	}

	@Override
	public Object visit(Variable variable, Object param) {
		variable.setLValue(true);
		variable.setType(variable.definition.getType());
		
		return null;
	}

	@Override
	public Object visit(TernaryOperator ternaryOperator, Object param) {
		ternaryOperator.condition.accept(this, param);
		ternaryOperator.trueExpression.accept(this, param);
		ternaryOperator.falseExpression.accept(this, param);
		
		ternaryOperator.setLValue(false);
		
		// predicate (condition.type.isLogical())
		if(! ternaryOperator.condition.getType().isLogical() && ! (ternaryOperator.condition.getType() instanceof ErrorType) )
			ternaryOperator.setType( new ErrorType(ternaryOperator.condition, 
					"Semantical error: The ternary operator condition '"+ ternaryOperator.condition +"' is not logical."));
		
		// predicate (trueExpression.type.rightfulSuperType(falseExpression.type) != null)
		ternaryOperator.setType( ternaryOperator.trueExpression.getType().rightfulSuperType(ternaryOperator.falseExpression.getType()) );
		if(ternaryOperator.getType() == null)
			ternaryOperator.setType( new ErrorType(ternaryOperator, 
					"Semantical error: At least one of the types of these expressions '" + ternaryOperator.trueExpression +"', '"
							+ ternaryOperator.falseExpression +"' can't be used in a ternary operator expression.") );
		
		return null;
	}

	// Statements & Expressions
	@Override
	public Object visit(Invocation invocation, Object param) {
		invocation.function.accept(this, param);
		invocation.arguments.forEach( (arg) -> arg.accept(this, param) );
		
		invocation.setLValue(false);
		invocation.setAssignsValue(false);
		invocation.setIsntLoopOrSwitchAndHasBreak(false);

		// predicate (invocation.function.getType().parenthesis(argumentTypes) != null)
		List<Type> argumentTypes = new LinkedList<>();
		invocation.arguments.forEach( (arg) -> argumentTypes.add(arg.getType()) );
		
		invocation.setType(invocation.function.getType().parenthesis(argumentTypes));
		if(invocation.getType() == null)
			invocation.setType( new ErrorType(invocation.function, 
					"Semantical error: The invocation of the function '" + invocation.function + "' "
							+ "is not valid. The number of arguments or the type of those arguments isn't right.") );
		
		// No tiene return
		return false;
	}

	@Override
	public Object visit(PreArithmetic preArithmetic, Object param) {
		preArithmetic.expression.accept(this, param);

		preArithmetic.setLValue(false);
		preArithmetic.setAssignsValue(true);
		preArithmetic.setIsntLoopOrSwitchAndHasBreak(false);
		
		// predicate (preArithmetic.expression.getLValue() == true)		
		if(preArithmetic.expression.getLValue() == false)
			preArithmetic.setType( new ErrorType(preArithmetic.expression, 
					"Semantical error: The expression of an Pre Arithmetic element must be an lValue expression.") );
		
		// predicate (preArithmetic.expression.getType().pArithmetic() != null)
		preArithmetic.setType(preArithmetic.expression.getType().pArithmetic());
		if(preArithmetic.getType() == null)
			preArithmetic.setType( new ErrorType(preArithmetic.expression, 
					"Semantical error: The type of this expression '" + preArithmetic.expression + "' "
							+ "can't be used in a Pre Arithmetic element.") );
		
		// No tiene return
		return false;
	}

	@Override
	public Object visit(PostArithmetic postArithmetic, Object param) {
		postArithmetic.expression.accept(this, param);

		postArithmetic.setLValue(false);
		postArithmetic.setAssignsValue(true);
		postArithmetic.setIsntLoopOrSwitchAndHasBreak(false);
		
		// predicate (postArithmetic.expression.getLValue() == true)		
		if(postArithmetic.expression.getLValue() == false)
			postArithmetic.setType( new ErrorType(postArithmetic.expression, 
					"Semantical error: The expression of an Post Arithmetic element must be an lValue expression.") );
		
		// predicate (postArithmetic.expression.getType().pArithmetic() != null)
		postArithmetic.setType(postArithmetic.expression.getType().pArithmetic());
		if(postArithmetic.getType() == null)
			postArithmetic.setType( new ErrorType(postArithmetic.expression, 
					"Semantical error: The type of this expression '" + postArithmetic.expression + "' "
							+ "can't be used in a Post Arithmetic element.") );
		
		// No tiene return
		return false;
	}

}
