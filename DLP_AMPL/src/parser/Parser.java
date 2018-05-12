//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package parser;



//#line 1 "../../src/parser/parser.y"

/* * Declaraciones de código Java*/
/* * Se sitúan al comienzo del archivo generado*/
/* * El package lo añade yacc si utilizamos la opción -Jpackage*/
import scanner.Scanner;
import java.io.Reader;
import java.util.List;
import java.util.LinkedList;

import ast.*;
import ast.definitions.*;
import ast.expressions.*;
import ast.statements.*;
import ast.statementsAndExpressions.*;
import ast.types.*;
//#line 33 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Object dup_yyval(Object val)
{
  return val;
}
//#### end semantic value section ####
public final static short INT_CONSTANT=257;
public final static short REAL_CONSTANT=258;
public final static short CHAR_CONSTANT=259;
public final static short INPUT=260;
public final static short PRINT=261;
public final static short DEF=262;
public final static short WHILE=263;
public final static short IF=264;
public final static short ELSE=265;
public final static short INT=266;
public final static short DOUBLE=267;
public final static short CHAR=268;
public final static short STRUCT=269;
public final static short RETURN=270;
public final static short VOID=271;
public final static short ID=272;
public final static short MAIN=273;
public final static short PLUS_EQUAL=274;
public final static short MINUS_EQUAL=275;
public final static short MUL_EQUAL=276;
public final static short DIV_EQUAL=277;
public final static short MOD_EQUAL=278;
public final static short MENOR_QUE_ELSE=279;
public final static short AND=280;
public final static short OR=281;
public final static short GREATER_OR_EQUAL=282;
public final static short LESS_OR_EQUAL=283;
public final static short DISTINCT=284;
public final static short EQUALS=285;
public final static short UNARY_MINUS=286;
public final static short CAST=287;
public final static short PARENTESIS=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    5,    2,    3,    7,    7,    9,
    4,   10,   10,   12,   12,   13,   11,   11,    6,    6,
    6,    6,   15,   15,   16,   16,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,    8,    8,
    8,   14,   14,   14,   30,   31,   32,   32,   18,   23,
   35,   24,   34,   34,   21,   21,   22,   22,   22,   22,
   22,   22,   36,   20,   37,   37,   19,   25,   26,   27,
   28,   29,   38,   33,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   33,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   33,   33,   33,   33,
};
final static short yylen[] = {                            2,
    2,    0,    2,    2,    0,   10,    4,    1,    3,    0,
   11,    0,    1,    1,    3,    3,    1,    1,    0,    2,
    1,    1,    1,    2,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    4,    4,    1,    2,    4,    3,
    0,    4,    1,    3,    6,    4,    6,   10,    8,    4,
    8,    6,    0,    4,    0,    1,    5,    4,    4,    4,
    4,    4,    4,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    4,    3,
    4,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    8,    1,    3,    4,    0,    0,    0,
    0,    0,    0,    0,   42,   43,   44,    0,    0,    0,
   39,   40,   41,    9,    0,    0,    0,    0,    7,    0,
    0,    0,    0,   14,   47,    0,    0,    0,    0,    0,
    0,   46,   48,   45,    0,   16,    0,   15,    0,   18,
    0,   17,   94,   95,   96,    0,   51,    0,    0,   63,
    0,    0,    0,    0,   23,    0,    0,    0,   25,   27,
   28,   29,   30,   31,   32,   33,   34,   35,   36,   37,
   38,    0,   93,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    6,   24,    0,    0,
   26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   50,    0,    0,    0,    0,    0,
    0,    0,    0,   92,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,    0,   11,    0,    0,   52,    0,
   56,    0,    0,   64,    0,    0,   68,   69,   70,   71,
   72,   49,   91,   73,    0,    0,    0,   67,   55,    0,
    0,   62,    0,    0,    0,   59,   61,    0,   58,
};
final static short yydgoto[] = {                          1,
    2,    5,   65,    7,    9,   66,    8,   20,   10,   32,
   51,   33,   34,   21,   67,   68,   69,   70,   71,   72,
   73,   74,   75,   76,   77,   78,   79,   80,   81,   22,
   23,   36,   82,  131,   88,   91,  132,   83,
};
final static short yysindex[] = {                         0,
    0, -191,    0,    0,    0,    0,    0,  -42, -258, -245,
  -56, -234,    2,    5,    0,    0,    0,  -77, -190,    9,
    0,    0,    0,    0,   28, -200, -199,  -13,    0,   30,
   33,   51,   50,    0,    0, -124,  -56, -176, -202,   39,
 -200,    0,    0,    0,  -24,    0, -227,    0, 1267,    0,
  -18,    0,    0,    0,    0,  377,    0,  377,  377,    0,
   67,  377,  377,  488,    0,  -10, 1267, 1318,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  215,    0, 1267,   78,  671,  -31,  377,  131,  410,
  377,  377,  -29,  -29,   79,  295,    0,    0, 1318,   67,
    0,  377,  377,  377,  377,  377,  377,  377,  377,  377,
  377,  377,  377,  377,  377,  377,  377,  377,  377,  377,
 -153,  377,   -3,  377,    0,  377,  -30,  847, 1069,  417,
   80,   82,  377,    0,  522,  546,  557,  581,  614,  621,
  678,  678,  187,  187,  187,  187,  187,  187,  189,  189,
  -29,  -29,  -29,    0,  494,    0,   84,  671,    0, 1318,
    0, 1318, -139,    0,   74,  -29,    0,    0,    0,    0,
    0,    0,    0,    0, 1038, 1085, 1137,    0,    0, -131,
 1318,    0, 1156, 1186, 1318,    0,    0, 1206,    0,
};
final static short yyrindex[] = {                         0,
    0,    0, -254,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  100,    0,    0,    0,    0,
    0,    0,  102,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   19,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  158,    0,    0,    0,    0,    0,   20,   21,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   19,  -37,   11,    0,    0,    0,    0,
    0,  106,  -11,   16,    0,    0,    0,    0,   24,  242,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  106,    0,    0,    0,    0,    0,    0,
  109,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  937, 1000,  739,  751,  792,  814,  871,  884,  691,  727,
   42,   69,   95,    0,    0,    0,    0,   49,    0,    0,
    0,    0, 1222,    0,  268,  122,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1249,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  354,    0,    0,   68,    0,  114,    0,    0,
    0,    0,  117,  -27,    0,  -64,  290,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1441,  -45,    0,    0,   36,    0,
};
final static int YYTABLESIZE=1590;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         97,
   42,   12,   99,   97,   97,   97,   97,   97,   97,   97,
   87,   46,  126,  126,   13,   11,  121,   10,    5,   52,
   97,   97,   97,   97,   97,   88,   14,  125,  159,   88,
   88,   88,   88,   88,   19,   88,   95,   24,   15,   16,
   17,   25,  127,   50,   26,   27,   88,   88,   88,   88,
   88,   53,   87,   97,   53,   97,   87,   87,   87,   87,
   87,  122,   87,   15,   16,   17,   28,   29,   30,   53,
    3,   31,    4,   87,   87,   87,   87,   87,   84,   37,
    4,   88,   84,   84,   84,   84,   84,   38,   84,   54,
   39,   40,   54,   41,   45,  175,   47,  176,   49,   84,
   84,   84,   84,   84,   84,   85,   92,   54,   87,   85,
   85,   85,   85,   85,   97,   85,  184,  124,  154,  133,
  188,  156,  165,  126,  174,  177,   85,   85,   85,   85,
   85,   86,  178,  183,   84,   86,   86,   86,   86,   86,
   12,   86,   13,   19,   21,   22,   65,    4,   20,   66,
   44,  123,   86,   86,   86,   86,   86,   48,   89,  157,
    0,   85,   89,   89,   89,   89,   89,  120,   89,    0,
    0,    0,  118,  116,    0,  117,  121,  119,    0,   89,
   89,   89,   89,   89,    0,    0,    0,   86,  128,    0,
  112,    0,  110,    0,   97,    0,    0,    0,    0,   97,
   97,    8,   97,   97,   97,    0,    0,    0,    0,   15,
   16,   17,   18,    0,   89,    8,    0,   97,   97,   97,
    0,  122,    0,  120,    0,  120,    0,    0,  118,  116,
  118,  117,  121,  119,  121,  119,   97,   97,   97,   97,
   97,    0,   97,   97,   97,   97,   97,   97,   97,    0,
    0,  120,    0,    0,    0,    0,  118,  116,    0,  117,
  121,  119,   88,   88,   88,   88,   88,    0,   88,   88,
   88,   88,   88,   88,  112,  107,  110,  122,   97,  122,
    0,    0,    0,   97,   97,    0,   97,   97,   97,   87,
   87,   87,   87,   87,    0,   87,   87,   87,   87,   87,
   87,   97,   97,   97,   73,  122,    0,    0,    0,   73,
   73,    0,   73,   73,   73,   84,   84,   84,   84,   84,
    0,   84,   84,   84,   84,   84,   84,   73,   73,   73,
    0,  120,   97,    0,    0,  134,  118,  116,    0,  117,
  121,  119,   85,   85,   85,   85,   85,    0,   85,   85,
   85,   85,   85,   85,  112,    6,  110,  101,   73,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   86,   86,
   86,   86,   86,    0,   86,   86,   86,   86,   86,   86,
   35,    0,    0,    0,    0,  122,    0,    0,  101,   43,
    0,    0,    0,    0,    0,   89,   89,   89,   89,   89,
    0,   89,   89,   89,   89,   89,   89,    0,    0,   63,
  108,  109,  111,  113,  114,  115,   64,  161,  163,    0,
   98,   62,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   97,   97,   97,   97,   97,    0,   97,   97,   97,
   97,   97,   97,    0,    0,    0,  120,    0,    0,    0,
    0,  118,  116,  120,  117,  121,  119,    0,  118,  116,
    0,  117,  121,  119,  101,  101,  182,  129,    0,  112,
    0,  110,  186,  101,    0,  164,  112,  101,  110,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  102,  103,
  104,  105,  106,    0,  108,  109,  111,  113,  114,  115,
  122,    0,    0,    0,    0,    0,    0,  122,    0,    0,
    0,    0,    0,    0,    0,   97,   97,   97,   97,   97,
   63,   97,   97,   97,   97,   97,   97,   64,    0,    0,
  120,    0,   62,    0,    0,  118,  116,    0,  117,  121,
  119,   73,   73,   73,   73,   73,    0,   73,   73,   73,
   73,   73,   73,  112,    0,  110,    0,    0,  120,    0,
    0,    0,    0,  118,  116,    0,  117,  121,  119,    0,
    0,    0,    0,    0,  108,  109,  111,  113,  114,  115,
  167,  112,  120,  110,  122,    0,  173,  118,  116,    0,
  117,  121,  119,  120,    0,    0,    0,    0,  118,  116,
    0,  117,  121,  119,  168,  112,    0,  110,    0,    0,
    0,    0,  122,    0,    0,  169,  112,  120,  110,    0,
    0,    0,  118,  116,    0,  117,  121,  119,    0,    0,
    0,    0,    0,   53,   54,   55,  122,    0,    0,  170,
  112,    0,  110,    0,    0,    0,    0,  122,   85,    0,
  120,    0,    0,    0,    0,  118,  116,  120,  117,  121,
  119,    0,  118,  116,    0,  117,  121,  119,    0,    0,
    0,  122,  171,  112,    0,  110,    0,    0,    0,  172,
  112,    0,  110,    0,    0,    0,    0,    0,    0,  108,
  109,  111,  113,  114,  115,    0,  108,  109,  111,  113,
  114,  115,    0,    0,  122,    0,    0,  120,    0,    0,
    0,  122,  118,  116,  120,  117,  121,  119,    0,  118,
  116,    0,  117,  121,  119,    0,    0,    0,    0,    0,
  112,   82,  110,   82,   82,   82,    0,  112,    0,  110,
    0,    0,    0,    0,   53,   54,   55,    0,   82,   82,
   82,   82,   82,   15,   16,   17,    0,    0,    0,   85,
    0,  122,    0,    0,    0,    0,    0,   83,  122,   83,
   83,   83,    0,  108,  109,  111,  113,  114,  115,   76,
    0,    0,   76,   82,   83,   83,   83,   83,   83,    0,
    0,   77,    0,    0,   77,    0,   76,   76,   76,   76,
   76,  108,  109,  111,  113,  114,  115,    0,   77,   77,
   77,   77,   77,    0,    0,    0,    0,    0,    0,   83,
    0,    0,    0,    0,    0,  108,  109,  111,  113,  114,
  115,   76,   78,    0,    0,   78,  108,  109,  111,  113,
  114,  115,    0,   77,    0,    0,    0,    0,    0,   78,
   78,   78,   78,   78,   79,    0,    0,   79,    0,    0,
  108,  109,  111,  113,  114,  115,    0,    0,    0,    0,
    0,   79,   79,   79,   79,   79,    0,    0,    0,   63,
    0,    0,    0,    0,   78,    0,   64,    0,    0,    0,
    0,   62,    0,  108,  109,  111,  113,  114,  115,    0,
  108,  109,  111,  113,  114,  115,   79,    0,    0,    0,
    0,   80,    0,    0,   80,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   81,    0,    0,   81,   80,   80,
   80,   80,   80,    0,    0,    0,    0,    0,    0,    0,
    0,   81,   81,   81,   81,   81,    0,    0,    0,    0,
  108,  109,  111,  113,  114,  115,    0,    0,    0,  111,
  113,  114,  115,   80,   82,   82,   82,   82,   82,  160,
   82,   82,   82,   82,   82,   82,   81,   74,    0,    0,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   74,   74,    0,   74,    0,    0,
   83,   83,   83,   83,   83,    0,   83,   83,   83,   83,
   83,   83,   76,   76,   76,   76,   76,    0,   76,   76,
   76,   76,   76,   76,   77,   77,   77,   77,   77,   74,
   77,   77,   77,   77,   77,   77,    0,    0,    0,    0,
   75,    0,    0,   75,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   75,   75,    0,
   75,    0,    0,    0,    0,   78,   78,   78,   78,   78,
   63,   78,   78,   78,   78,   78,   78,   64,    0,    0,
    0,    0,   62,    0,    0,    0,    0,   79,   79,   79,
   79,   79,   75,   79,   79,   79,   79,   79,   79,    0,
    0,   63,    0,   53,   54,   55,   56,   57,   64,   58,
   59,    0,    0,   62,    0,    0,   60,   63,  100,    0,
    0,    0,    0,    0,   64,    0,    0,    0,    0,   62,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   80,   80,   80,   80,   80,    0,
   80,   80,   80,   80,   80,   80,    0,   81,   81,   81,
   81,   81,  179,   81,   81,   81,   81,   81,   81,   63,
    0,    0,    0,    0,    0,    0,   64,    0,    0,    0,
    0,   62,    0,    0,    0,    0,    0,    0,   63,    0,
    0,  162,    0,    0,    0,   64,    0,    0,    0,    0,
   62,    0,    0,    0,    0,    0,    0,    0,    0,  180,
   74,   74,   74,   74,   74,    0,   74,   74,   63,    0,
    0,    0,    0,    0,    0,   64,    0,    0,    0,    0,
   62,    0,    0,    0,    0,    0,    0,    0,   63,    0,
    0,    0,    0,    0,    0,   64,    0,    0,    0,    0,
   62,    0,    0,    0,   60,    0,    0,    0,    0,  181,
    0,   60,    0,    0,    0,    0,   60,    0,    0,    0,
    0,    0,    0,   75,   75,   75,   75,   75,  185,   75,
   75,   57,    0,    0,    0,    0,    0,    0,   57,    0,
    0,    0,    0,   57,   53,   54,   55,   56,   57,   63,
   58,   59,    0,    0,    0,    0,   64,   60,    0,  100,
  187,   62,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   53,   54,   55,   56,   57,
  189,   58,   59,    0,    0,    0,    0,    0,   60,    0,
  100,   53,   54,   55,   56,   57,   60,   58,   59,    0,
   63,    0,    0,    0,   60,    0,  100,   64,    0,    0,
    0,    0,   62,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   53,   54,   55,   56,   57,    0,   58,
   59,    0,    0,    0,    0,    0,   60,    0,  100,    0,
    0,    0,   53,   54,   55,   56,   57,    0,   58,   59,
    0,    0,    0,    0,    0,   60,    0,  100,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   53,   54,   55,   56,   57,    0,   58,   59,
    0,    0,    0,    0,    0,   60,    0,  100,    0,    0,
    0,    0,   53,   54,   55,   56,   57,    0,   58,   59,
    0,    0,    0,    0,    0,   60,    0,  100,   60,   60,
   60,   60,   60,    0,   60,   60,    0,    0,    0,    0,
    0,   60,    0,   60,    0,    0,   86,    0,   89,   90,
    0,    0,   93,   94,   96,   57,   57,   57,   57,   57,
    0,   57,   57,    0,    0,    0,    0,    0,   57,    0,
   57,    0,    0,   53,   54,   55,   56,   57,   86,   58,
   59,  130,   86,    0,    0,    0,   60,    0,   61,    0,
    0,    0,  135,  136,  137,  138,  139,  140,  141,  142,
  143,  144,  145,  146,  147,  148,  149,  150,  151,  152,
  153,    0,  155,    0,   86,    0,  158,    0,    0,    0,
    0,    0,    0,  166,   53,   54,   55,   56,   57,    0,
   58,   59,    0,    0,    0,    0,    0,   60,    0,  100,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  125,   44,   67,   41,   42,   43,   44,   45,   46,   47,
   56,   39,   44,   44,  273,   58,   46,  272,  273,   47,
   58,   59,   60,   61,   62,   37,  272,   59,   59,   41,
   42,   43,   44,   45,   91,   47,   64,  272,  266,  267,
  268,   40,   88,  271,   40,  123,   58,   59,   60,   61,
   62,   41,   37,   91,   44,   93,   41,   42,   43,   44,
   45,   91,   47,  266,  267,  268,  257,   59,   41,   59,
  262,  272,  272,   58,   59,   60,   61,   62,   37,   93,
  272,   93,   41,   42,   43,   44,   45,   58,   47,   41,
   58,   41,   44,   44,  271,  160,   58,  162,  123,   58,
   59,   60,   61,   62,  123,   37,   40,   59,   93,   41,
   42,   43,   44,   45,  125,   47,  181,   40,  272,   41,
  185,  125,   41,   44,   41,  265,   58,   59,   60,   61,
   62,   37,   59,  265,   93,   41,   42,   43,   44,   45,
   41,   47,   41,  125,  125,  125,   41,  272,  125,   41,
   37,   84,   58,   59,   60,   61,   62,   41,   37,  124,
   -1,   93,   41,   42,   43,   44,   45,   37,   47,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   58,
   59,   60,   61,   62,   -1,   -1,   -1,   93,   58,   -1,
   60,   -1,   62,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   44,   45,   46,   47,   -1,   -1,   -1,   -1,  266,
  267,  268,  269,   -1,   93,   58,   -1,   60,   61,   62,
   -1,   91,   -1,   37,   -1,   37,   -1,   -1,   42,   43,
   42,   45,   46,   47,   46,   47,  274,  275,  276,  277,
  278,   -1,  280,  281,  282,  283,  284,  285,   91,   -1,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,  274,  275,  276,  277,  278,   -1,  280,  281,
  282,  283,  284,  285,   60,   61,   62,   91,   37,   91,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,  274,
  275,  276,  277,  278,   -1,  280,  281,  282,  283,  284,
  285,   60,   61,   62,   37,   91,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,  274,  275,  276,  277,  278,
   -1,  280,  281,  282,  283,  284,  285,   60,   61,   62,
   -1,   37,   91,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,  274,  275,  276,  277,  278,   -1,  280,  281,
  282,  283,  284,  285,   60,    2,   62,   68,   91,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  274,  275,
  276,  277,  278,   -1,  280,  281,  282,  283,  284,  285,
   27,   -1,   -1,   -1,   -1,   91,   -1,   -1,   99,   36,
   -1,   -1,   -1,   -1,   -1,  274,  275,  276,  277,  278,
   -1,  280,  281,  282,  283,  284,  285,   -1,   -1,   33,
  280,  281,  282,  283,  284,  285,   40,  128,  129,   -1,
   67,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  274,  275,  276,  277,  278,   -1,  280,  281,  282,
  283,  284,  285,   -1,   -1,   -1,   37,   -1,   -1,   -1,
   -1,   42,   43,   37,   45,   46,   47,   -1,   42,   43,
   -1,   45,   46,   47,  175,  176,  177,   58,   -1,   60,
   -1,   62,  183,  184,   -1,   59,   60,  188,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  274,  275,
  276,  277,  278,   -1,  280,  281,  282,  283,  284,  285,
   91,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  274,  275,  276,  277,  278,
   33,  280,  281,  282,  283,  284,  285,   40,   -1,   -1,
   37,   -1,   45,   -1,   -1,   42,   43,   -1,   45,   46,
   47,  274,  275,  276,  277,  278,   -1,  280,  281,  282,
  283,  284,  285,   60,   -1,   62,   -1,   -1,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,  280,  281,  282,  283,  284,  285,
   59,   60,   37,   62,   91,   -1,   93,   42,   43,   -1,
   45,   46,   47,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   59,   60,   -1,   62,   -1,   -1,
   -1,   -1,   91,   -1,   -1,   59,   60,   37,   62,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,   91,   -1,   -1,   59,
   60,   -1,   62,   -1,   -1,   -1,   -1,   91,  272,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   37,   45,   46,
   47,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   91,   59,   60,   -1,   62,   -1,   -1,   -1,   59,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,  280,
  281,  282,  283,  284,  285,   -1,  280,  281,  282,  283,
  284,  285,   -1,   -1,   91,   -1,   -1,   37,   -1,   -1,
   -1,   91,   42,   43,   37,   45,   46,   47,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   60,   41,   62,   43,   44,   45,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,  257,  258,  259,   -1,   58,   59,
   60,   61,   62,  266,  267,  268,   -1,   -1,   -1,  272,
   -1,   91,   -1,   -1,   -1,   -1,   -1,   41,   91,   43,
   44,   45,   -1,  280,  281,  282,  283,  284,  285,   41,
   -1,   -1,   44,   93,   58,   59,   60,   61,   62,   -1,
   -1,   41,   -1,   -1,   44,   -1,   58,   59,   60,   61,
   62,  280,  281,  282,  283,  284,  285,   -1,   58,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,  280,  281,  282,  283,  284,
  285,   93,   41,   -1,   -1,   44,  280,  281,  282,  283,
  284,  285,   -1,   93,   -1,   -1,   -1,   -1,   -1,   58,
   59,   60,   61,   62,   41,   -1,   -1,   44,   -1,   -1,
  280,  281,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   61,   62,   -1,   -1,   -1,   33,
   -1,   -1,   -1,   -1,   93,   -1,   40,   -1,   -1,   -1,
   -1,   45,   -1,  280,  281,  282,  283,  284,  285,   -1,
  280,  281,  282,  283,  284,  285,   93,   -1,   -1,   -1,
   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,   58,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
  280,  281,  282,  283,  284,  285,   -1,   -1,   -1,  282,
  283,  284,  285,   93,  274,  275,  276,  277,  278,  123,
  280,  281,  282,  283,  284,  285,   93,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   -1,   61,   -1,   -1,
  274,  275,  276,  277,  278,   -1,  280,  281,  282,  283,
  284,  285,  274,  275,  276,  277,  278,   -1,  280,  281,
  282,  283,  284,  285,  274,  275,  276,  277,  278,   93,
  280,  281,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,
   61,   -1,   -1,   -1,   -1,  274,  275,  276,  277,  278,
   33,  280,  281,  282,  283,  284,  285,   40,   -1,   -1,
   -1,   -1,   45,   -1,   -1,   -1,   -1,  274,  275,  276,
  277,  278,   93,  280,  281,  282,  283,  284,  285,   -1,
   -1,   33,   -1,  257,  258,  259,  260,  261,   40,  263,
  264,   -1,   -1,   45,   -1,   -1,  270,   33,  272,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   45,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  274,  275,  276,  277,  278,   -1,
  280,  281,  282,  283,  284,  285,   -1,  274,  275,  276,
  277,  278,  125,  280,  281,  282,  283,  284,  285,   33,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,
   -1,   45,   -1,   -1,   -1,   -1,   -1,   -1,   33,   -1,
   -1,  123,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,
  274,  275,  276,  277,  278,   -1,  280,  281,   33,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   33,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   45,   -1,   -1,   -1,   33,   -1,   -1,   -1,   -1,  123,
   -1,   40,   -1,   -1,   -1,   -1,   45,   -1,   -1,   -1,
   -1,   -1,   -1,  274,  275,  276,  277,  278,  123,  280,
  281,   33,   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,
   -1,   -1,   -1,   45,  257,  258,  259,  260,  261,   33,
  263,  264,   -1,   -1,   -1,   -1,   40,  270,   -1,  272,
  125,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  125,  263,  264,   -1,   -1,   -1,   -1,   -1,  270,   -1,
  272,  257,  258,  259,  260,  261,  125,  263,  264,   -1,
   33,   -1,   -1,   -1,  270,   -1,  272,   40,   -1,   -1,
   -1,   -1,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,   -1,  263,
  264,   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,   -1,
   -1,   -1,  257,  258,  259,  260,  261,   -1,  263,  264,
   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,   -1,  263,  264,
   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,   -1,  263,  264,
   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,  257,  258,
  259,  260,  261,   -1,  263,  264,   -1,   -1,   -1,   -1,
   -1,  270,   -1,  272,   -1,   -1,   56,   -1,   58,   59,
   -1,   -1,   62,   63,   64,  257,  258,  259,  260,  261,
   -1,  263,  264,   -1,   -1,   -1,   -1,   -1,  270,   -1,
  272,   -1,   -1,  257,  258,  259,  260,  261,   88,  263,
  264,   91,   92,   -1,   -1,   -1,  270,   -1,  272,   -1,
   -1,   -1,  102,  103,  104,  105,  106,  107,  108,  109,
  110,  111,  112,  113,  114,  115,  116,  117,  118,  119,
  120,   -1,  122,   -1,  124,   -1,  126,   -1,   -1,   -1,
   -1,   -1,   -1,  133,  257,  258,  259,  260,  261,   -1,
  263,  264,   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"INT_CONSTANT","REAL_CONSTANT",
"CHAR_CONSTANT","INPUT","PRINT","DEF","WHILE","IF","ELSE","INT","DOUBLE","CHAR",
"STRUCT","RETURN","VOID","ID","MAIN","PLUS_EQUAL","MINUS_EQUAL","MUL_EQUAL",
"DIV_EQUAL","MOD_EQUAL","MENOR_QUE_ELSE","AND","OR","GREATER_OR_EQUAL",
"LESS_OR_EQUAL","DISTINCT","EQUALS","UNARY_MINUS","CAST","PARENTESIS",
};
final static String yyrule[] = {
"$accept : program",
"program : _program main",
"_program :",
"_program : _program variable_definition",
"_program : _program function_definition",
"$$1 :",
"main : DEF $$1 MAIN '(' ')' ':' VOID '{' function_body '}'",
"variable_definition : variables ':' type ';'",
"variables : ID",
"variables : variables ',' ID",
"$$2 :",
"function_definition : DEF $$2 ID '(' parameters ')' ':' return_type '{' function_body '}'",
"parameters :",
"parameters : _parameters",
"_parameters : parameter",
"_parameters : _parameters ',' parameter",
"parameter : ID ':' simple_type",
"return_type : simple_type",
"return_type : VOID",
"function_body :",
"function_body : variable_definitions statements",
"function_body : variable_definitions",
"function_body : statements",
"variable_definitions : variable_definition",
"variable_definitions : variable_definitions variable_definition",
"statements : statement",
"statements : statements statement",
"statement : assignment",
"statement : function_call_as_statement",
"statement : return",
"statement : while",
"statement : if",
"statement : read",
"statement : write",
"statement : plusEqual",
"statement : minusEqual",
"statement : mulEqual",
"statement : divEqual",
"statement : modEqual",
"type : simple_type",
"type : array",
"type : struct",
"simple_type : INT",
"simple_type : DOUBLE",
"simple_type : CHAR",
"array : '[' INT_CONSTANT ']' type",
"struct : STRUCT '{' struct_body '}'",
"struct_body : variable_definition",
"struct_body : struct_body variable_definition",
"assignment : expression '=' expression ';'",
"read : INPUT expressions ';'",
"$$3 :",
"write : PRINT $$3 expressions ';'",
"expressions : expression",
"expressions : expressions ',' expression",
"while : WHILE expression ':' '{' statements '}'",
"while : WHILE expression ':' statement",
"if : IF expression ':' '{' statements '}'",
"if : IF expression ':' '{' statements '}' ELSE '{' statements '}'",
"if : IF expression ':' '{' statements '}' ELSE statement",
"if : IF expression ':' statement",
"if : IF expression ':' statement ELSE '{' statements '}'",
"if : IF expression ':' statement ELSE statement",
"$$4 :",
"return : RETURN $$4 expression ';'",
"parameters_in_function_call :",
"parameters_in_function_call : expressions",
"function_call_as_statement : ID '(' parameters_in_function_call ')' ';'",
"plusEqual : expression PLUS_EQUAL expression ';'",
"minusEqual : expression MINUS_EQUAL expression ';'",
"mulEqual : expression MUL_EQUAL expression ';'",
"divEqual : expression DIV_EQUAL expression ';'",
"modEqual : expression MOD_EQUAL expression ';'",
"function_call_as_expression : ID '(' parameters_in_function_call ')'",
"expression : expression AND expression",
"expression : expression OR expression",
"expression : expression '>' expression",
"expression : expression GREATER_OR_EQUAL expression",
"expression : expression '<' expression",
"expression : expression LESS_OR_EQUAL expression",
"expression : expression DISTINCT expression",
"expression : expression EQUALS expression",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression '*' expression",
"expression : expression '/' expression",
"expression : expression '%' expression",
"expression : '!' expression",
"expression : '-' expression",
"expression : '(' simple_type ')' expression",
"expression : expression '.' ID",
"expression : expression '[' expression ']'",
"expression : '(' expression ')'",
"expression : function_call_as_expression",
"expression : INT_CONSTANT",
"expression : REAL_CONSTANT",
"expression : CHAR_CONSTANT",
"expression : ID",
};

//#line 428 "../../src/parser/parser.y"


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
	    // System.err.println ("Lexical error at line " + scanner.getLine() + " and column "+scanner.getColumn()+":\n\t"+e);
	    // Esto es cuando se produce un error en el lexico. Cuando casca, no cuando detecta un error lexico.
	    new ErrorType(scanner.getLine(), scanner.getColumn(), "Lexical error: " + e); 
    }
    return token;
}

// * Manejo de Errores Sintácticos
public void yyerror (String error) {
    // System.err.println ("Syntactical error at line " + scanner.getLine() + " and column "+scanner.getColumn()+":\n\t"+error);
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

//#line 751 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 68 "../../src/parser/parser.y"
{
																		List<Definition> definitions = (List<Definition>) val_peek(1);
																		Definition main = (Definition) val_peek(0);
																		definitions.add(main);
																		ast = new Program(scanner.getLine(), scanner.getColumn(), definitions);																			
																	}
break;
case 2:
//#line 76 "../../src/parser/parser.y"
{yyval = new LinkedList<Definition>();}
break;
case 3:
//#line 77 "../../src/parser/parser.y"
{List<Definition> definitions = (List<Definition>) val_peek(1); definitions.addAll((List<Definition>) val_peek(0)); yyval = definitions;}
break;
case 4:
//#line 78 "../../src/parser/parser.y"
{List<Definition> definitions = (List<Definition>) val_peek(1); definitions.add((Definition) val_peek(0)); yyval = definitions;}
break;
case 5:
//#line 81 "../../src/parser/parser.y"
{funDefTempLine = scanner.getLine();}
break;
case 6:
//#line 82 "../../src/parser/parser.y"
{
																		FunctionType functionType = new FunctionType(funDefTempLine, 1, new LinkedList<VarDefinition>(), VoidType.getInstance());
																		yyval = new FunDefinition(funDefTempLine, 1, "main", functionType, (List<Statement>) val_peek(1));
																	}
break;
case 7:
//#line 89 "../../src/parser/parser.y"
{	/* Una definicion de variable puede tener varias variables, y tenemos que dividirlo en varias VarDefinition*/
																		List<VarDefinition> varDefinitions = new LinkedList<VarDefinition>();
																		
																		List<String> vars = (List<String>) val_peek(3);
																		for(String var : vars){
																			VarDefinition varDef = new VarDefinition(scanner.getLine(), scanner.getColumn(), var, (Type) val_peek(1));
																			varDefinitions.add(varDef);
																		}
																		yyval = varDefinitions;
																	}
break;
case 8:
//#line 101 "../../src/parser/parser.y"
{	
																		List<String> identifiers = new LinkedList<String>();
																		identifiers.add( (String) val_peek(0) );
																		yyval = identifiers;
																	}
break;
case 9:
//#line 106 "../../src/parser/parser.y"
{	
																		List<String> identifiers = (List<String>) val_peek(2);
																		String identifier = (String) val_peek(0);
																		
																		/* Si el identificador ya está en la lista, es un error semántico (lo detectamos en esta fase que es más fácil). */
																		if(identifiers.contains(identifier))
																			new ErrorType(scanner.getLine(), scanner.getColumn(), 
																				"Semantical error: The identifier '"+ identifier +"' has already been used in the same variable definition.");
																		
																		identifiers.add( identifier );
																		yyval = identifiers;
																	}
break;
case 10:
//#line 122 "../../src/parser/parser.y"
{funDefTempLine = scanner.getLine();}
break;
case 11:
//#line 124 "../../src/parser/parser.y"
{
																		FunctionType functionType = new FunctionType(funDefTempLine, 1, (List<VarDefinition>) val_peek(6), (Type) val_peek(3));
																		yyval = new FunDefinition(funDefTempLine, 1, (String) val_peek(8), functionType, (List<Statement>) val_peek(1));
																	}
break;
case 12:
//#line 130 "../../src/parser/parser.y"
{yyval = new LinkedList<VarDefinition>();}
break;
case 13:
//#line 131 "../../src/parser/parser.y"
{yyval = (List<VarDefinition>) val_peek(0);}
break;
case 14:
//#line 134 "../../src/parser/parser.y"
{List<VarDefinition> parameters = new LinkedList<VarDefinition>(); parameters.add((VarDefinition) val_peek(0)); yyval = parameters;}
break;
case 15:
//#line 135 "../../src/parser/parser.y"
{List<VarDefinition> parameters = (List<VarDefinition>) val_peek(2); parameters.add((VarDefinition) val_peek(0)); yyval = parameters;}
break;
case 16:
//#line 138 "../../src/parser/parser.y"
{yyval = new VarDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(2), (Type) val_peek(0));}
break;
case 17:
//#line 141 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 18:
//#line 142 "../../src/parser/parser.y"
{yyval = VoidType.getInstance();}
break;
case 19:
//#line 147 "../../src/parser/parser.y"
{yyval = new LinkedList<Statement>();}
break;
case 20:
//#line 148 "../../src/parser/parser.y"
{	
																		List<Statement> varDefinitions = (List<Statement>) val_peek(1); List<Statement> statements = (List<Statement>) val_peek(0); 
																		varDefinitions.addAll(statements); /* Añadimos los statements al final de la lista de varDefinitions*/
																		yyval = varDefinitions; 
																	}
break;
case 21:
//#line 153 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 22:
//#line 154 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 23:
//#line 157 "../../src/parser/parser.y"
{List<VarDefinition> varDefinitions = new LinkedList<VarDefinition>(); varDefinitions.addAll((List<VarDefinition>) val_peek(0)); yyval = varDefinitions;}
break;
case 24:
//#line 158 "../../src/parser/parser.y"
{List<VarDefinition> varDefinitions = (List<VarDefinition>) val_peek(1); varDefinitions.addAll((List<VarDefinition>) val_peek(0)); yyval = varDefinitions;}
break;
case 25:
//#line 161 "../../src/parser/parser.y"
{List<Statement> statements = new LinkedList<Statement>(); statements.addAll((List<Statement>) val_peek(0)); yyval = statements;}
break;
case 26:
//#line 162 "../../src/parser/parser.y"
{List<Statement> statements = (List<Statement>) val_peek(1); statements.addAll((List<Statement>) val_peek(0)); yyval = statements;}
break;
case 27:
//#line 166 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 28:
//#line 167 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 29:
//#line 168 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 30:
//#line 169 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 31:
//#line 170 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 32:
//#line 171 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 33:
//#line 172 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 34:
//#line 173 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 35:
//#line 174 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 36:
//#line 175 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 37:
//#line 176 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 38:
//#line 177 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 39:
//#line 181 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 40:
//#line 182 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 41:
//#line 183 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 42:
//#line 186 "../../src/parser/parser.y"
{yyval = IntType.getInstance();}
break;
case 43:
//#line 187 "../../src/parser/parser.y"
{yyval = RealType.getInstance();}
break;
case 44:
//#line 188 "../../src/parser/parser.y"
{yyval = CharType.getInstance();}
break;
case 45:
//#line 191 "../../src/parser/parser.y"
{yyval = new ArrayType(scanner.getLine(), scanner.getColumn(), (int) val_peek(2), (Type) val_peek(0));}
break;
case 46:
//#line 194 "../../src/parser/parser.y"
{yyval = new RecordType(scanner.getLine(), scanner.getColumn(), (List<RecordField>) val_peek(1));}
break;
case 47:
//#line 200 "../../src/parser/parser.y"
{	
																		List<RecordField> fields = new LinkedList<RecordField>(); 
																		List<VarDefinition> varDefinitions = (List<VarDefinition>) val_peek(0);
																		
																		for(VarDefinition varDefinition : varDefinitions){
																			RecordField field = new RecordField(varDefinition);
																			fields.add(field);
																		}
																		yyval = fields;
																	}
break;
case 48:
//#line 210 "../../src/parser/parser.y"
{	
																		List<RecordField> fields = (List<RecordField>) val_peek(1); 
																		List<VarDefinition> varDefinitions = (List<VarDefinition>) val_peek(0);
																		
																		for(VarDefinition varDefinition : varDefinitions){
																			RecordField field = new RecordField(varDefinition);
																			
																			/* Si el campo ya está en la lista, es un error semántico (lo detectamos en esta fase que es más fácil). */
																			if(fields.contains(field))
																				new ErrorType(field, "Semantical error: The record field '" + field.name + "' has already been defined in the struct.");
																			
																			fields.add(field);
																		}
																		yyval = fields;
																	}
break;
case 49:
//#line 228 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));
  																		yyval = asStatementList(assignment);
																	}
break;
case 50:
//#line 234 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) val_peek(1);
																		for(Expression expression : expressions){
																			statements.add( new Read(scanner.getLine(), scanner.getColumn(), expression) );
																		}
																		yyval = statements;
																	}
break;
case 51:
//#line 245 "../../src/parser/parser.y"
{writeTempLine = scanner.getLine();}
break;
case 52:
//#line 245 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) val_peek(1);
																		for(Expression expression : expressions){
																			statements.add( new Write(writeTempLine, scanner.getColumn(), expression) );
																		}
																		yyval = statements;
																	}
break;
case 53:
//#line 257 "../../src/parser/parser.y"
{List<Expression> expressions = new LinkedList<Expression>(); expressions.add((Expression) val_peek(0)); yyval = expressions;}
break;
case 54:
//#line 258 "../../src/parser/parser.y"
{List<Expression> expressions = (List<Expression>) val_peek(2); expressions.add((Expression) val_peek(0)); yyval = expressions;}
break;
case 55:
//#line 262 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) val_peek(1));
																		Expression expression = (Expression) val_peek(4);

																		While _while = new While(expression.getLine(), expression.getColumn(), expression, body);
  																		yyval = asStatementList(_while);
																	}
break;
case 56:
//#line 270 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) val_peek(0));
																		Expression expression = (Expression) val_peek(2);

																		While _while = new While(expression.getLine(), expression.getColumn(), expression, body);
  																		yyval = asStatementList(_while);
																	}
break;
case 57:
//#line 280 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(1));
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) val_peek(4);

																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 58:
//#line 289 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(5));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(1));
  																		Expression expression = (Expression) val_peek(8);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 59:
//#line 298 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(3));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(0));
  																		Expression expression = (Expression) val_peek(6);

																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 60:
//#line 307 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(0));
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) val_peek(2);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 61:
//#line 316 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(4));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(1));
  																		Expression expression = (Expression) val_peek(6);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody); 
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 62:
//#line 325 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(2));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(0));
  																		Expression expression = (Expression) val_peek(4);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
																		yyval = asStatementList(ifStatement);
																	}
break;
case 63:
//#line 336 "../../src/parser/parser.y"
{returnTempLine = scanner.getLine();}
break;
case 64:
//#line 336 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Return _return = new Return(returnTempLine, scanner.getColumn(), (Expression) val_peek(1));
																		yyval = asStatementList(_return);
																	}
break;
case 65:
//#line 345 "../../src/parser/parser.y"
{yyval = new LinkedList<Expression>();}
break;
case 66:
//#line 346 "../../src/parser/parser.y"
{yyval = (List<Expression>) val_peek(0);}
break;
case 67:
//#line 349 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(4));
																		Invocation invocation = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) val_peek(2)); 
																		yyval = asStatementList(invocation);	 
																	}
break;
case 68:
//#line 357 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), 
																			new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), "+", (Expression) val_peek(1)));
  																		yyval = asStatementList(assignment);
																	}
break;
case 69:
//#line 364 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), 
																			new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), "-", (Expression) val_peek(1)));
  																		yyval = asStatementList(assignment);
																	}
break;
case 70:
//#line 371 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), 
																			new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), "*", (Expression) val_peek(1)));
  																		yyval = asStatementList(assignment);
																	}
break;
case 71:
//#line 378 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), 
																			new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), "/", (Expression) val_peek(1)));
  																		yyval = asStatementList(assignment);
																	}
break;
case 72:
//#line 385 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), 
																			new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), "%", (Expression) val_peek(1)));
  																		yyval = asStatementList(assignment);
																	}
break;
case 73:
//#line 395 "../../src/parser/parser.y"
{	
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(3));
																		yyval = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) val_peek(1));
																	}
break;
case 74:
//#line 401 "../../src/parser/parser.y"
{yyval = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 75:
//#line 402 "../../src/parser/parser.y"
{yyval = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 76:
//#line 403 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 77:
//#line 404 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 78:
//#line 405 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 79:
//#line 406 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 80:
//#line 407 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 81:
//#line 408 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 82:
//#line 409 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 83:
//#line 410 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 84:
//#line 411 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 85:
//#line 412 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 86:
//#line 413 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 87:
//#line 414 "../../src/parser/parser.y"
{yyval = new UnaryNot(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 88:
//#line 415 "../../src/parser/parser.y"
{yyval = new UnaryMinus(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 89:
//#line 416 "../../src/parser/parser.y"
{yyval = new Cast(scanner.getLine(), scanner.getColumn(), (Type) val_peek(2), (Expression) val_peek(0));}
break;
case 90:
//#line 417 "../../src/parser/parser.y"
{yyval = new FieldAccess(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(0));}
break;
case 91:
//#line 418 "../../src/parser/parser.y"
{yyval = new Indexing(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));}
break;
case 92:
//#line 419 "../../src/parser/parser.y"
{yyval = (Expression) val_peek(1);}
break;
case 93:
//#line 420 "../../src/parser/parser.y"
{yyval = (Invocation) val_peek(0);}
break;
case 94:
//#line 421 "../../src/parser/parser.y"
{yyval = new IntLiteral(scanner.getLine(), scanner.getColumn(), (int) val_peek(0));}
break;
case 95:
//#line 422 "../../src/parser/parser.y"
{yyval = new RealLiteral(scanner.getLine(), scanner.getColumn(), (double) val_peek(0));}
break;
case 96:
//#line 423 "../../src/parser/parser.y"
{yyval = new CharLiteral(scanner.getLine(), scanner.getColumn(), (char) val_peek(0));}
break;
case 97:
//#line 424 "../../src/parser/parser.y"
{yyval = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(0));}
break;
//#line 1453 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
