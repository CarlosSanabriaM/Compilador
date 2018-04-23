
package parser;

import scanner.Scanner;

public class Parser {

    // * Tokens
    public final static int INT_CONSTANT 	= 257;
    public final static int REAL_CONSTANT 	= 258;
    public final static int CHAR_CONSTANT 	= 259;
    public final static int INPUT 			= 260;
    public final static int PRINT 			= 261;
    public final static int DEF 				= 262;
    public final static int WHILE 			= 263;
    public final static int IF 				= 264;
    public final static int ELSE 			= 265;
    public final static int INT 				= 266;
    public final static int DOUBLE 			= 267;
    public final static int CHAR 			= 268;
    public final static int STRUCT 			= 269;
    public final static int RETURN 			= 270;
    public final static int VOID 			= 271;
    public final static int ID 				= 272;
    public final static int EQUALS 			= 273;
    public final static int LESS_OR_EQUAL	= 274;
    public final static int GREATER_OR_EQUAL	= 275;
    public final static int DISTINCT 		= 276;
    
    private Scanner scanner;
    
    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }
}