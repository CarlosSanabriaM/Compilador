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
    0,    1,    1,    1,    5,    2,    3,    7,    7,    9,
    4,   10,   10,   12,   12,   13,   11,   11,    6,    6,
    6,    6,   15,   15,   16,   16,   17,   17,   17,   17,
   17,   17,   17,    8,    8,    8,   14,   14,   14,   25,
   26,   27,   27,   18,   23,   24,   29,   29,   21,   21,
   22,   22,   22,   22,   22,   22,   20,   30,   30,   19,
   31,   28,   28,   28,   28,   28,   28,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   28,   28,   28,
   28,   28,   28,   28,   28,
};
final static short yylen[] = {                            2,
    2,    0,    2,    2,    0,   10,    4,    1,    3,    0,
   11,    0,    1,    1,    3,    3,    1,    1,    0,    2,
    1,    1,    1,    2,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    4,
    4,    1,    2,    4,    3,    3,    1,    3,    6,    4,
    6,   10,    8,    4,    8,    6,    3,    0,    1,    5,
    4,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    4,    3,    4,    3,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    8,    1,    3,    4,    0,    0,    0,
    0,    0,    0,    0,   37,   38,   39,    0,    0,    0,
   34,   35,   36,    9,    0,    0,    0,    0,    7,    0,
    0,    0,    0,   14,   42,    0,    0,    0,    0,    0,
    0,   41,   43,   40,    0,   16,    0,   15,    0,   18,
    0,   17,   82,   83,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   23,    0,    0,    0,   25,   27,
   28,   29,   30,   31,   32,   33,    0,   81,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,   24,    0,    0,   26,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,   46,    0,    0,   57,
    0,    0,    0,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   78,    0,
   11,    0,    0,    0,   50,    0,    0,    0,    0,   44,
   79,   61,    0,    0,    0,   60,   49,    0,    0,   56,
    0,    0,    0,   53,   55,    0,   52,
};
final static short yydgoto[] = {                          1,
    2,    5,   65,    7,    9,   66,    8,   20,   10,   32,
   51,   33,   34,   21,   67,   68,   69,   70,   71,   72,
   73,   74,   75,   76,   22,   23,   36,   77,  121,  122,
   78,
};
final static short yysindex[] = {                         0,
    0, -220,    0,    0,    0,    0,    0,  -42, -260, -257,
  -90, -254,    1,    3,    0,    0,    0,  -78, -211,    6,
    0,    0,    0,    0,   30, -199, -184,   -1,    0,   42,
   43,   66,   71,    0,    0, -111,  -90, -155, -187,   59,
 -199,    0,    0,    0,    4,    0,   15,    0,  763,    0,
   13,    0,    0,    0,    0,  658,  658,  658,  658,  658,
   84,  658,  658,  691,    0,   12,  763,  780,    0,    0,
    0,    0,    0,    0,    0,    0,  123,    0,  763,   86,
  469,  -32,  -15,  153,  160,  186,  658,  -27,  -27,  110,
  216,    0,    0,  780,   84,    0,  658,  658,  658,  658,
  658,  658,  658,  658,  658,  658,  658,  658,  658,  658,
 -134,  658,   27,  658,    0,  658,    0,  495,  534,    0,
  112,  122,  658,    0,  350,  496,  496,  278,  278,  278,
  278,  278,  278,  179,  179,  -27,  -27,  -27,    0,  407,
    0,  130,  469,  780,    0,  780, -101,  108,  -27,    0,
    0,    0,  635,  682,  591,    0,    0,  -93,  780,    0,
  661,  717,  780,    0,    0,  813,    0,
};
final static short yyrindex[] = {                         0,
    0,    0, -234,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  132,    0,    0,    0,    0,
    0,    0,  133,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   97,    0,    0,    0,    0,    0,   55,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   50,  -37,
  -24,    0,    0,    0,    0,    0,  146,  -11,   16,    0,
    0,    0,    0,   64,  441,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  146,    0,    0,    0,    0,    0,    0,
  150,    0,    0,    0,    0,  301,  903,  510,  539,  551,
  561,  604,  611,  363,  503,   25,   52,   61,    0,    0,
    0,    0,   -4,    0,    0,    0,  831,  462,   88,    0,
    0,    0,    0,    0,    0,    0,    0,  868,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  126,    0,    0,  113,    0,  157,    0,    0,
    0,    0,  163,  -36,    0,  189,  118,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1048,   34,   87,
    0,
};
final static int YYTABLESIZE=1179;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   19,   12,   46,   85,   85,   85,   85,   85,   85,   85,
   52,  116,   13,   42,   14,   11,   47,   24,  111,   47,
   85,   85,   85,   85,   85,   76,  115,   90,  116,   76,
   76,   76,   76,   76,   47,   76,   48,   10,    5,   48,
   25,    3,   26,  117,   27,   28,   76,   76,   76,   76,
   76,    4,   75,   85,   48,   85,   75,   75,   75,   75,
   75,   72,   75,  112,   29,   72,   72,   72,   72,   72,
   30,   72,   31,   75,   75,   75,   75,   75,   15,   16,
   17,   76,   72,   72,   72,   72,   72,    4,   73,   82,
   83,   37,   73,   73,   73,   73,   73,   74,   73,   38,
   39,   74,   74,   74,   74,   74,   40,   74,   75,   73,
   73,   73,   73,   73,   41,   45,   47,   72,   74,   74,
   74,   74,   74,   87,   77,  114,   49,    6,   77,   77,
   77,   77,   77,   85,   77,   79,   92,  139,   85,   85,
    8,   85,   85,   85,   73,   77,   77,   77,   77,   77,
  123,  141,   35,   74,    8,  116,   85,   85,   85,  110,
    4,   43,  148,  155,  108,  106,  156,  107,  111,  109,
  152,  161,   12,   13,   19,   15,   16,   17,   18,   21,
   77,   22,  102,   97,  100,   96,   58,   85,   20,  110,
   59,  113,   93,   44,  108,  106,  110,  107,  111,  109,
  142,  108,  106,   48,  107,  111,  109,    0,    0,    0,
  118,   96,  102,  112,  100,  110,    0,  119,    0,  102,
  108,  100,  110,    0,  111,  109,    0,  108,  106,    0,
  107,  111,  109,    0,    0,  145,  147,   85,   85,   85,
   85,   85,   85,  112,  120,  102,    0,  100,    0,    0,
  112,    0,  110,    0,    0,   94,  124,  108,  106,    0,
  107,  111,  109,   76,   76,   76,   76,   76,   76,  112,
   96,   96,  160,    0,    0,  102,  112,  100,  164,   96,
   15,   16,   17,   96,    0,   50,    0,    0,    0,    0,
   75,   75,   75,   75,   75,   75,    0,    0,    0,   72,
   72,   72,   72,   72,   72,    0,  112,    0,    0,    0,
    0,    0,    0,    0,  110,    0,    0,    0,    0,  108,
  106,    0,  107,  111,  109,    0,   73,   73,   73,   73,
   73,   73,  153,    0,  154,   74,   74,   74,   74,   74,
   74,   62,    0,    0,   62,    0,    0,  162,    0,    0,
    0,  166,    0,    0,    0,    0,    0,    0,   62,   62,
    0,   62,   77,   77,   77,   77,   77,   77,  112,    0,
    0,   85,   85,   85,   85,   85,   85,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  110,    0,    0,    0,
    0,  108,  106,   62,  107,  111,  109,   98,   99,  101,
  103,  104,  105,   70,    0,   70,   70,   70,  150,  102,
    0,  100,    0,    0,    0,    0,    0,    0,    0,    0,
   70,   70,   70,   70,   70,    0,    0,   98,   99,  101,
  103,  104,  105,    0,   98,   99,  101,  103,  104,  105,
  112,    0,    0,  110,    0,    0,    0,    0,  108,  106,
    0,  107,  111,  109,    0,   70,    0,    0,    0,    0,
   98,   99,  101,  103,  104,  105,  102,    0,  100,    0,
    0,    0,    0,    0,    0,    0,    0,   85,    0,    0,
    0,    0,   85,   85,    0,   85,   85,   85,    0,    0,
   98,   99,  101,  103,  104,  105,    0,  112,   61,  151,
   85,   85,   85,   61,   61,  110,   61,   61,   61,    0,
  108,  106,    0,  107,  111,  109,    0,    0,    0,    0,
    0,   61,   61,   61,    0,    0,    0,   63,  102,    0,
  100,   85,  110,    0,   64,    0,    0,  108,  106,   62,
  107,  111,  109,   71,    0,   71,   71,   71,    0,    0,
   64,    0,   61,   64,    0,  102,    0,  100,    0,  112,
   71,   71,   71,   71,   71,    0,   63,   64,   64,   64,
   64,   64,    0,   64,    0,   62,   62,    0,   62,   65,
    0,    0,   65,    0,    0,    0,  112,    0,    0,    0,
    0,   66,    0,    0,   66,   71,   65,   65,   65,   65,
   65,   67,   64,    0,   67,    0,    0,    0,   66,   66,
   66,   66,   66,    0,    0,    0,    0,  144,   67,   67,
   67,   67,   67,   63,   98,   99,  101,  103,  104,  105,
   64,   65,    0,    0,    0,   62,    0,   70,   70,   70,
   70,   70,   70,   66,   68,    0,    0,   68,    0,    0,
    0,   69,    0,   67,   69,    0,  146,    0,    0,    0,
    0,   68,   68,   68,   68,   68,    0,   63,   69,   69,
   69,   69,   69,    0,   64,    0,    0,    0,    0,   62,
    0,   98,   99,  101,  103,  104,  105,    0,    0,    0,
   63,    0,    0,   63,    0,    0,   68,   64,    0,    0,
   64,    0,   62,   69,    0,   62,    0,    0,    0,    0,
    0,    0,    0,  159,   63,   85,   85,   85,   85,   85,
   85,   64,    0,   63,    0,    0,   62,    0,    0,    0,
   64,    0,    0,    0,    0,   62,   61,   61,   61,   61,
   61,   61,    0,   98,   99,  101,  103,  104,  105,   63,
    0,   53,   54,   55,   56,   57,   64,   58,   59,  157,
    0,   62,    0,    0,   60,    0,   95,    0,    0,    0,
    0,    0,  101,  103,  104,  105,    0,   71,   71,   71,
   71,   71,   71,  163,   64,   64,   64,   64,   64,   64,
   53,   54,   55,   56,   57,   63,   58,   59,    0,    0,
    0,    0,   64,   60,    0,   95,  158,   62,    0,    0,
    0,    0,   63,   65,   65,   65,   65,   65,   65,   64,
    0,    0,    0,    0,   62,   66,   66,   66,   66,   66,
   66,    0,    0,    0,    0,   67,   67,   67,   67,   67,
   67,  165,    0,    0,    0,   63,    0,   53,   54,   55,
   56,   57,   64,   58,   59,    0,    0,   62,    0,    0,
   60,    0,   95,   54,    0,    0,    0,    0,    0,    0,
   54,    0,    0,    0,    0,   54,    0,    0,   68,   68,
   68,   68,   68,   68,    0,   69,   69,   69,   69,   69,
   69,   53,   54,   55,   56,   57,    0,   58,   59,    0,
   51,    0,    0,    0,   60,    0,   95,   51,    0,    0,
    0,    0,   51,    0,   53,   54,   55,   53,   54,   55,
   56,   57,    0,   58,   59,    0,    0,    0,    0,   80,
   60,    0,   95,    0,    0,    0,    0,  167,   53,   54,
   55,   56,   57,   63,   58,   59,   63,   53,   54,   55,
    0,   60,    0,   95,    0,   54,   15,   16,   17,    0,
   63,   63,   80,   63,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   53,   54,   55,   56,   57,    0,   58,
   59,    0,    0,    0,    0,    0,   60,    0,   95,    0,
    0,    0,   51,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
   54,   55,   56,   57,    0,   58,   59,    0,    0,    0,
    0,    0,   60,    0,   61,    0,   53,   54,   55,   56,
   57,    0,   58,   59,    0,    0,    0,    0,    0,   60,
    0,   95,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
   54,   55,   56,   57,    0,   58,   59,    0,    0,    0,
    0,    0,   60,    0,   95,    0,    0,   54,   54,   54,
   54,   54,    0,   54,   54,    0,    0,    0,    0,    0,
   54,    0,   54,   81,   81,   84,   85,   86,    0,   88,
   89,   91,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   51,   51,   51,   51,   51,    0,
   51,   51,    0,    0,   81,    0,    0,   51,    0,   51,
    0,    0,    0,    0,  125,  126,  127,  128,  129,  130,
  131,  132,  133,  134,  135,  136,  137,  138,    0,  140,
    0,   81,    0,  143,    0,    0,    0,    0,    0,    0,
  149,    0,    0,    0,    0,    0,    0,   63,   63,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
   91,   44,   39,   41,   42,   43,   44,   45,   46,   47,
   47,   44,  273,  125,  272,   58,   41,  272,   46,   44,
   58,   59,   60,   61,   62,   37,   59,   64,   44,   41,
   42,   43,   44,   45,   59,   47,   41,  272,  273,   44,
   40,  262,   40,   59,  123,  257,   58,   59,   60,   61,
   62,  272,   37,   91,   59,   93,   41,   42,   43,   44,
   45,   37,   47,   91,   59,   41,   42,   43,   44,   45,
   41,   47,  272,   58,   59,   60,   61,   62,  266,  267,
  268,   93,   58,   59,   60,   61,   62,  272,   37,   56,
   57,   93,   41,   42,   43,   44,   45,   37,   47,   58,
   58,   41,   42,   43,   44,   45,   41,   47,   93,   58,
   59,   60,   61,   62,   44,  271,   58,   93,   58,   59,
   60,   61,   62,   40,   37,   40,  123,    2,   41,   42,
   43,   44,   45,   37,   47,  123,  125,  272,   42,   43,
   44,   45,   46,   47,   93,   58,   59,   60,   61,   62,
   41,  125,   27,   93,   58,   44,   60,   61,   62,   37,
  272,   36,   41,  265,   42,   43,   59,   45,   46,   47,
   41,  265,   41,   41,  125,  266,  267,  268,  269,  125,
   93,  125,   60,   61,   62,   68,   41,   91,  125,   37,
   41,   79,   67,   37,   42,   43,   37,   45,   46,   47,
  114,   42,   43,   41,   45,   46,   47,   -1,   -1,   -1,
   58,   94,   60,   91,   62,   37,   -1,   58,   -1,   60,
   42,   62,   37,   -1,   46,   47,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,  118,  119,  275,  276,  277,
  278,  279,  280,   91,   59,   60,   -1,   62,   -1,   -1,
   91,   -1,   37,   -1,   -1,   67,   41,   42,   43,   -1,
   45,   46,   47,  275,  276,  277,  278,  279,  280,   91,
  153,  154,  155,   -1,   -1,   60,   91,   62,  161,  162,
  266,  267,  268,  166,   -1,  271,   -1,   -1,   -1,   -1,
  275,  276,  277,  278,  279,  280,   -1,   -1,   -1,  275,
  276,  277,  278,  279,  280,   -1,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,  275,  276,  277,  278,
  279,  280,  144,   -1,  146,  275,  276,  277,  278,  279,
  280,   41,   -1,   -1,   44,   -1,   -1,  159,   -1,   -1,
   -1,  163,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,
   -1,   61,  275,  276,  277,  278,  279,  280,   91,   -1,
   -1,  275,  276,  277,  278,  279,  280,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,
   -1,   42,   43,   93,   45,   46,   47,  275,  276,  277,
  278,  279,  280,   41,   -1,   43,   44,   45,   59,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   58,   59,   60,   61,   62,   -1,   -1,  275,  276,  277,
  278,  279,  280,   -1,  275,  276,  277,  278,  279,  280,
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
  270,   -1,  272,   56,   57,   58,   59,   60,   -1,   62,
   63,   64,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,   -1,
  263,  264,   -1,   -1,   87,   -1,   -1,  270,   -1,  272,
   -1,   -1,   -1,   -1,   97,   98,   99,  100,  101,  102,
  103,  104,  105,  106,  107,  108,  109,  110,   -1,  112,
   -1,  114,   -1,  116,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,
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

//#line 379 "../../src/parser/parser.y"


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

// Variable temporal para detectar bien los numeros de linea
int tempLine;

//#line 642 "Parser.java"
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
//#line 62 "../../src/parser/parser.y"
{
																		List<Definition> definitions = (List<Definition>) val_peek(1);
																		Definition main = (Definition) val_peek(0);
																		definitions.add(main);
																		ast = new Program(scanner.getLine(), scanner.getColumn(), definitions);																			
																	}
break;
case 2:
//#line 70 "../../src/parser/parser.y"
{yyval = new LinkedList<Definition>();}
break;
case 3:
//#line 71 "../../src/parser/parser.y"
{List<Definition> definitions = (List<Definition>) val_peek(1); definitions.addAll((List<Definition>) val_peek(0)); yyval = definitions;}
break;
case 4:
//#line 72 "../../src/parser/parser.y"
{List<Definition> definitions = (List<Definition>) val_peek(1); definitions.add((Definition) val_peek(0)); yyval = definitions;}
break;
case 5:
//#line 75 "../../src/parser/parser.y"
{tempLine = scanner.getLine();}
break;
case 6:
//#line 76 "../../src/parser/parser.y"
{
																		FunctionType functionType = new FunctionType(tempLine, 1, new LinkedList<VarDefinition>(), VoidType.getInstance());
																		yyval = new FunDefinition(tempLine, 1, "main", functionType, (List<Statement>) val_peek(1));
																	}
break;
case 7:
//#line 83 "../../src/parser/parser.y"
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
//#line 95 "../../src/parser/parser.y"
{	
																		List<String> identifiers = new LinkedList<String>();
																		identifiers.add( (String) val_peek(0) );
																		yyval = identifiers;
																	}
break;
case 9:
//#line 100 "../../src/parser/parser.y"
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
//#line 116 "../../src/parser/parser.y"
{tempLine = scanner.getLine();}
break;
case 11:
//#line 118 "../../src/parser/parser.y"
{
																		FunctionType functionType = new FunctionType(tempLine, 1, (List<VarDefinition>) val_peek(6), (Type) val_peek(3));
																		yyval = new FunDefinition(tempLine, 1, (String) val_peek(8), functionType, (List<Statement>) val_peek(1));
																	}
break;
case 12:
//#line 124 "../../src/parser/parser.y"
{yyval = new LinkedList<VarDefinition>();}
break;
case 13:
//#line 125 "../../src/parser/parser.y"
{yyval = (List<VarDefinition>) val_peek(0);}
break;
case 14:
//#line 128 "../../src/parser/parser.y"
{List<VarDefinition> parameters = new LinkedList<VarDefinition>(); parameters.add((VarDefinition) val_peek(0)); yyval = parameters;}
break;
case 15:
//#line 129 "../../src/parser/parser.y"
{List<VarDefinition> parameters = (List<VarDefinition>) val_peek(2); parameters.add((VarDefinition) val_peek(0)); yyval = parameters;}
break;
case 16:
//#line 132 "../../src/parser/parser.y"
{yyval = new VarDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(2), (Type) val_peek(0));}
break;
case 17:
//#line 135 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 18:
//#line 136 "../../src/parser/parser.y"
{yyval = VoidType.getInstance();}
break;
case 19:
//#line 141 "../../src/parser/parser.y"
{yyval = new LinkedList<Statement>();}
break;
case 20:
//#line 142 "../../src/parser/parser.y"
{	
																		List<Statement> varDefinitions = (List<Statement>) val_peek(1); List<Statement> statements = (List<Statement>) val_peek(0); 
																		varDefinitions.addAll(statements); /* Añadimos los statements al final de la lista de varDefinitions*/
																		yyval = varDefinitions; 
																	}
break;
case 21:
//#line 147 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 22:
//#line 148 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 23:
//#line 151 "../../src/parser/parser.y"
{List<VarDefinition> varDefinitions = new LinkedList<VarDefinition>(); varDefinitions.addAll((List<VarDefinition>) val_peek(0)); yyval = varDefinitions;}
break;
case 24:
//#line 152 "../../src/parser/parser.y"
{List<VarDefinition> varDefinitions = (List<VarDefinition>) val_peek(1); varDefinitions.addAll((List<VarDefinition>) val_peek(0)); yyval = varDefinitions;}
break;
case 25:
//#line 155 "../../src/parser/parser.y"
{List<Statement> statements = new LinkedList<Statement>(); statements.addAll((List<Statement>) val_peek(0)); yyval = statements;}
break;
case 26:
//#line 156 "../../src/parser/parser.y"
{List<Statement> statements = (List<Statement>) val_peek(1); statements.addAll((List<Statement>) val_peek(0)); yyval = statements;}
break;
case 27:
//#line 160 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 28:
//#line 161 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 29:
//#line 162 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 30:
//#line 163 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 31:
//#line 164 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 32:
//#line 165 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 33:
//#line 166 "../../src/parser/parser.y"
{yyval = (List<Statement>) val_peek(0);}
break;
case 34:
//#line 169 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 35:
//#line 170 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 36:
//#line 171 "../../src/parser/parser.y"
{yyval = (Type) val_peek(0);}
break;
case 37:
//#line 174 "../../src/parser/parser.y"
{yyval = IntType.getInstance();}
break;
case 38:
//#line 175 "../../src/parser/parser.y"
{yyval = RealType.getInstance();}
break;
case 39:
//#line 176 "../../src/parser/parser.y"
{yyval = CharType.getInstance();}
break;
case 40:
//#line 179 "../../src/parser/parser.y"
{yyval = new ArrayType(scanner.getLine(), scanner.getColumn(), (int) val_peek(2), (Type) val_peek(0));}
break;
case 41:
//#line 182 "../../src/parser/parser.y"
{yyval = new RecordType(scanner.getLine(), scanner.getColumn(), (List<RecordField>) val_peek(1));}
break;
case 42:
//#line 188 "../../src/parser/parser.y"
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
case 43:
//#line 198 "../../src/parser/parser.y"
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
case 44:
//#line 216 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Assignment assignment = new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));
  																		yyval = asStatementList(assignment);
																	}
break;
case 45:
//#line 222 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) val_peek(1);
																		for(Expression expression : expressions){
																			statements.add( new Read(scanner.getLine(), scanner.getColumn(), expression) );
																		}
																		yyval = statements;
																	}
break;
case 46:
//#line 233 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> statements = new LinkedList<Statement>();
																		
																		List<Expression> expressions = (List<Expression>) val_peek(1);
																		for(Expression expression : expressions){
																			statements.add( new Write(scanner.getLine(), scanner.getColumn(), expression) );
																		}
																		yyval = statements;
																	}
break;
case 47:
//#line 245 "../../src/parser/parser.y"
{List<Expression> expressions = new LinkedList<Expression>(); expressions.add((Expression) val_peek(0)); yyval = expressions;}
break;
case 48:
//#line 246 "../../src/parser/parser.y"
{List<Expression> expressions = (List<Expression>) val_peek(2); expressions.add((Expression) val_peek(0)); yyval = expressions;}
break;
case 49:
//#line 250 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) val_peek(1));
																		Expression expression = (Expression) val_peek(4);

																		While _while = new While(expression.getLine(), expression.getColumn(), expression, body);
  																		yyval = asStatementList(_while);
																	}
break;
case 50:
//#line 258 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> body = new LinkedList<Statement>(); body.addAll((List<Statement>) val_peek(0));
																		Expression expression = (Expression) val_peek(2);

																		While _while = new While(expression.getLine(), expression.getColumn(), expression, body);
  																		yyval = asStatementList(_while);
																	}
break;
case 51:
//#line 268 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(1));
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) val_peek(4);

																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 52:
//#line 277 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(5));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(1));
  																		Expression expression = (Expression) val_peek(8);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 53:
//#line 286 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(3));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(0));
  																		Expression expression = (Expression) val_peek(6);

																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 54:
//#line 295 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)*/
																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(0));
  																		List<Statement> elseBody = new LinkedList<Statement>();
  																		Expression expression = (Expression) val_peek(2);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 55:
//#line 304 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(4));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(1));
  																		Expression expression = (Expression) val_peek(6);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody); 
  																		yyval = asStatementList(ifStatement);
  																	}
break;
case 56:
//#line 313 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
  																		List<Statement> ifBody = new LinkedList<Statement>(); ifBody.addAll((List<Statement>) val_peek(2));
  																		List<Statement> elseBody = new LinkedList<Statement>(); elseBody.addAll((List<Statement>) val_peek(0));
  																		Expression expression = (Expression) val_peek(4);
																		
																		IfStatement ifStatement = new IfStatement(expression.getLine(), expression.getColumn(), expression, ifBody, elseBody);
																		yyval = asStatementList(ifStatement);
																	}
break;
case 57:
//#line 324 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Return _return = new Return(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(1));
																		yyval = asStatementList(_return);
																	}
break;
case 58:
//#line 333 "../../src/parser/parser.y"
{yyval = new LinkedList<Expression>();}
break;
case 59:
//#line 334 "../../src/parser/parser.y"
{yyval = (List<Expression>) val_peek(0);}
break;
case 60:
//#line 337 "../../src/parser/parser.y"
{	/* statement se espera una lista (hay que meterlo en una lista aunque sea un solo elemento)																		*/
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(4));
																		Invocation invocation = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) val_peek(2)); 
																		yyval = asStatementList(invocation);	 
																	}
break;
case 61:
//#line 346 "../../src/parser/parser.y"
{	
																		Variable function = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(3));
																		yyval = new Invocation(scanner.getLine(), scanner.getColumn(), function, (List<Expression>) val_peek(1));
																	}
break;
case 62:
//#line 352 "../../src/parser/parser.y"
{yyval = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 63:
//#line 353 "../../src/parser/parser.y"
{yyval = new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 64:
//#line 354 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 65:
//#line 355 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 66:
//#line 356 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 67:
//#line 357 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 68:
//#line 358 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 69:
//#line 359 "../../src/parser/parser.y"
{yyval = new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 70:
//#line 360 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 71:
//#line 361 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 72:
//#line 362 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 73:
//#line 363 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 74:
//#line 364 "../../src/parser/parser.y"
{yyval = new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(1), (Expression) val_peek(0));}
break;
case 75:
//#line 365 "../../src/parser/parser.y"
{yyval = new UnaryNot(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 76:
//#line 366 "../../src/parser/parser.y"
{yyval = new UnaryMinus(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 77:
//#line 367 "../../src/parser/parser.y"
{yyval = new Cast(scanner.getLine(), scanner.getColumn(), (Type) val_peek(2), (Expression) val_peek(0));}
break;
case 78:
//#line 368 "../../src/parser/parser.y"
{yyval = new FieldAccess(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (String) val_peek(0));}
break;
case 79:
//#line 369 "../../src/parser/parser.y"
{yyval = new Indexing(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));}
break;
case 80:
//#line 370 "../../src/parser/parser.y"
{yyval = (Expression) val_peek(1);}
break;
case 81:
//#line 371 "../../src/parser/parser.y"
{yyval = (Invocation) val_peek(0);}
break;
case 82:
//#line 372 "../../src/parser/parser.y"
{yyval = new IntLiteral(scanner.getLine(), scanner.getColumn(), (int) val_peek(0));}
break;
case 83:
//#line 373 "../../src/parser/parser.y"
{yyval = new RealLiteral(scanner.getLine(), scanner.getColumn(), (double) val_peek(0));}
break;
case 84:
//#line 374 "../../src/parser/parser.y"
{yyval = new CharLiteral(scanner.getLine(), scanner.getColumn(), (char) val_peek(0));}
break;
case 85:
//#line 375 "../../src/parser/parser.y"
{yyval = new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(0));}
break;
//#line 1276 "Parser.java"
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
