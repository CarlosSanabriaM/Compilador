// ************  Código a incluir ********************

package scanner;
import parser.Parser;
import ast.types.ErrorType;

%%
// ************  Opciones ********************
// % debug // * Opción para depurar
%byaccj
%class Scanner //el nombre de la clase que queremos que genere
%public
%unicode
%line
%column

%{
// ************  Atributos y métodos ********************

// * Para acceder al número de línea (yyline es package)
public int getLine() { 
	// * Flex empieza en cero
	return yyline+1;
}

// * Para acceder al número de columna (yycolumn es package)
public int getColumn() { 
	// * Flex empieza en cero
	return yycolumn+1;
}

// * Valor semantico del token
private Object yylval;
public Object getYylval() {
	return this.yylval;
}

%}

// ********************  Patrones (macros) ********************

// Saltables y comentarios
Saltables = [ \n\t\r]+
ComentarioUnaLinea = "#" .* (\n)? //El operador . no incluye caracteres especiales, como puede ser \n, mientras que ~ sí, por eso usamos .* y no ~
ComentarioVariasLineas = \"\"\" ~ \"\"\" 

// Enteros
ConstanteEntera = [0-9]+

// Reales
ConstanteRealIzda = {ConstanteEntera}"."
ConstanteRealDcha = "."{ConstanteEntera}
ConstanteRealAmbos = {ConstanteEntera}"."{ConstanteEntera}
ConstanteRealPorPunto = {ConstanteRealIzda} | {ConstanteRealDcha} | {ConstanteRealAmbos} 

ConstanteEnteraORealPorPunto = {ConstanteEntera} | {ConstanteRealPorPunto}
ConstanteRealPorExponente = {ConstanteEnteraORealPorPunto}[eE][-+]?{ConstanteEntera} 
ConstanteReal = {ConstanteRealPorPunto} | {ConstanteRealPorExponente}

// Caracteres
CaracteresEspeciales = '\\n'|'\\t'
ConstanteCaracterNormal = '.'|{CaracteresEspeciales}
ConstanteCaracterASCII = '\\[0-9]+'

// Identificadores
Identificador = [a-zA-ZñÑáéíóúÁÉÍÓÚ_][a-zA-ZñÑáéíóúÁÉÍÓÚ_0-9]* //Incluye ñ y las tíldes habituales

// Operadores de más de un carácter
IgualIgual 	= "==" 
MenorOIgual 	= "<="
MayorOIgual 	= ">=" 
Distinto 	= "!="
And 			= "&&"
Or 			= "||"
MasIgual 	= "+="
MenosIgual 	= "-="
PorIgual 	= "*="
DivIgual 	= "/="
ModIgual 	= "%="
MasMas		= "++"
MenosMenos	= "--"

%%
// ********************  Acciones ********************
// Para utilizar un patrón, ponemos su nombre entre llaves

// * Saltables y comentarios
{Saltables}						{ }
{ComentarioUnaLinea}				{ }
{ComentarioVariasLineas}			{ }

// * Palabras reservadas
"input"							{ return Parser.INPUT; }
"print"							{ return Parser.PRINT; }
"println"						{ return Parser.PRINTLN; }
"def"							{ return Parser.DEF; }
"while"							{ return Parser.WHILE; }
"if"								{ return Parser.IF; }
"else"							{ return Parser.ELSE; }
"int"							{ return Parser.INT; }
"double"							{ return Parser.DOUBLE; }
"char"							{ return Parser.CHAR; }
"struct"							{ return Parser.STRUCT; }
"return"							{ return Parser.RETURN; }
"void"							{ return Parser.VOID; }
"main"							{ return Parser.MAIN; }

// * Operadores, paréntesis, corchetes, llaves, coma, punto, punto y coma, dos puntos, interrogacion
[+\-/*%><=()!\[\]{},;.:?]			{ this.yylval = yytext();
									return yytext().charAt(0); }
									
{IgualIgual}						{ this.yylval = yytext();
									return Parser.EQUALS; }
{MenorOIgual}					{ this.yylval = yytext();
									return Parser.LESS_OR_EQUAL; }
{MayorOIgual}					{ this.yylval = yytext();
									return Parser.GREATER_OR_EQUAL; }
{Distinto}						{ this.yylval = yytext();
									return Parser.DISTINCT; }
{And}							{ this.yylval = yytext();
									return Parser.AND; }
{Or}								{ this.yylval = yytext();
									return Parser.OR; }
									
// * +=, -=, *=, /= y %=
{MasIgual}						{ this.yylval = yytext();
									return Parser.PLUS_EQUAL; }

{MenosIgual}						{ this.yylval = yytext();
									return Parser.MINUS_EQUAL; }

{PorIgual}						{ this.yylval = yytext();
									return Parser.MUL_EQUAL; }

{DivIgual}						{ this.yylval = yytext();
									return Parser.DIV_EQUAL; }

{ModIgual}						{ this.yylval = yytext();
									return Parser.MOD_EQUAL; }
									
// ++ y --
{MasMas}							{ this.yylval = yytext();
									return Parser.PLUS_PLUS; }

{MenosMenos}						{ this.yylval = yytext();
									return Parser.MINUS_MINUS; }
									

// * Constante Entera
{ConstanteEntera}				{ this.yylval = new Integer(yytext());
         			  				return Parser.INT_CONSTANT; }
// * Constante Real         			  				
{ConstanteReal}					{ this.yylval = new Double(yytext());
									return Parser.REAL_CONSTANT; }
// * Constantes Caracter         			  				
{ConstanteCaracterNormal}		{ String s = yytext();
								  	//Comprobamos si el caracter es uno de los especiales
								  	if(s.equals("'\\n'")) this.yylval = '\n'; 
								  	else if(s.equals("'\\t'")) this.yylval = '\t';
								  	//Si no, es que solo tiene un elemento dentro de las comillas
								  	else this.yylval = yytext().charAt(1);
									return Parser.CHAR_CONSTANT; }

{ConstanteCaracterASCII}			{ String s = yytext();
									//Obtenemos el string que hay entre la \ y la ultima ' (será el entero en formato string)
									String ascii_string = s.substring(2,s.length()-1);
									//Convertimos el string en entero, y el entero en char
									Character c = (char) Integer.parseInt(ascii_string);
								  	this.yylval = c;
									return Parser.CHAR_CONSTANT; }
																	
// * Identificador									
{Identificador}					{ this.yylval = yytext();
									return Parser.ID; }
			

		  
// * Cualquier otro carácter
.								{ new ErrorType(this.getLine(), this.getColumn(), "Lexical error: Unknow character \'"+ yycharat(0)+"\'."); }		
				
			
			