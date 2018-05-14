package visitors;

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
import ast.statements.DoWhile;
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

public interface Visitor {
	
	// Program
	Object visit(Program program, Object param);
	
	// Definitions
	Object visit(FunDefinition funDefinition, Object param);
	Object visit(VarDefinition varDefinition, Object param);
	
	// Statements
	Object visit(Assignment assignment, Object param);
	Object visit(IfStatement ifStatement, Object param);
	Object visit(Read read, Object param);
	Object visit(Return _return, Object param);
	Object visit(While _while, Object param);
	Object visit(DoWhile doWhile, Object param);
	Object visit(Write write, Object param);
	
	// Expressions
	Object visit(Arithmetic arithmetic, Object param);
	Object visit(Cast cast, Object param);
	Object visit(CharLiteral charLiteral, Object param);
	Object visit(Comparison comparison, Object param);
	Object visit(FieldAccess fieldAccess, Object param);
	Object visit(Indexing indexing, Object param);
	Object visit(IntLiteral intLiteral, Object param);
	Object visit(Logical logical, Object param);
	Object visit(RealLiteral realLiteral, Object param);
	Object visit(UnaryMinus unaryMinus, Object param);
	Object visit(UnaryNot unaryNot, Object param);
	Object visit(Variable variable, Object param);
	Object visit(TernaryOperator ternaryOperator, Object param);
	
	// Statements & Expressions
	Object visit(Invocation invocation, Object param);
	Object visit(PreArithmetic preArithmetic, Object param);
	Object visit(PostArithmetic postArithmetic, Object param);
	
	// Types
	Object visit(ArrayType arrayType, Object param);
	Object visit(CharType charType, Object param);
	Object visit(FunctionType functionType, Object param);
	Object visit(IntType intType, Object param);
	Object visit(RealType realType, Object param);
	Object visit(RecordField recordField, Object param);
	Object visit(RecordType recordType, Object param);
	Object visit(VoidType voidType, Object param);
	
	// Error type
	Object visit(ErrorType errorType, Object param);
	
}
