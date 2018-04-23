package ast.types;

import java.util.List;

import ast.ASTNode;

public interface Type extends ASTNode{

	/**
	 * Returns true if the type who calls the function is a built-in type (CharType, IntType or RealType).
	 */
	boolean isBuiltIn();

	/**
	 * Returns true if the type who calls the function is a logical type
	 * (can be used in a while or an if-else condition, for example)
	 */
	boolean isLogical();
	
	/**
	 * If the type who calls the function and the type passed as parameter
	 * are compatible to do an arithmetic operation (+, -, /, *, %) 
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type arithmetic(Type type);
	
	/**
	 * If the type who calls the function can be used to do an arithmetic 
	 * operation with an single operator (for example, UnaryMinus -), 
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type arithmetic();

	/**
	 * If the type who calls the function and the type passed as parameter
	 * are compatible to do an comparison operation  (>, >=, <, <=, ==, !=)
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type comparison(Type type);
	
	/**
	 * If the type who calls the function and the type passed as parameter
	 * are compatible to do an logical operation (&&, ||)
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type logical(Type type);
	
	/**
	 * If the type who calls the function can be used to do an logical 
	 * operation with an single operator (for example, UnaryNot !), 
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type logical();

	/**
	 * If the type who calls the function promotes to the type passed as parameter
	 * (for example, INT promotes to REAL, but REAL doesnt promote to INT)
	 * returns the resulting type that would have that. If not, returns null.
	 */
	Type promotesTo(Type type);

	/**
	 * If the type who calls the function can be casted to the type passed as parameter 
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type canBeCast(Type type);
	
	/**
	 * If the type who calls the function has a field with the name passed as parameter, 
	 * returns the type of that field. If not, returns null.
	 */
	Type dot(String fieldName);
	
	/**
	 * If the type who calls the function can be accesed with the type passed as parameter
	 * using the brackets operator (for example, v[1] or w[7][4+i])
	 * returns the resulting type that would have that operation. If not, returns null.
	 */
	Type squareBrackets(Type type);
	
	/**
	 * If the type who calls the function is FunctionType and the list of types passed as parameter 
	 * promotes to the list of types declared in the function definition,
	 * returns the return type of the function. If not, returns null.
	 */
	Type parenthesis(List<Type> types);
	
	/**
	 * If the type who calls the function and the type passed as parameter
	 * are compatible, returns the greater of the two types. If not, returns null.
	 */
	Type superType(Type type);
	
	
	/**
	 * Returns the number of bytes occupied by the type in memory
	 */
	int numBytes();
	
	
	/**
	 * Returns the suffix of this type
	 */
	char suffix();
	
}
