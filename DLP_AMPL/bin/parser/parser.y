%{
// * Declaraciones de código Java
// * Se sitúan al comienzo del archivo generado
// * El package lo añade yacc si utilizamos la opción -Jpackage
import scanner.Scanner;
import java.io.Reader;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import ast.*;
import ast.definitions.*;
import ast.expressions.*;
import ast.statements.*;
import ast.statementsAndExpressions.*;
import ast.types.*;
%}

// * Declaraciones Yacc
//Aqui declaramos todos los tokens
%token INT_CONSTANT
%token REAL_CONSTANT
%token CHAR_CONSTANT
%token INPUT
%token PRINT
%token PRINTLN
%token DEF
%token WHILE
%token IF
%token ELSE
%token INT
%token DOUBLE
%token CHAR
%token STRUCT
%token RETURN
%token VOID
%token ID
%token MAIN
%token DO
%token FOR


// ** Tokens con prioridades (los de mas abajo son mas prioritarios) **
%nonassoc MENOR_QUE_ELSE
%nonassoc ELSE

%nonassoc MENOR_QUE_COMA
%nonassoc ','

// Igual es siempre el que menos prioridad tiene
%right '=' PLUS_EQUAL MINUS_EQUAL MUL_EQUAL DIV_EQUAL MOD_EQUAL
%right TERNARY_OPERATOR '?'

%left AND OR XOR
%left '>' GREATER_OR_EQUAL '<' LESS_OR_EQUAL DISTINCT EQUALS
%left '+' '-'
%left '*' '/' '%'
%nonassoc '!' UNARY_MINUS PRE_ARITHMETIC
%nonassoc PLUS_PLUS MINUS_MINUS


%nonassoc CAST
%left '.'
%nonassoc '[' ']'

// Parentesis es siempre el que más prioridad tiene
%nonassoc PARENTESIS


%%
// * Gramática y acciones Yacc

// La funcion main al final del programa es OBLIGATORIA
program: _program main												{
																		List<Definition> definitions = (List<Definition>) $1;
																		Definition main = (Definition) $2;
																		definitions.add(main);
																		ast = new Program(scanner.getLine(), scanner.getColumn(), definitions);																			
																	}
		;
		
_program: 															{$$ = new LinkedList<Definition>();}
		| _program variable_definition								{List<Definition> definitions = (List<Definition>) $1; definitions.addAll((List<Definition>) $2); $$ = definitions;}
		| _program function_definition								{List<Definition> definitions = (List<Definition>) $1; definitions.add((Definition) $2); $$ = definitions;}
		;

main: DEF {funDefTempLine = scanner.getLine();} MAIN '(' ')' ':' VOID '{' function_body '}'					
																	{
																		FunctionType functionType = new FunctionType(funDefTempLine, 1, new LinkedList<VarDefinition>(), VoidType.getInstance());
																		$$ = new FunDefinition(funDefTempLine, 1, "main", functionType, (List<Statement>) $9);
																	}
	;

// Definicion de variables
variable_definition: variables ':' type ';'							{	// Una definicion de variable puede tener varias variables, y tenemos que dividirlo en varias VarDefinition
																		List<VarDefinition> varDefinitions = new LinkedList<VarDefinition>();
																		
																		List<String> vars = (List<String>) $1;
																		for(String var : vars){
																			VarDefinition varDef = new VarDefinition(scanner.getLine(), scanner.getColumn(), var, (Type) $3);
																			varDefinitions.add(varDef);
																		}
																		$$ = varDefinitions;
																	}
		;

variables: ID														{	
																		List<String> identifiers = new LinkedList<String>();
																		identifiers.add( (String) $1 );
																		$$ = identifiers;
																	}
		| variables ',' ID											{	
																		List<String> identifiers = (List<String>) $1;
																		String identifier = (String) $3;
																		
																		// Si el identificador ya está en la lista, es un error semántico (lo detectamos en esta fase que es más fácil). 
																		if(identifiers.contains(identifier))
																			new ErrorType(scanner.getLine(), scanner.getColumn(), 
																				"Semantical error: The identifier '"+ identifier +"' has already been used in the same variable definition.");
																		
																		identifiers.add( identifier );
																		$$ = identifiers;
																	}
		;

	
// Definicion de funciones
function_definition: DEF {funDefTempLine = scanner.getLine();} ID '(' parameters ')' ':' return_type '{' function_body '}' 		
																	
																	{
																		FunctionType functionType = new FunctionType(funDefTempLine, 1, (List<VarDefinition>) $5, (Type) $8);
																		$$ = new FunDefinition(funDefTempLine, 1, (String) $3, functionType, (List<Statement>) $10);
																	}
		;
																	// parameters es un List<VarDefinition>
parameters: 															{$$ = new LinkedList<VarDefinition>();}
		| _parameters												{$$ = (List<VarDefinition>) $1;}
		;
																	// _parameters es un List<VarDefinition>
_parameters: parameter												{List<VarDefinition> parameters = new LinkedList<VarDefinition>(); parameters.add((VarDefinition) $1); $$ = parameters;}
		| _parameters ',' parameter									{List<VarDefinition> parameters = (List<VarDefinition>) $1; parameters.add((VarDefinition) $3); $$ = parameters;}
		;
																	// Un parameter es un VarDefinition y tiene scope local.
parameter: ID ':' simple_type										{$$ = new VarDefinition(scanner.getLine(), scanner.getColumn(), (String) $1, (Type) $3);}				
		;

return_type: simple_type												{$$ = (Type) $1;}
		| VOID														{$$ = VoidType.getInstance();}
		;

																	// VarDefinition es un Statement (implementa la interfaz Statement)
																	// esto devuelve un List<Statement> en los cuatro casos
function_body: 														{$$ = new LinkedList<Statement>();}
		| variable_definitions statements 							{	
																		List<Statement> varDefinitions = (List<Statement>) $1; List<Statement> statements = (List<Statement>) $2; 
																		varDefinitions.addAll(statements); // Añadimos los statements al final de la lista de varDefinitions
																		$$ = varDefinitions; 
																	}
		| variable_definitions										{$$ = (List<Statement>) $1;}
		| statements													{$$ = (List<Statement>) $1;}
		;
																	// variable_definition es un List<VarDefinition>, no un unico VarDefinition
variable_definitions: variable_definition							{List<VarDefinition> varDefinitions = new LinkedList<VarDefinition>(); varDefinitions.addAll((List<VarDefinition>) $1); $$ = varDefinitions;}
		| variable_definitions variable_definition					{List<VarDefinition> varDefinitions = (List<VarDefinition>) $1; varDefinitions.addAll((List<VarDefinition>) $2); $$ = varDefinitions;}
		;
																	// statements es un List<Statement>, no un unico Statement
statements: statement												{List<Statement> statements = new LinkedList<Statement>(); statements.addAll((List<Statement>) $1); $$ = statements;}
		| statements statement										{List<Statement> statements = (List<Statement>) $1; statements.addAll((List<Statement>) $2); $$ = statements;}
		;

// ~~~~~~~~~~~ Tipos de sentencias ~~~~~~~~~~~
statement: statement_without_semicolon ';'							{$$ = (List<Statement>) $1;}
		| while														{$$ = asStatementList((Statement) $1);}
		| do_while													{$$ = asStatementList((Statement) $1);}
		| for														{$$ = asStatementList((Statement) $1);}
		| if															{$$ = asStatementList((Statement) $1);}
		;

// ~~~~~ Sentencias que llevan ';', pero aún no lo tienen ~~~~~
statement_without_semicolon: assignment_as_expression					{$$ = asStatementList((Statement) $1);}
							| function_call_as_expression			{$$ = asStatementList((Statement) $1);}
							| return									{$$ = asStatementList((Statement) $1);}
							| read									{$$ = (List<Statement>) $1;} 			// Read  ya devuelve una lista
							| write									{$$ = (List<Statement>) $1;} 			// Write ya devuelve una lista
							| plus_equal								{$$ = asStatementList((Statement) $1);}
							| minus_equal							{$$ = asStatementList((Statement) $1);}
							| mul_equal								{$$ = asStatementList((Statement) $1);}
							| div_equal								{$$ = asStatementList((Statement) $1);}
							| mod_equal								{$$ = asStatementList((Statement) $1);}
							| pre_plus_plus_as_expression			{$$ = asStatementList((Statement) $1);}
					        	| pre_minus_minus_as_expression			{$$ = asStatementList((Statement) $1);}
					       	| post_plus_plus_as_expression			{$$ = asStatementList((Statement) $1);}
					        	| post_minus_minus_as_expression			{$$ = asStatementList((Statement) $1);}				
							;



// ~~~~~~~~~~~ Tipos ~~~~~~~~~~~
type: simple_type													{$$ = (Type) $1;}
	| array															{$$ = (Type) $1;}
	| struct															{$$ = (Type) $1;}			
	;

simple_type: INT														{$$ = IntType.getInstance();}
	| DOUBLE															{$$ = RealType.getInstance();}
	| CHAR															{$$ = CharType.getInstance();}
	;

array: '[' INT_CONSTANT ']' type 									{$$ = new ArrayType(scanner.getLine(), scanner.getColumn(), (int) $2, (Type) $4);}
 	;
		
struct: STRUCT '{' struct_body '}'									{$$ = new RecordType(scanner.getLine(), scanner.getColumn(), (List<RecordField>) $3);}
	;
	
// Obligamos a que dentro del struct haya al menos una definicion de variable (un campo)

																	// variable_definition es un List<VarDefinition>. Tenemos que convertirlo en un List<RecordField>
struct_body: variable_definition 									{	
																		List<RecordField> fields = new LinkedList<RecordField>(); 
																		List<VarDefinition> varDefinitions = (List<VarDefinition>) $1;
																		
																		for(VarDefinition varDefinition : varDefinitions){
																			RecordField field = new RecordField(varDefinition);
																			fields.add(field);
																		}
																		$$ = fields;
																	}
		| struct_body variable_definition							{	
																		List<RecordField> fields = (List<RecordField>) $1; 
																		List<VarDefinition> varDefinitions = (List<VarDefinition>) $2;
																		
																		for(VarDefinition varDefinition : varDefinitions){
																			RecordField field = new RecordField(varDefinition);
																			
																			// Si el campo ya está en la lista, es un error semántico (lo detectamos en esta fase que es más fácil). 
																			if(fields.contains(field))
																				new ErrorType(field, "Semantical error: The record field '" + field.name + "' has already been defined in the struct.");
																			
																			fields.add(field);
																		}
																		$$ = fields;
																	}
		;

// ########### Sentencias (Statements)  ########### 
read: INPUT expressions			%prec MENOR_QUE_COMA					{	// statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) $2;
																		for(Expression expression : expressions){
																			statements.add( new Read(scanner.getLine(), scanner.getColumn(), expression) );
																		}
																		$$ = statements;
																	}
	;
	
write: PRINT {writeTempLine = scanner.getLine();} expressions		%prec MENOR_QUE_COMA	
																	{	// statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) $3;
																		for(Expression expression : expressions){
																			statements.add( new Write(writeTempLine, scanner.getColumn(), expression) );
																		}
																		$$ = statements;
																	}
	| PRINTLN {writeTempLine = scanner.getLine();} expressions	%prec MENOR_QUE_COMA
																	{	// statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) $3;
																		for(Expression expression : expressions){
																			statements.add( new Write(writeTempLine, scanner.getColumn(), expression) );
																		}
																		
																		// Añadimos un ultimo Write con un salto de linea
																		statements.add( new Write(writeTempLine, scanner.getColumn(), 
																			new CharLiteral(scanner.getLine(), scanner.getColumn(), '\n')) );
																		
																		$$ = statements;
																	}
	;

// Auxiliar (1+cs)
expressions: expression												{List<Expression> expressions = new LinkedList<Expression>(); expressions.add((Expression) $1); $$ = expressions;}
		| expressions ',' expression									{List<Expression> expressions = (List<Expression>) $1; expressions.add((Expression) $3); $$ = expressions;}
		;


while: WHILE expression ':' '{' statements '}'						{
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) $5);
																		Expression expression = (Expression) $2;

																		$$ = new While(expression.getLine(), expression.getColumn(), expression, body);
																	}

	| WHILE expression ':' statement									{																		
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) $4);
																		Expression expression = (Expression) $2;

																		$$ = new While(expression.getLine(), expression.getColumn(), expression, body);
																	}
	;
	
do_while: DO ':' '{' statements '}' WHILE expression ';'				{
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) $4);
																		Expression expression = (Expression) $7;

																		$$ = new DoWhile(expression.getLine(), expression.getColumn(), expression, body);
																	}

	| DO ':' statement WHILE expression ';'							{																		
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) $3);
																		Expression expression = (Expression) $5;

																		$$ = new DoWhile(expression.getLine(), expression.getColumn(), expression, body);
																	}
	;

for: FOR '(' for_parenthesis_body ')' ':' '{' statements '}'			{
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) $7);
																		ForParenthesisBody forParenthesisBody = (ForParenthesisBody) $3;
																		
																		$$ = new For(forParenthesisBody.condition.getLine(), forParenthesisBody.condition.getColumn(), 
																								forParenthesisBody.initializationStatements, forParenthesisBody.condition, 
																								forParenthesisBody.incrementStatements, body);
																	}

	| FOR '(' for_parenthesis_body ')' ':' statement					{																		
																		ForParenthesisBody forParenthesisBody = (ForParenthesisBody) $3;
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) $6);

																		$$ = new For(forParenthesisBody.condition.getLine(), forParenthesisBody.condition.getColumn(), 
																								forParenthesisBody.initializationStatements, forParenthesisBody.condition, 
																								forParenthesisBody.incrementStatements, body);
																	}
	;
	
// Al usar statement_without_semicolon ya nos quitamos de en medio if, while, ...
for_parenthesis_body: statement_without_semicolon_1mcs ';' expression ';' statement_without_semicolon_1mcs
																	{	
																		$$ = new ForParenthesisBody((List<Statement>) $1, (Expression) $3, (List<Statement>) $5); 
																	}
					;

// Auxiliar (1+cs)				
statement_without_semicolon_1mcs: statement_without_semicolon					{$$ = (List<Statement>) $1;} // Retornamos directamente la lista de sentencias
		| statement_without_semicolon_1mcs ',' statement_without_semicolon	{List<Statement> statements = (List<Statement>) $1; statements.addAll((List<Statement>) $3); $$ = statements;}
		;

	
if: IF expression ':' '{' statements '}' 	%prec MENOR_QUE_ELSE		{																		
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) $5);
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) $2;

																		$$ = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																	}

  | IF expression ':' '{' statements '}' ELSE '{' statements '}'		{
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) $5);
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) $9);
  																		Expression expression = (Expression) $2;
																		
																		$$ = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																	}
  
  | IF expression ':' '{' statements '}' ELSE statement				{																		
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) $5);
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) $8);
  																		Expression expression = (Expression) $2;

																		$$ = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																	}
  
  | IF expression ':' statement				%prec MENOR_QUE_ELSE		{
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) $4);
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) $2;
																		
																		$$ = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																	}
  																		
  | IF expression ':' statement ELSE '{' statements '}'				{																		
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) $4);
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) $7);
  																		Expression expression = (Expression) $2;
																		
																		$$ = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody); 
  																	}
  																		
  | IF expression ':' statement ELSE statement						{																		
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) $4);
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) $6);
  																		Expression expression = (Expression) $2;
																		
																		$$ = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
																	}
  ;


return: RETURN {returnTempLine = scanner.getLine();} expression		{																		
																		$$ = new Return(returnTempLine, scanner.getColumn(), (Expression) $3);
																	}
	;
	

// Auxiliar.  Entre los () va una secuencia de expresiones separadas por comas (QUE PUEDE SER VACIA). 
// Necesitamos 0+cs. Hacemos que expressions sea opcional.
parameters_in_function_call: 											{$$ = new LinkedList<Expression>();}
							| expressions								{$$ = (List<Expression>) $1;}
							;
				
// * +=, -=, *=, /= y %=				
plus_equal: expression PLUS_EQUAL expression							{																		
																		$$ = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, 
																				new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "+", (Expression) $3));
																	}
		;
		
minus_equal: expression MINUS_EQUAL expression						{																		
																		$$ = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, 
																				new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "-", (Expression) $3));
																	}
		;
		
mul_equal: expression MUL_EQUAL expression							{																		
																		$$ = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, 
																				new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "*", (Expression) $3));
																	}
		;
		
div_equal: expression DIV_EQUAL expression							{																		
																		$$ = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, 
																				new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "/", (Expression) $3));
																	}
		;
		
mod_equal: expression MOD_EQUAL expression							{																		
																		$$ = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, 
																				new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "%", (Expression) $3));
																	}
		;
			
		
// ########### Expresiones (Expressions)  ###########
	 
expression: expression AND expression								{$$ = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression OR expression									{$$ = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | xor_expression											{$$ = (Logical) $1;}
		 | expression '>' expression									{$$ = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression GREATER_OR_EQUAL expression					{$$ = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression '<' expression									{$$ = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression LESS_OR_EQUAL expression						{$$ = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression DISTINCT expression							{$$ = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression EQUALS expression								{$$ = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
		 | expression '+' expression									{$$ = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
         | expression '-' expression									{$$ = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
         | expression '*' expression									{$$ = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
         | expression '/' expression									{$$ = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
         | expression '%' expression									{$$ = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $2, (Expression) $3);}
         | '!' expression											{$$ = new UnaryNot(scanner.getLine(), scanner.getColumn(), (Expression) $2);}
         | '-' expression					%prec UNARY_MINUS		{$$ = new UnaryMinus(scanner.getLine(), scanner.getColumn(), (Expression) $2);}
         | '(' simple_type ')' expression 	%prec CAST				{$$ = new Cast(scanner.getLine(), scanner.getColumn(), (Type) $2, (Expression) $4);}
         | expression '.' ID											{$$ = new FieldAccess(scanner.getLine(), scanner.getColumn(), (Expression) $1, (String) $3);}
         | expression '[' expression ']'								{$$ = new Indexing(scanner.getLine(), scanner.getColumn(), (Expression) $1, (Expression) $3);}
         | '(' expression ')' 				%prec PARENTESIS       	{$$ = (Expression) $2;}
         | function_call_as_expression								{$$ = (Invocation) $1;}
         | INT_CONSTANT												{$$ = new IntLiteral(scanner.getLine(), scanner.getColumn(), (int) $1);}
         | REAL_CONSTANT												{$$ = new RealLiteral(scanner.getLine(), scanner.getColumn(), (double) $1);}
         | CHAR_CONSTANT												{$$ = new CharLiteral(scanner.getLine(), scanner.getColumn(), (char) $1);}
         | ID														{$$ = new Variable(scanner.getLine(), scanner.getColumn(), (String) $1);}
         | pre_plus_plus_as_expression								{$$ = (PreArithmetic) $1;}
         | pre_minus_minus_as_expression								{$$ = (PreArithmetic) $1;}
         | post_plus_plus_as_expression								{$$ = (PostArithmetic) $1;}
         | post_minus_minus_as_expression							{$$ = (PostArithmetic) $1;}
         | ternaryOperator											{$$ = (TernaryOperator) $1;}
         | assignment_as_expression									{$$ = (Assignment) $1;}
         ;
   
function_call_as_expression: ID '(' parameters_in_function_call ')'  	{	
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) $1);
																		$$ = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) $3);
																	}
						;
						
pre_plus_plus_as_expression: 	PLUS_PLUS expression		%prec PRE_ARITHMETIC		{$$ = new PreArithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $2, "++");}
pre_minus_minus_as_expression:	MINUS_MINUS expression	%prec PRE_ARITHMETIC		{$$ = new PreArithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $2, "--");}
post_plus_plus_as_expression: 	expression PLUS_PLUS								{$$ = new PostArithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "++");}
post_minus_minus_as_expression:	expression MINUS_MINUS							{$$ = new PostArithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "--");}
						
ternaryOperator: expression '?' expression ':' expression		%prec TERNARY_OPERATOR 	{
																						$$ = new TernaryOperator(scanner.getLine(), scanner.getColumn(), 
																												(Expression) $1, (Expression) $3, (Expression) $5);
																					}
				;      
						
assignment_as_expression: expression '=' expression				 	{
																		$$ = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, (Expression) $3);
																	}
						;      
         
xor_expression: expression XOR expression							{	// a ^^ b <-> (a || b) && !(a && b)
																		Logical left  = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) $1, "||", (Expression) $3);
																		Logical right = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) $1, "&&", (Expression) $3);
																		UnaryNot notRight = new UnaryNot(scanner.getLine(), scanner.getColumn(), right);
																		
																		$$ = new Logical(scanner.getLine(), scanner.getColumn(), left, "&&", notRight);
																	}
			;
         
%%

// * Código Java
// * Se crea una clase "Parser", lo que aquí ubiquemos será:
//	- Atributos, si son variables
//	- Métodos, si son funciones
//   de la clase "Parser"

// * Estamos obligados a implementar:
//	int yylex()
//	void yyerror(String)

// * Referencia al analizador léxico
private Scanner scanner;

// * Llamada al analizador léxico
private int yylex () {
    int token=0;
    try { 
		token=scanner.yylex(); 	
		this.yylval = scanner.getYylval();
    } catch(Throwable e) {
	    // Esto es cuando se produce un error en el lexico. Cuando casca, no cuando detecta un error lexico.
	    new ErrorType(scanner.getLine(), scanner.getColumn(), "Lexical error: " + e); 
    }
    return token;
}

// * Manejo de Errores Sintácticos
public void yyerror (String error) {
    new ErrorType(scanner.getLine(), scanner.getColumn(), "Syntactical error: " + error);
}

// * Constructor del Sintáctico
public Parser(Scanner scanner) {
	this.scanner = scanner;
}

// * Nodo raiz del arbol
private ASTNode ast;

public ASTNode getAST(){
	return this.ast;
}

// * Para añadir un Statement a un List<Statement> de un solo elemento
private List<Statement> asStatementList(Statement statement){
	List<Statement> list = new LinkedList<Statement>();
	list.add(statement);
	return list;
}

// Variables temporales para detectar bien los numeros de linea
int funDefTempLine;
int writeTempLine;
int returnTempLine;

// * Para devolver los 3 elementos del cuerpo entre paréntesis de un for
private class ForParenthesisBody{
	public List<Statement> initializationStatements;
	public Expression condition;
	public List<Statement> incrementStatements;
	
	public ForParenthesisBody(List<Statement> initializationStatements, Expression condition,
			List<Statement> incrementStatements) {
		this.initializationStatements = initializationStatements;
		this.condition = condition;
		this.incrementStatements = incrementStatements;
	}
	
}

