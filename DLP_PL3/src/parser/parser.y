%{
// * Declaraciones de código Java
// * Se sitúan al comienzo del archivo generado
// * El package lo añade yacc si utilizamos la opción -Jpackage
import scanner.Scanner;
import java.io.Reader;
%}

// * Declaraciones Yacc
//Aqui declaramos todos los tokens
%token INT_CONSTANT
%token REAL_CONSTANT
%token CHAR_CONSTANT
%token INPUT
%token PRINT
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

//Tokens con prioridades (los de mas abajo son mas prioritarios)
%nonassoc MENOR_QUE_ELSE
%nonassoc ELSE
%right '='
%left AND OR
%left '>' GREATER_OR_EQUAL '<' LESS_OR_EQUAL DISTINCT EQUALS
%left '+' '-'
%left '*' '/' '%'
%nonassoc '!'
%nonassoc UNARY_MINUS
%nonassoc CAST
%left '.'
%nonassoc '['
%nonassoc PARENTESIS

%%
// * Gramática y acciones Yacc

// La funcion main al final del programa es OBLIGATORIA
program: _program main
		;
		
_program: // λ
		| _program variable_definition
		| _program function_definition
		;

main: DEF MAIN '(' ')' ':' VOID '{' function_body '}'
	;

// Definicion de variables
variable_definition: variables ':' type ';'
		;

variables: ID
		| variables ',' ID
		;
	
// Definicion de funciones
function_definition: DEF ID '(' parameters ')' ':' return_type '{' function_body '}'
		;
		
parameters: // λ
		| _parameters
		;

_parameters: parameter
		| _parameters ',' parameter
		;
		
parameter: ID ':' simple_type
		;

return_type: simple_type
		| VOID
		;

function_body: // λ
		| definitions statements // Una lista de definiciones, seguido de una lista de sentencias
		| definitions			// Solo definiciones
		| statements				// Solo sentencias
		;
		
definitions: variable_definition
		| definitions variable_definition
		;
		
statements: statement
		| statements statement
		;

// Tipos de sentencias		
statement: assignment
		| function_call_as_statement
		| return
		| while
		| if
		| read
		| write	
		;
		
type: simple_type
	| array
	| struct
	;

simple_type: INT
	| DOUBLE
	| CHAR
	;

// Asi deberia funcionar y permitir varias dimensiones del array --> a: [2][1][2] INT;
// Ya que type puede ser otro array con sus corchetes y type.
array: '[' INT_CONSTANT ']' type 
 	;
		
struct: STRUCT '{' struct_body '}'
	;
	
// Obligamos a que dentro del struct haya al menos una definicion de variable (un campo)
struct_body: variable_definition 
		| struct_body variable_definition 
		;

// ########### Sentencias (Statements)  ########### 
assignment: expression '=' expression ';'
		;
		
read: INPUT expressions ';'
	;
	
write: PRINT expressions ';'
	;

// Auxiliar (1+cs)
expressions: expression
		| expressions ',' expression
		;

while: WHILE expression ':' '{' statements '}'
	| WHILE expression ':' statement
	;
	
if: IF expression ':' '{' statements '}' 						%prec MENOR_QUE_ELSE
  | IF expression ':' '{' statements '}' ELSE '{' statements '}'
  | IF expression ':' '{' statements '}' ELSE statement
  | IF expression ':' statement									%prec MENOR_QUE_ELSE
  | IF expression ':' statement ELSE '{' statements '}'
  | IF expression ':' statement ELSE statement
  ;

return: RETURN expression ';'
	;
	

// Auxiliar.  Entre los () va una secuencia de expresiones separadas por comas (QUE PUEDE SER VACIA). 
// Necesitamos 0+cs. Hacemos que expressions sea opcional.
parameters_in_funcion_call: // λ
						| expressions
						;
		
function_call_as_statement: ID '(' parameters_in_funcion_call ')' ';'
						; 

//  ########### Expresiones (Expressions)  ###########

function_call_as_expression: ID '(' parameters_in_funcion_call ')'
						;
 
expression: expression AND expression
		 | expression OR expression
		 | expression '>' expression
		 | expression GREATER_OR_EQUAL expression		
		 | expression '<' expression		
		 | expression LESS_OR_EQUAL expression		
		 | expression DISTINCT expression		
		 | expression EQUALS expression		
		 | expression '+' expression		
         | expression '-' expression
         | expression '*' expression
         | expression '/' expression
         | expression '%' expression
         | '!' expression
         | '-' expression					%prec UNARY_MINUS
         | '(' simple_type ')' expression 	%prec CAST
         | expression '.' ID //ANTES TENIA expression al final
         | expression '[' expression ']'
         | '(' expression ')' 				%prec PARENTESIS
         | function_call_as_expression
         | INT_CONSTANT
         | REAL_CONSTANT
         | CHAR_CONSTANT
         | ID
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
	    System.err.println ("Lexical error at line " + scanner.getLine() + " and column "+scanner.getColumn()+":\n\t"+e); 
    }
    return token;
}

// * Manejo de Errores Sintácticos
public void yyerror (String error) {
    System.err.println ("Syntactical error at line " + scanner.getLine() + " and column "+scanner.getColumn()+":\n\t"+error);
}

// * Constructor del Sintáctico
public Parser(Scanner scanner) {
	this.scanner = scanner;
}
