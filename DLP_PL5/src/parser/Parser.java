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
import ast.errors.*;
//#line 34 "Parser.java"




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
public final static short MENOR_QUE_ELSE=274;
public final static short AND=275;
public final static short OR=276;
public final static short GREATER_OR_EQUAL=277;
public final static short LESS_OR_EQUAL=278;
public final static short DISTINCT=279;
public final static short EQUALS=280;
public final static short UNARY_MINUS=281;
public final static short CAST=282;
public final static short PARENTESIS=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    3,    6,    6,    4,    8,
    8,   10,   10,   11,    9,    9,    5,    5,    5,    5,
   13,   13,   14,   14,   15,   15,   15,   15,   15,   15,
   15,    7,    7,    7,   12,   12,   12,   23,   24,   25,
   25,   16,   21,   22,   27,   27,   19,   19,   20,   20,
   20,   20,   20,   20,   18,   28,   28,   17,   29,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   26,
};
final static short yylen[] = {                            2,
    2,    0,    2,    2,    9,    4,    1,    3,   10,    0,
    1,    1,    3,    3,    1,    1,    0,    2,    1,    1,
    1,    2,    1,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    4,    4,    1,
    2,    4,    3,    3,    1,    3,    6,    4,    6,   10,
    8,    4,    8,    6,    3,    0,    1,    5,    4,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    4,    3,    4,    3,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    7,    1,    3,    4,    0,    0,    0,
    0,    0,    0,    0,   35,   36,   37,    0,    0,    0,
   32,   33,   34,    8,    0,    0,    0,   12,    0,    0,
    0,    6,    0,    0,    0,    0,   40,    0,    0,   14,
    0,   13,    0,   39,   41,   38,   16,    0,   15,    0,
    0,   80,   81,   82,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   21,    0,    0,    0,   23,   25,   26,
   27,   28,   29,   30,   31,    0,   79,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    5,   22,    0,    0,   24,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    9,    0,   43,    0,   44,    0,    0,   55,    0,
    0,    0,   78,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   76,    0,    0,
    0,    0,   48,    0,    0,    0,    0,   42,   77,   59,
    0,    0,    0,   58,   47,    0,    0,   54,    0,    0,
    0,   51,   53,    0,   50,
};
final static short yydgoto[] = {                          1,
    2,    5,   64,    7,   65,    8,   20,   26,   48,   27,
   28,   21,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   75,   22,   23,   38,   76,  120,  121,   77,
};
final static short yysindex[] = {                         0,
    0, -234, -227,    0,    0,    0,    0,  -31,  -29,  -25,
  -90, -243, -237,   -2,    0,    0,    0,  -82, -214,    6,
    0,    0,    0,    0,   13,   32,   35,    0,   23, -184,
   22,    0, -176,   49, -237, -155,    0, -111,  -90,    0,
 -115,    0,   -6,    0,    0,    0,    0,    1,    0,  763,
  763,    0,    0,    0,  658,  658,  658,  658,  658,   86,
  658,  658,  691,    0,    2,  763,  780,    0,    0,    0,
    0,    0,    0,    0,    0,  123,    0,    3,   96,  469,
  -42,  -41,  153,  160,  186,  658,  -27,  -27,  122,  216,
    0,    0,  780,   86,    0,  658,  658,  658,  658,  658,
  658,  658,  658,  658,  658,  658,  658,  658,  658, -135,
  658,    0,  658,    0,  658,    0,  495,  534,    0,   94,
  132,  658,    0,  350,  496,  496,  343,  343,  343,  343,
  343,  343,  125,  125,  -27,  -27,  -27,    0,  407,  133,
  469,  780,    0,  780, -101,  116,  -27,    0,    0,    0,
  635,  682,  591,    0,    0,  -85,  780,    0,  661,  717,
  780,    0,    0,  813,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  141,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  146,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   66,
   66,    0,    0,    0,    0,    0,    0,    0,    0,   97,
    0,    0,    0,    0,    0,   67,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -37,   -4,
    0,    0,    0,    0,    0,  167,  -11,   16,    0,    0,
    0,    0,   76,  441,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  167,    0,    0,    0,    0,    0,    0,  168,
    0,    0,    0,    0,  301,  903,  510,  539,  551,  561,
  604,  611,  363,  503,   25,   52,   61,    0,    0,    0,
  145,    0,    0,    0,  831,  462,   88,    0,    0,    0,
    0,    0,    0,    0,    0,  868,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   14,    0,  159,    0,  173,    0,    0,    0,
  182,  -21,    0,  128,  157,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 1049,   45,  106,    0,
};
final static int YYTABLESIZE=1179;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
   19,  115,  115,   83,   83,   83,   83,   83,   83,   83,
   13,   40,   12,   44,   14,    6,  114,  116,  110,   49,
   83,   83,   83,   83,   83,   74,   11,    3,   24,   74,
   74,   74,   74,   74,   25,   74,   45,    4,   29,   45,
   30,   89,   31,   37,    9,   10,   74,   74,   74,   74,
   74,   45,   73,   83,   45,   83,   73,   73,   73,   73,
   73,   70,   73,  111,   32,   70,   70,   70,   70,   70,
   33,   70,   34,   73,   73,   73,   73,   73,   35,   92,
   36,   74,   70,   70,   70,   70,   70,    4,   71,   15,
   16,   17,   71,   71,   71,   71,   71,   72,   71,   81,
   82,   72,   72,   72,   72,   72,   41,   72,   73,   71,
   71,   71,   71,   71,   39,   43,   50,   70,   72,   72,
   72,   72,   72,   51,   75,   86,   91,  112,   75,   75,
   75,   75,   75,   83,   75,  113,  138,  115,   83,   83,
    7,   83,   83,   83,   71,   75,   75,   75,   75,   75,
   15,   16,   17,   72,    7,   47,   83,   83,   83,  109,
    4,  109,  122,  153,  107,  105,  107,  106,  110,  108,
  110,  108,  146,  150,  154,   15,   16,   17,   18,  159,
   75,   10,  101,   96,   99,   46,   11,   83,   46,  109,
   17,   19,   20,   93,  107,  105,  109,  106,  110,  108,
   18,  107,  105,   46,  106,  110,  108,   56,   57,   78,
  117,   46,  101,  111,   99,  111,   42,  118,  140,  101,
    0,   99,  109,   95,    0,    0,    0,  107,  105,    0,
  106,  110,  108,    0,    0,    0,    0,   83,   83,   83,
   83,   83,   83,  111,  119,  101,    0,   99,    0,   95,
  111,    0,  109,    0,    0,    0,  123,  107,  105,    0,
  106,  110,  108,   74,   74,   74,   74,   74,   74,  151,
    0,  152,    0,  143,  145,  101,  111,   99,    0,    0,
    0,    0,    0,    0,  160,    0,    0,    0,  164,    0,
   73,   73,   73,   73,   73,   73,    0,    0,    0,   70,
   70,   70,   70,   70,   70,    0,  111,   95,   95,  158,
    0,    0,    0,    0,    0,  162,   95,    0,    0,    0,
   95,    0,    0,    0,    0,    0,   71,   71,   71,   71,
   71,   71,    0,    0,    0,   72,   72,   72,   72,   72,
   72,   60,    0,    0,   60,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   60,   60,
    0,   60,   75,   75,   75,   75,   75,   75,    0,    0,
    0,   83,   83,   83,   83,   83,   83,    0,    0,  109,
    0,    0,    0,    0,  107,  105,  109,  106,  110,  108,
    0,  107,  105,   60,  106,  110,  108,   97,   98,  100,
  102,  103,  104,   68,    0,   68,   68,   68,  148,  101,
    0,   99,    0,    0,    0,    0,    0,    0,    0,    0,
   68,   68,   68,   68,   68,    0,    0,   97,   98,  100,
  102,  103,  104,  111,   97,   98,  100,  102,  103,  104,
  111,    0,    0,  109,    0,    0,    0,    0,  107,  105,
    0,  106,  110,  108,    0,   68,    0,    0,    0,    0,
   97,   98,  100,  102,  103,  104,  101,    0,   99,    0,
    0,    0,    0,    0,    0,    0,    0,   83,    0,    0,
    0,    0,   83,   83,    0,   83,   83,   83,    0,    0,
   97,   98,  100,  102,  103,  104,    0,  111,   59,  149,
   83,   83,   83,   59,   59,  109,   59,   59,   59,    0,
  107,  105,    0,  106,  110,  108,    0,    0,    0,    0,
    0,   59,   59,   59,    0,    0,    0,   62,  101,    0,
   99,   83,  109,    0,   63,    0,    0,  107,  105,   61,
  106,  110,  108,   69,    0,   69,   69,   69,    0,    0,
   62,    0,   59,   62,    0,  101,    0,   99,    0,  111,
   69,   69,   69,   69,   69,    0,   62,   62,   62,   62,
   62,   62,    0,   63,    0,   60,   60,    0,   61,   63,
    0,    0,   63,    0,    0,    0,  111,    0,    0,    0,
    0,   64,    0,    0,   64,   69,   63,   63,   63,   63,
   63,   65,   62,    0,   65,    0,    0,    0,   64,   64,
   64,   64,   64,    0,    0,    0,    0,  142,   65,   65,
   65,   65,   65,   62,   97,   98,  100,  102,  103,  104,
   63,   63,    0,    0,    0,   61,    0,   68,   68,   68,
   68,   68,   68,   64,   66,    0,    0,   66,    0,    0,
    0,   67,    0,   65,   67,    0,  144,    0,    0,    0,
    0,   66,   66,   66,   66,   66,    0,   62,   67,   67,
   67,   67,   67,    0,   63,    0,    0,    0,    0,   61,
    0,   97,   98,  100,  102,  103,  104,    0,    0,    0,
   62,    0,    0,   62,    0,    0,   66,   63,    0,    0,
   63,    0,   61,   67,    0,   61,    0,    0,    0,    0,
    0,    0,    0,  157,   62,   83,   83,   83,   83,   83,
   83,   63,    0,   62,    0,    0,   61,    0,    0,    0,
   63,    0,    0,    0,    0,   61,   59,   59,   59,   59,
   59,   59,    0,   97,   98,  100,  102,  103,  104,   62,
    0,   52,   53,   54,   55,   56,   63,   57,   58,  155,
    0,   61,    0,    0,   59,    0,   94,    0,    0,    0,
    0,    0,  100,  102,  103,  104,    0,   69,   69,   69,
   69,   69,   69,  161,   62,   62,   62,   62,   62,   62,
   52,   53,   54,   55,   56,   62,   57,   58,    0,    0,
    0,    0,   63,   59,    0,   94,  156,   61,    0,    0,
    0,    0,   62,   63,   63,   63,   63,   63,   63,   63,
    0,    0,    0,    0,   61,   64,   64,   64,   64,   64,
   64,    0,    0,    0,    0,   65,   65,   65,   65,   65,
   65,  163,    0,    0,    0,   62,    0,   52,   53,   54,
   55,   56,   63,   57,   58,    0,    0,   61,    0,    0,
   59,    0,   94,   52,    0,    0,    0,    0,    0,    0,
   52,    0,    0,    0,    0,   52,    0,    0,   66,   66,
   66,   66,   66,   66,    0,   67,   67,   67,   67,   67,
   67,   52,   53,   54,   55,   56,    0,   57,   58,    0,
   49,    0,    0,    0,   59,    0,   94,   49,    0,    0,
    0,    0,   49,    0,   52,   53,   54,   52,   53,   54,
   55,   56,    0,   57,   58,    0,    0,    0,    0,   79,
   59,    0,   94,    0,    0,    0,    0,  165,   52,   53,
   54,   55,   56,   61,   57,   58,   61,   52,   53,   54,
    0,   59,    0,   94,    0,   52,   15,   16,   17,    0,
   61,   61,   79,   61,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   52,   53,   54,   55,   56,    0,   57,
   58,    0,    0,    0,    0,    0,   59,    0,   94,    0,
    0,    0,   49,    0,    0,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   52,
   53,   54,   55,   56,    0,   57,   58,    0,    0,    0,
    0,    0,   59,    0,   60,    0,   52,   53,   54,   55,
   56,    0,   57,   58,    0,    0,    0,    0,    0,   59,
    0,   94,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   52,
   53,   54,   55,   56,    0,   57,   58,    0,    0,    0,
    0,    0,   59,    0,   94,    0,    0,   52,   52,   52,
   52,   52,    0,   52,   52,    0,    0,    0,    0,    0,
   52,    0,   52,   80,   80,   83,   84,   85,    0,   87,
   88,   90,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   49,   49,   49,   49,   49,    0,
   49,   49,    0,    0,   80,    0,    0,   49,    0,   49,
    0,    0,    0,    0,  124,  125,  126,  127,  128,  129,
  130,  131,  132,  133,  134,  135,  136,  137,    0,  139,
    0,   80,    0,  141,    0,    0,    0,    0,    0,    0,
  147,    0,    0,    0,    0,    0,    0,   61,   61,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
   91,   44,   44,   41,   42,   43,   44,   45,   46,   47,
   40,   33,   44,  125,   40,    2,   59,   59,   46,   41,
   58,   59,   60,   61,   62,   37,   58,  262,  272,   41,
   42,   43,   44,   45,  272,   47,   41,  272,   41,   44,
  123,   63,  257,   30,  272,  273,   58,   59,   60,   61,
   62,   38,   37,   91,   59,   93,   41,   42,   43,   44,
   45,   37,   47,   91,   59,   41,   42,   43,   44,   45,
   58,   47,   41,   58,   59,   60,   61,   62,   44,   66,
   58,   93,   58,   59,   60,   61,   62,  272,   37,  266,
  267,  268,   41,   42,   43,   44,   45,   37,   47,   55,
   56,   41,   42,   43,   44,   45,   58,   47,   93,   58,
   59,   60,   61,   62,   93,  271,  123,   93,   58,   59,
   60,   61,   62,  123,   37,   40,  125,  125,   41,   42,
   43,   44,   45,   37,   47,   40,  272,   44,   42,   43,
   44,   45,   46,   47,   93,   58,   59,   60,   61,   62,
  266,  267,  268,   93,   58,  271,   60,   61,   62,   37,
  272,   37,   41,  265,   42,   43,   42,   45,   46,   47,
   46,   47,   41,   41,   59,  266,  267,  268,  269,  265,
   93,   41,   60,   61,   62,   41,   41,   91,   44,   37,
  125,  125,  125,   66,   42,   43,   37,   45,   46,   47,
  125,   42,   43,   59,   45,   46,   47,   41,   41,   51,
   58,   39,   60,   91,   62,   91,   35,   58,  113,   60,
   -1,   62,   37,   67,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,  275,  276,  277,
  278,  279,  280,   91,   59,   60,   -1,   62,   -1,   93,
   91,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,  275,  276,  277,  278,  279,  280,  142,
   -1,  144,   -1,  117,  118,   60,   91,   62,   -1,   -1,
   -1,   -1,   -1,   -1,  157,   -1,   -1,   -1,  161,   -1,
  275,  276,  277,  278,  279,  280,   -1,   -1,   -1,  275,
  276,  277,  278,  279,  280,   -1,   91,  151,  152,  153,
   -1,   -1,   -1,   -1,   -1,  159,  160,   -1,   -1,   -1,
  164,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
  279,  280,   -1,   -1,   -1,  275,  276,  277,  278,  279,
  280,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,
   -1,   61,  275,  276,  277,  278,  279,  280,   -1,   -1,
   -1,  275,  276,  277,  278,  279,  280,   -1,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   37,   45,   46,   47,
   -1,   42,   43,   93,   45,   46,   47,  275,  276,  277,
  278,  279,  280,   41,   -1,   43,   44,   45,   59,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   58,   59,   60,   61,   62,   -1,   -1,  275,  276,  277,
  278,  279,  280,   91,  275,  276,  277,  278,  279,  280,
   91,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   93,   -1,   -1,   -1,   -1,
  275,  276,  277,  278,  279,  280,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
  275,  276,  277,  278,  279,  280,   -1,   91,   37,   93,
   60,   61,   62,   42,   43,   37,   45,   46,   47,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   60,   61,   62,   -1,   -1,   -1,   33,   60,   -1,
   62,   91,   37,   -1,   40,   -1,   -1,   42,   43,   45,
   45,   46,   47,   41,   -1,   43,   44,   45,   -1,   -1,
   41,   -1,   91,   44,   -1,   60,   -1,   62,   -1,   91,
   58,   59,   60,   61,   62,   -1,   33,   58,   59,   60,
   61,   62,   -1,   40,   -1,  275,  276,   -1,   45,   41,
   -1,   -1,   44,   -1,   -1,   -1,   91,   -1,   -1,   -1,
   -1,   41,   -1,   -1,   44,   93,   58,   59,   60,   61,
   62,   41,   93,   -1,   44,   -1,   -1,   -1,   58,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,  123,   58,   59,
   60,   61,   62,   33,  275,  276,  277,  278,  279,  280,
   40,   93,   -1,   -1,   -1,   45,   -1,  275,  276,  277,
  278,  279,  280,   93,   41,   -1,   -1,   44,   -1,   -1,
   -1,   41,   -1,   93,   44,   -1,  123,   -1,   -1,   -1,
   -1,   58,   59,   60,   61,   62,   -1,   33,   58,   59,
   60,   61,   62,   -1,   40,   -1,   -1,   -1,   -1,   45,
   -1,  275,  276,  277,  278,  279,  280,   -1,   -1,   -1,
   33,   -1,   -1,   33,   -1,   -1,   93,   40,   -1,   -1,
   40,   -1,   45,   93,   -1,   45,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   33,  275,  276,  277,  278,  279,
  280,   40,   -1,   33,   -1,   -1,   45,   -1,   -1,   -1,
   40,   -1,   -1,   -1,   -1,   45,  275,  276,  277,  278,
  279,  280,   -1,  275,  276,  277,  278,  279,  280,   33,
   -1,  257,  258,  259,  260,  261,   40,  263,  264,  125,
   -1,   45,   -1,   -1,  270,   -1,  272,   -1,   -1,   -1,
   -1,   -1,  277,  278,  279,  280,   -1,  275,  276,  277,
  278,  279,  280,  123,  275,  276,  277,  278,  279,  280,
  257,  258,  259,  260,  261,   33,  263,  264,   -1,   -1,
   -1,   -1,   40,  270,   -1,  272,  125,   45,   -1,   -1,
   -1,   -1,   33,  275,  276,  277,  278,  279,  280,   40,
   -1,   -1,   -1,   -1,   45,  275,  276,  277,  278,  279,
  280,   -1,   -1,   -1,   -1,  275,  276,  277,  278,  279,
  280,  125,   -1,   -1,   -1,   33,   -1,  257,  258,  259,
  260,  261,   40,  263,  264,   -1,   -1,   45,   -1,   -1,
  270,   -1,  272,   33,   -1,   -1,   -1,   -1,   -1,   -1,
   40,   -1,   -1,   -1,   -1,   45,   -1,   -1,  275,  276,
  277,  278,  279,  280,   -1,  275,  276,  277,  278,  279,
  280,  257,  258,  259,  260,  261,   -1,  263,  264,   -1,
   33,   -1,   -1,   -1,  270,   -1,  272,   40,   -1,   -1,
   -1,   -1,   45,   -1,  257,  258,  259,  257,  258,  259,
  260,  261,   -1,  263,  264,   -1,   -1,   -1,   -1,  272,
  270,   -1,  272,   -1,   -1,   -1,   -1,  125,  257,  258,
  259,  260,  261,   41,  263,  264,   44,  257,  258,  259,
   -1,  270,   -1,  272,   -1,  125,  266,  267,  268,   -1,
   58,   59,  272,   61,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,   -1,  263,
  264,   -1,   -1,   -1,   -1,   -1,  270,   -1,  272,   -1,
   -1,   -1,  125,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,   -1,  263,  264,   -1,   -1,   -1,
   -1,   -1,  270,   -1,  272,   -1,  257,  258,  259,  260,
  261,   -1,  263,  264,   -1,   -1,   -1,   -1,   -1,  270,
   -1,  272,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,   -1,  263,  264,   -1,   -1,   -1,
   -1,   -1,  270,   -1,  272,   -1,   -1,  257,  258,  259,
  260,  261,   -1,  263,  264,   -1,   -1,   -1,   -1,   -1,
  270,   -1,  272,   55,   56,   57,   58,   59,   -1,   61,
   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,   -1,
  263,  264,   -1,   -1,   86,   -1,   -1,  270,   -1,  272,
   -1,   -1,   -1,   -1,   96,   97,   98,   99,  100,  101,
  102,  103,  104,  105,  106,  107,  108,  109,   -1,  111,
   -1,  113,   -1,  115,   -1,   -1,   -1,   -1,   -1,   -1,
  122,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=283;
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
"STRUCT","RETURN","VOID","ID","MAIN","MENOR_QUE_ELSE","AND","OR",
"GREATER_OR_EQUAL","LESS_OR_EQUAL","DISTINCT","EQUALS","UNARY_MINUS","CAST",
"PARENTESIS",
};
final static String yyrule[] = {
"$accept : program",
"program : _program main",
"_program :",
"_program : _program variable_definition",
"_program : _program function_definition",
"main : DEF MAIN '(' ')' ':' VOID '{' function_body '}'",
"variable_definition : variables ':' type ';'",
"variables : ID",
"variables : variables ',' ID",
"function_definition : DEF ID '(' parameters ')' ':' return_type '{' function_body '}'",
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
"write : PRINT expressions ';'",
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
"return : RETURN expression ';'",
"parameters_in_funcion_call :",
"parameters_in_funcion_call : expressions",
"function_call_as_statement : ID '(' parameters_in_funcion_call ')' ';'",
"function_call_as_expression : ID '(' parameters_in_funcion_call ')'",
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

//#line 375 "../../src/parser/parser.y"


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

//#line 636 "Parser.java"
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
//#line 58 "../../src/parser/parser.y"
{
																		List<Definition> definitions = (List<Definition>) val_peek(1);
																		Definition main = (Definition) val_peek(0);
																		definitions.add(main);
																		ast = new Program(scanner.getLine(), scanner.getColumn(), definitions);																			
																	}
break;
case 2:
//#line 66 "../../src/parser/parser.y"
{yyval = new LinkedList<Definition>();}
break;
case 3:
//#line 67 "../../src/parser/parser.y"
{List<Definition> definitions = (List<Definition>) val_peek(1); definitions.addAll((List<Definition>) val_peek(0)); yyval = definitions;}
break;
case 4:
//#line 68 "../../src/parser/parser.y"
{List<Definition> definitions = (List<Definition>) val_peek(1); definitions.add((Definition) val_peek(0)); yyval = definitions;}
break;
case 5:
//#line 71 "../../src/parser/parser.y"
{
																		FunctionType functionType = new FunctionType(scanner.getLine(), scanner.getColumn(), 
																														new LinkedList<VarDefinition>(), VoidType.getInstance());
																		yyval = new FunDefinition(scanner.getLine(), scanner.getColumn(), "main", functionType, (List<Statement>) val_peek(1));
																	}
break;
case 6:
//#line 79 "../../src/parser/parser.y"
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
case 7:
//#line 91 "../../src/parser/parser.y"
{	
																		List<String> identifiers = new LinkedList<String>();
																		identifiers.add( (String) val_peek(0) );
																		yyval = identifiers;
																	}
break;
case 8:
//#line 96 "../../src/parser/parser.y"
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
case 9:
//#line 114 "../../src/parser/parser.y"
{
																		FunctionType functionType = new FunctionType(scanner.getLine(), scanner.getColumn(), (List<VarDefinition>) val_peek(6), (Type) val_peek(3));
																		yyval = new FunDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(8), functionType, (List<Statement>) val_peek(1));
																	}
break;
case 10:
//#line 120 "../../src/parser/parser.y"
{yyval = new LinkedList<VarDefinition>();}
break;
case 11:
//#line 121 "../../src/parser/parser.y"
{yyval = (List<VarDefinition>) val_peek(0);}
break;
case 12:
//#line 124 "../../src/parser/parser.y"
{List<VarDefinition> parameters = new LinkedList<VarDefinition>(); parameters.add((VarDefinition) val_peek(0)); yyval = parameters;}
break;
case 13:
//#line 125 "../../src/parser/parser.y"
{List<VarDefinition> parameters = (List<VarDefinition>) val_peek(2); parameters.add((VarDefinition) val_peek(0)); yyval = parameters;}
break;
case 14:
//#line 128 "../../src/parser/parser.y"
{yyval = new VarDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(2), (Type) val_peek(0));}
break;
case 15:
//#line 131 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 16:
//#line 132 "../../src/parser/parser.y"
{yyval = VoidType.getInstance();}
break;
case 17:
//#line 137 "../../src/parser/parser.y"
{yyval = new LinkedList<Statement>();}
break;
case 18:
//#line 138 "../../src/parser/parser.y"
{	
																		List<Statement> varDefinitions = (List<Statement>) val_peek(1); List<Statement> statements = (List<Statement>) val_peek(0); 
																		varDefinitions.addAll(statements); /* Añadimos los statements al final de la lista de varDefinitions*/
																		yyval = varDefinitions; 
																	}
break;
case 19:
//#line 143 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 20:
//#line 144 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 21:
//#line 147 "../../src/parser/parser.y"
{List<VarDefinition> varDefinitions = new LinkedList<VarDefinition>(); varDefinitions.addAll((List<VarDefinition>) val_peek(0)); yyval = varDefinitions;}
break;
case 22:
//#line 148 "../../src/parser/parser.y"
{List<VarDefinition> varDefinitions = (List<VarDefinition>) val_peek(1); varDefinitions.addAll((List<VarDefinition>) val_peek(0)); yyval = varDefinitions;}
break;
case 23:
//#line 151 "../../src/parser/parser.y"
{List<Statement> statements = new LinkedList<Statement>(); statements.addAll((List<Statement>) val_peek(0)); yyval = statements;}
break;
case 24:
//#line 152 "../../src/parser/parser.y"
{List<Statement> statements = (List<Statement>) val_peek(1); statements.addAll((List<Statement>) val_peek(0)); yyval = statements;}
break;
case 25:
//#line 156 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 26:
//#line 157 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 27:
//#line 158 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 28:
//#line 159 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 29:
//#line 160 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 30:
//#line 161 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 31:
//#line 162 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 32:
//#line 165 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 33:
//#line 166 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 34:
//#line 167 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 35:
//#line 170 "../../src/parser/parser.y"
{yyval = IntType.getInstance();}
break;
case 36:
//#line 171 "../../src/parser/parser.y"
{yyval = RealType.getInstance();}
break;
case 37:
//#line 172 "../../src/parser/parser.y"
{yyval = CharType.getInstance();}
break;
case 38:
//#line 175 "../../src/parser/parser.y"
{yyval = new ArrayType(scanner.getLine(), scanner.getColumn(), (int) val_peek(2), (Type) val_peek(0));}
break;
case 39:
//#line 178 "../../src/parser/parser.y"
{yyval = new RecordType(scanner.getLine(), scanner.getColumn(), (List<RecordField>) val_peek(1));}
break;
case 40:
//#line 184 "../../src/parser/parser.y"
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
case 41:
//#line 194 "../../src/parser/parser.y"
{	
																		List<RecordField> fields = (List<RecordField>) val_peek(1); 
																		List<VarDefinition> varDefinitions = (List<VarDefinition>) val_peek(0);
																		
																		for(VarDefinition varDefinition : varDefinitions){
																			RecordField field = new RecordField(varDefinition);
																			
																			/* Si el campo ya está en la lista, es un error semántico (lo detectamos en esta fase que es más fácil). */
																			if(fields.contains(field))
																				new ErrorType(field, "Semantical error: The record field '" + field.getName() + "' has already been defined in the struct.");
																			
																			fields.add(field);
																		}
																		yyval = fields;
																	}
break;
case 42:
//#line 212 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));
  																		yyval = asStatementList(assignment);
																	}
break;
case 43:
//#line 218 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) val_peek(1);
																		for(Expression expression : expressions){
																			statements.add( new Read(scanner.getLine(), scanner.getColumn(), expression) );
																		}
																		yyval = statements;
																	}
break;
case 44:
//#line 229 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) val_peek(1);
																		for(Expression expression : expressions){
																			statements.add( new Write(scanner.getLine(), scanner.getColumn(), expression) );
																		}
																		yyval = statements;
																	}
break;
case 45:
//#line 241 "../../src/parser/parser.y"
{List<Expression> expressions = new LinkedList<Expression>(); expressions.add((Expression) val_peek(0)); yyval = expressions;}
break;
case 46:
//#line 242 "../../src/parser/parser.y"
{List<Expression> expressions = (List<Expression>) val_peek(2); expressions.add((Expression) val_peek(0)); yyval = expressions;}
break;
case 47:
//#line 246 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) val_peek(1));
																		Expression expression = (Expression) val_peek(4);

																		While _while = new While(expression.getLine(), expression.getColumn(), expression, body);
  																		yyval = asStatementList(_while);
																	}
break;
case 48:
//#line 254 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) val_peek(0));
																		Expression expression = (Expression) val_peek(2);

																		While _while = new While(expression.getLine(), expression.getColumn(), expression, body);
  																		yyval = asStatementList(_while);
																	}
break;
case 49:
//#line 264 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(1));
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) val_peek(4);

																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 50:
//#line 273 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(5));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(1));
  																		Expression expression = (Expression) val_peek(8);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 51:
//#line 282 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(3));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(0));
  																		Expression expression = (Expression) val_peek(6);

																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 52:
//#line 291 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(0));
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) val_peek(2);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 53:
//#line 300 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(4));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(1));
  																		Expression expression = (Expression) val_peek(6);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody); 
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 54:
//#line 309 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(2));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(0));
  																		Expression expression = (Expression) val_peek(4);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
																		yyval = asStatementList(ifStatement);
																	}
break;
case 55:
//#line 320 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Return _return = new Return(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(1));
																		yyval = asStatementList(_return);
																	}
break;
case 56:
//#line 329 "../../src/parser/parser.y"
{yyval = new LinkedList<Expression>();}
break;
case 57:
//#line 330 "../../src/parser/parser.y"
{yyval = (List<Expression>) val_peek(0);}
break;
case 58:
//#line 333 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(4));
																		Invocation invocation = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) val_peek(2)); 
																		yyval = asStatementList(invocation);	 
																	}
break;
case 59:
//#line 342 "../../src/parser/parser.y"
{	
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(3));
																		yyval = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) val_peek(1));
																	}
break;
case 60:
//#line 348 "../../src/parser/parser.y"
{yyval = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 61:
//#line 349 "../../src/parser/parser.y"
{yyval = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 62:
//#line 350 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 63:
//#line 351 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 64:
//#line 352 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 65:
//#line 353 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 66:
//#line 354 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 67:
//#line 355 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 68:
//#line 356 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 69:
//#line 357 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 70:
//#line 358 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 71:
//#line 359 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 72:
//#line 360 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 73:
//#line 361 "../../src/parser/parser.y"
{yyval = new UnaryNot(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 74:
//#line 362 "../../src/parser/parser.y"
{yyval = new UnaryMinus(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 75:
//#line 363 "../../src/parser/parser.y"
{yyval = new Cast(scanner.getLine(), scanner.getColumn(), (Type) val_peek(2), (Expression) val_peek(0));}
break;
case 76:
//#line 364 "../../src/parser/parser.y"
{yyval = new FieldAccess(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(0));}
break;
case 77:
//#line 365 "../../src/parser/parser.y"
{yyval = new Indexing(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));}
break;
case 78:
//#line 366 "../../src/parser/parser.y"
{yyval = (Expression) val_peek(1);}
break;
case 79:
//#line 367 "../../src/parser/parser.y"
{yyval = (Invocation) val_peek(0);}
break;
case 80:
//#line 368 "../../src/parser/parser.y"
{yyval = new IntLiteral(scanner.getLine(), scanner.getColumn(), (int) val_peek(0));}
break;
case 81:
//#line 369 "../../src/parser/parser.y"
{yyval = new RealLiteral(scanner.getLine(), scanner.getColumn(), (double) val_peek(0));}
break;
case 82:
//#line 370 "../../src/parser/parser.y"
{yyval = new CharLiteral(scanner.getLine(), scanner.getColumn(), (char) val_peek(0));}
break;
case 83:
//#line 371 "../../src/parser/parser.y"
{yyval = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(0));}
break;
//#line 1263 "Parser.java"
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
