package visitors;

import java.util.LinkedList;
import java.util.List;

import ast.definitions.FunDefinition;
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
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.Type;
import ast.types.VoidType;

public class TypeCheckingVisitor extends AbstractVisitor {
	
	// Definitions
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		funDefinition.getType().accept(this, param);

		// A las sentencias de la función, le pasamos como parámetro el tipo de retorno de la funcion.
		FunctionType functionType = (FunctionType) funDefinition.getType();
		Type returnType = functionType.returnType;
		funDefinition.statements.forEach( (stm) -> stm.accept(this, returnType) );

		return null;
	}

	// Statements
	@Override
	public Object visit(Assignment assignment, Object param) {
		assignment.left.accept(this, param);
		assignment.right.accept(this, param);
		
		predicate(assignment.left.getLValue(), assignment, 
				"Semantical error: The left value of an assignment must be an lValue expression.");
		
		// predicate (assignment.right.getType().promotesTo(assignment.left.getType()) != null)
		assignment.left.setType( assignment.right.getType().promotesTo(assignment.left.getType()) );
		if(assignment.left.getType() == null)
			assignment.left.setType( new ErrorType(assignment, 
					"Semantical error: The expression '"+ assignment.right +"' can't be assisgned to '"+ assignment.left +
					"' because their types are not compatible "
					+ "(the right type doesn't promote to the left type).") );
		
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement, Object param) {
		ifStatement.condition.accept(this, param);
		
		//predicate (ifStatement.condition.getType().isLogical())
		if(! ifStatement.condition.getType().isLogical() && ! (ifStatement.condition.getType() instanceof ErrorType) )
			ifStatement.condition.setType(
					new ErrorType(ifStatement.condition, 
							"Semantical error: The if condition '"+ ifStatement.condition +"' is not logical."));
		
		ifStatement.ifBody.forEach( (stm) -> stm.accept(this, param));
		ifStatement.elseBody.forEach( (stm) -> stm.accept(this, param));
				
		return null;
	}
	
	@Override
	public Object visit(Read read, Object param) {
		read.expression.accept(this, param);

		predicate(read.expression.getLValue(), read, 
				"Semantical error: Read statements must receive an lValue expression.");
		
		return null;
	}
	
	@Override
	public Object visit(Return _return, Object param) {
		_return.expression.accept(this, param);
		
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
		
		return null;
	}

	@Override
	public Object visit(While _while, Object param) {
		_while.condition.accept(this, param);

		//predicate (_while.condition.getType().isLogical())
		if(! _while.condition.getType().isLogical() && ! (_while.condition.getType() instanceof ErrorType) )
			_while.condition.setType( new ErrorType(_while.condition, 
					"Semantical error: The while condition '"+ _while.condition +"' is not logical."));
		
		_while.body.forEach( (stm) -> stm.accept(this, param) );
		
		return null;
	}

	@Override
	public Object visit(Write write, Object param) {
		write.expression.accept(this, param);
		
		// predicate (write.expression.getType().isBuiltIn())
		if(! write.expression.getType().isBuiltIn() && ! (write.expression.getType() instanceof ErrorType) )
			write.expression.setType( new ErrorType(write.expression, 
					"Semantical error: The write expression '"+ write.expression +"' is not valid. "
						+ "Its type must be a simple type (char, int or real).") );	
		
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
					"Semantical error: At least one of the types of this expressions '" + arithmetic.leftOp +"', '"
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
					"Semantical error: At least one of the types of this expressions '" + comparison.leftOp +"', '"
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
					"Semantical error: At least one of the types of this expressions '" + logical.leftOp +"', '"
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

	// Statements & Expressions
	@Override
	public Object visit(Invocation invocation, Object param) {
		invocation.function.accept(this, param);
		invocation.arguments.forEach( (arg) -> arg.accept(this, param) );
		
		invocation.setLValue(false);

		// predicate (invocation.function.getType().parenthesis(argumentTypes) != null)
		List<Type> argumentTypes = new LinkedList<>();
		invocation.arguments.forEach( (arg) -> argumentTypes.add(arg.getType()) );
		
		invocation.setType(invocation.function.getType().parenthesis(argumentTypes));
		if(invocation.getType() == null)
			invocation.setType( new ErrorType(invocation.function, 
					"Semantical error: The invocation of the function '" + invocation.function + "' "
							+ "is not valid. The number of arguments or the type of those arguments isn't right.") );
		
		return null;
	}

}
