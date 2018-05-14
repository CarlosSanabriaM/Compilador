package visitors.codeGeneration;

import ast.Program;
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
import ast.statements.IfStatement;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.While;
import ast.statements.Write;
import ast.statementsAndExpressions.Assignment;
import ast.statementsAndExpressions.Invocation;
import ast.statementsAndExpressions.PostArithmetic;
import ast.statementsAndExpressions.PreArithmetic;
import ast.types.ArrayType;
import ast.types.CharType;
import ast.types.ErrorType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.RecordField;
import ast.types.RecordType;
import ast.types.VoidType;
import visitors.Visitor;

public abstract class AbstractCGVisitor implements Visitor {

	private static final String ERROR_MESSAGE = "Code generation template NOT defined";
	
	@Override
	public Object visit(Program program, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Assignment assignment, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(IfStatement ifStatement, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Read read, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Return _return, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(While _while, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Write write, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Arithmetic arithmetic, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Cast cast, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(CharLiteral charLiteral, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Comparison comparison, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(FieldAccess fieldAccess, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Indexing indexing, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(IntLiteral intLiteral, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Logical logical, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(RealLiteral realLiteral, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(UnaryMinus unaryMinus, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Variable variable, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}
	
	@Override
	public Object visit(TernaryOperator ternaryOperator, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(Invocation invocation, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(PreArithmetic preArithmetic, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(PostArithmetic postArithmetic, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(ArrayType arrayType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(CharType charType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(FunctionType functionType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(IntType intType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(RealType realType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(RecordField recordField, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(RecordType recordType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(VoidType voidType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

	@Override
	public Object visit(ErrorType errorType, Object param) {
		throw new IllegalStateException(ERROR_MESSAGE);
	}

}
