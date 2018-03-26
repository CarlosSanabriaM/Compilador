package visitors;

import ast.ASTNode;
import ast.Program;
import ast.definitions.Definition;
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
import ast.types.ArrayType;
import ast.types.CharType;
import ast.types.ErrorType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.RecordField;
import ast.types.RecordType;
import ast.types.VoidType;

/**
 * Recorre todos los nodos, sin hacer nada específico.
 * @author carlos
 *
 */
public abstract class AbstractVisitor implements Visitor {

	/**
	 * If the given predicate is false, creates a new ErrorType with the given node and message
	 */
	protected void predicate(boolean predicate, ASTNode node, String errorMessage) {
		if( !predicate )
			new ErrorType(node, errorMessage);
	}
	
	// Program
	@Override
	public Object visit(Program program, Object param) {
		for (Definition definition: program.definitions)
			definition.accept(this, param);
		
		return null;
	}

	// Definitions
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		funDefinition.getType().accept(this, param);
		funDefinition.statements.forEach( (stm) -> stm.accept(this, param) );

		return null;
	}

	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		varDefinition.getType().accept(this, param);
		
		return null;
	}

	// Statements
	@Override
	public Object visit(Assignment assignment, Object param) {
		assignment.left.accept(this, param);
		assignment.right.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement, Object param) {
		ifStatement.condition.accept(this, param);
		ifStatement.ifBody.forEach( (stm) -> stm.accept(this, param));
		ifStatement.elseBody.forEach( (stm) -> stm.accept(this, param));
		
		return null;
	}

	@Override
	public Object visit(Read read, Object param) {
		read.expression.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(Return _return, Object param) {
		_return.expression.accept(this, param);

		return null;
	}

	@Override
	public Object visit(While _while, Object param) {
		_while.condition.accept(this, param);
		_while.body.forEach( (stm) -> stm.accept(this, param) );

		return null;
	}

	@Override
	public Object visit(Write write, Object param) {
		write.expression.accept(this, param);

		return null;
	}

	// Expressions
	@Override
	public Object visit(Arithmetic arithmetic, Object param) {
		arithmetic.leftOp.accept(this, param);
		arithmetic.rightOp.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(Cast cast, Object param) {
		cast.castType.accept(this, param);
		cast.expression.accept(this, param);
			
		return null;
	}

	@Override
	public Object visit(CharLiteral charLiteral, Object param) {		
		return null;
	}

	@Override
	public Object visit(Comparison comparison, Object param) {
		comparison.leftOp.accept(this, param);
		comparison.rightOp.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(FieldAccess fieldAccess, Object param) {
		fieldAccess.leftOp.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object param) {
		indexing.leftOp.accept(this, param);
		indexing.rightOp.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(IntLiteral intLiteral, Object param) {		
		return null;
	}

	@Override
	public Object visit(Logical logical, Object param) {
		logical.leftOp.accept(this, param);
		logical.rightOp.accept(this, param);

		return null;
	}

	@Override
	public Object visit(RealLiteral realLiteral, Object param) {		
		return null;
	}

	@Override
	public Object visit(UnaryMinus unaryMinus, Object param) {
		unaryMinus.expression.accept(this, param);
				
		return null;
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object param) {
		unaryNot.expression.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(Variable variable, Object param) {		
		return null;
	}

	// Statements & Expressions
	@Override
	public Object visit(Invocation invocation, Object param) {
		invocation.function.accept(this, param);
		invocation.arguments.forEach( (arg) -> arg.accept(this, param) );
		
		return null;
	}

	// Types
	@Override
	public Object visit(ArrayType arrayType, Object param) {
		arrayType.of.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(CharType charType, Object param) {
		return null;
	}

	@Override
	public Object visit(FunctionType functionType, Object param) {
		functionType.param.forEach( (p) -> p.accept(this, param) );
		functionType.returnType.accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(IntType intType, Object param) {
		return null;
	}

	@Override
	public Object visit(RealType realType, Object param) {		
		return null;
	}

	@Override
	public Object visit(RecordField recordField, Object param) {
		recordField.getType().accept(this, param);

		return null;
	}

	@Override
	public Object visit(RecordType recordType, Object param) {
		recordType.fields.forEach( (rField) -> rField.accept(this, param) );
		
		return null;
	}

	@Override
	public Object visit(VoidType voidType, Object param) {
		return null;
	}

	// Error type
	@Override
	public Object visit(ErrorType errorType, Object param) {
		return null;
	}

}
