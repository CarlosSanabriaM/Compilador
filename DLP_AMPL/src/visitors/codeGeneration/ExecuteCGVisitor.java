package visitors.codeGeneration;

import java.io.FileWriter;
import java.io.IOException;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
import ast.statements.DoWhile;
import ast.statements.For;
import ast.statements.IfStatement;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.Statement;
import ast.statements.While;
import ast.statements.Write;
import ast.statementsAndExpressions.Assignment;
import ast.statementsAndExpressions.Invocation;
import ast.statementsAndExpressions.PostArithmetic;
import ast.statementsAndExpressions.PreArithmetic;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.VoidType;
import codeGeneration.CodeGenerator;

/**
 *	Para la función de código EXECUTE[[ - ]].
 *	Habrá un visit() por cada plantilla de código.
 *	Es el primero de los ExecuteVisitor que se llama.
 */
public class ExecuteCGVisitor extends AbstractCGVisitor {
	
	private FileWriter fw;
	private String inputFileName;
	private String outputFileName;
	
	private CodeGenerator cg;
	private ValueCGVisitor valueCGVisitor;
	private AddressCGVisitor addressCGVisitor;

	/**
	 * Es el primero de los ExecuteVisitor que se llama.
	 * Es el encargado de crear los demás ExecuteVisitor.
	 */
	public ExecuteCGVisitor(String inputFileName, String outputFileName) {
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		
		// Creamos el Writer para el fichero de salida
		try {
			fw = new FileWriter(outputFileName);
		} catch(IOException io) {
			System.err.println("The output file "+outputFileName+" could not be opened.");
			return;
		}
		
		cg = new CodeGenerator(fw);

		// Les pasa el codeGenerator ya creado, en lugar del fileWriter, para que no tengan que crearlo.
		valueCGVisitor = new ValueCGVisitor(cg);
		addressCGVisitor = new AddressCGVisitor(cg);
		
		valueCGVisitor.setAddressCGVisitor(addressCGVisitor);
		addressCGVisitor.setValueCGVisitor(valueCGVisitor);
	}
	
	/**
	 * Se encarga de cerrar el Writer
	 */
	private void closeWriter() {
		if(fw != null) {
			try {
				fw.close();
			} catch (IOException e) {
				System.err.println("Error by closing the output file: \"" + outputFileName + "\".");
			}
		}
	}

	@Override
	public Object visit(Program program, Object param) {
		// Añadimos la directiva source con el nombre del fichero de entrada
		cg.sourceDirective(inputFileName);
		
		// Info de las variables globales
		for (Definition def : program.definitions)
			if(def instanceof VarDefinition)
				def.accept(this, "global"); // EXECUTE[[def]]

		cg.callMain();
		cg.halt();
		
		// Definiciones de funciones
		for (Definition def : program.definitions)
			if(def instanceof FunDefinition)
				def.accept(this, param); // EXECUTE[[def]]
		
		// Cerramos el Writer
		closeWriter();
		
		return null;
	}

	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		String scope = (String) param;
		
		cg.varDefinitionComment(varDefinition);
		cg.varDefinitionDirective(varDefinition, scope);
		
		return null;
	}
	
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		FunctionType functionType = (FunctionType) funDefinition.getType();
		
		cg.lineDirective(funDefinition.getLine());
		cg.label(funDefinition.getName());
		cg.directivet("func\t" + funDefinition.getName());

		// Info de los parametros
		cg.comment("--- Parameters ---");
		for (VarDefinition p : functionType.param)
			p.accept(this, "param"); // EXECUTE[[parami]]("param")
		
		// Info del valor de retorno
		cg.directivelnt("ret\t" + functionType.returnType.getDirectiveInfo() + "\n");
		
		// Info de las variables locales
		cg.comment("--- Local variables ---");
		for (Statement stm : funDefinition.statements)
			if(stm instanceof VarDefinition)
				stm.accept(this, "local"); // EXECUTE[[stm]]("local")
		
		cg.enter(funDefinition.bytesLocalVariables);
		
		// Ejecutamos las sentencias que no son defs. vars.
		for (Statement stm : funDefinition.statements)
			if(! (stm instanceof VarDefinition) )
				stm.accept(this, funDefinition); // EXECUTE[[stm]]	// Le pasamos el FunDefinition a todos sus statements
		
		// Si el tipo de retorno de la función es VOID (no tiene returns) hay que añadir un ret
		if(functionType.returnType instanceof VoidType)
			cg.ret(0, funDefinition.bytesLocalVariables, functionType.bytesParameters);
		
		return null;
	}

	@Override
	public Object visit(Write write, Object param) {
		cg.lineDirective(write.expression.getLine());
		cg.comment("Write");
		
		write.expression.accept(valueCGVisitor, param); // VALUE[[expr]]
		cg.out(write.expression.getType());
		
		return null;
	}

	@Override
	public Object visit(Read read, Object param) {
		cg.lineDirective(read.getLine());
		cg.comment("Read");
		
		read.expression.accept(addressCGVisitor, param); // ADDRESS[[expr]]
		cg.in(read.expression.getType());
		cg.store(read.expression.getType());
		
		return null;
	}

	@Override
	public Object visit(Assignment assignment, Object param) {
		cg.lineDirective(assignment.getLine());
		cg.comment("Assignment");
		
		assignment.left.accept(addressCGVisitor, param); 	// ADDRESS[[left]]
		assignment.right.accept(valueCGVisitor, param); 	// VALUE[[right]]
		
		// En caso de que sea necesario transformar el tipo de la derecha
		// al tipo de la izquierda, se añaden las conversiones necesarias
		cg.convert(assignment.right.getType(), assignment.left.getType());
		
		cg.store(assignment.left.getType());
		
		return null;
	}

	@Override
	public Object visit(While _while, Object param) {
		cg.lineDirective(_while.condition.getLine());
		cg.comment("While");
		
		int labelNum = cg.getLabelNum();
		
		cg.label("while" + labelNum);
		_while.condition.accept(valueCGVisitor, param);			// VALUE[[condition]]
		cg.convert(_while.condition.getType(), IntType.getInstance());
		cg.jz("end_while" + labelNum);
		
		cg.comment("While body");
		_while.body.forEach( (stm) -> stm.accept(this, param) );	// EXECUTE[[bodyi]]
		cg.jmp("while" + labelNum);
		
		cg.label("end_while" + labelNum);
		
		return null;
	}
	
	@Override
	public Object visit(DoWhile doWhile, Object param) {
		cg.lineDirective(doWhile.condition.getLine());
		cg.comment("Do While");
		
		int labelNum = cg.getLabelNum();
		
		cg.label("do_while" + labelNum);
		doWhile.body.forEach( (stm) -> stm.accept(this, param) );	// EXECUTE[[bodyi]]
		
		doWhile.condition.accept(valueCGVisitor, param);			// VALUE[[condition]]
		cg.convert(doWhile.condition.getType(), IntType.getInstance());
		cg.jnz("do_while" + labelNum);
		
		return null;
	}
	
	@Override
	public Object visit(For _for, Object param) {
		cg.lineDirective(_for.condition.getLine());
		cg.comment("For");
		
		int labelNum = cg.getLabelNum();
		
		_for.initializationStatements.forEach( (stm) -> stm.accept(this, param) );	// EXECUTE[[initializationStatementsi]]
		
		cg.label("for" + labelNum);
		_for.condition.accept(valueCGVisitor, param);								// VALUE[[condition]]
		cg.convert(_for.condition.getType(), IntType.getInstance());
		cg.jz("end_for" + labelNum);
		
		cg.comment("For body");
		_for.body.forEach( (stm) -> stm.accept(this, param) );						// EXECUTE[[bodyi]]
		_for.incrementStatements.forEach( (stm) -> stm.accept(this, param) );			// EXECUTE[[incrementStatementsi]]
		cg.jmp("for" + labelNum);
		
		cg.label("end_for" + labelNum);
		
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement, Object param) {
		boolean hasElse = ! ifStatement.elseBody.isEmpty();
		
		cg.lineDirective(ifStatement.condition.getLine());
		cg.comment("If");
		
		int labelNum = cg.getLabelNum();
		
		ifStatement.condition.accept(valueCGVisitor, param);					// VALUE[[condition]]
		cg.convert(ifStatement.condition.getType(), IntType.getInstance());
		// Si hay else, saltamos al principio del else, si no, saltamos al final del ifStatement 
		String jzLabel = hasElse ? "else" : "end_if" ;
		cg.jz(jzLabel + labelNum);
		
		cg.comment("If body");
		ifStatement.ifBody.forEach( (stm) -> stm.accept(this, param) );		// EXECUTE[[ifBodyi]]
		
		// Solo saltamos al cuerpo del else y ejecutamos sus sentencias si lo hay
		if(hasElse) {
			cg.jmp("end_if" + labelNum);
			
			cg.label("else" + labelNum);
			cg.comment("Else body");
			ifStatement.elseBody.forEach( (stm) -> stm.accept(this, param) );	// EXECUTE[[elseBodyi]]
		}
		
		cg.label("end_if" + labelNum);
		
		return null;
	}
	
	@Override
	public Object visit(Invocation invocation, Object param) {
		cg.lineDirective(invocation.function.getLine());
		cg.comment("Invocation of function: " + invocation.function.name);
		
		invocation.accept(valueCGVisitor, param);	// VALUE[[invocation]]
		
		FunctionType functionType = (FunctionType) invocation.function.getType();
		if(! (functionType.returnType instanceof VoidType) ) {
			cg.pop(functionType.returnType);
		}
		
		return null;
	}

	@Override
	public Object visit(Return _return, Object param) {
		cg.lineDirective(_return.getLine());
		cg.comment("Return");
		
		FunDefinition funDefinition = (FunDefinition) param;	// el visit(FunDefinition) pasa el FunDefinition a todos sus statements como parametro
		FunctionType functionType = (FunctionType) funDefinition.getType();
		
		_return.expression.accept(valueCGVisitor, param);		// VALUE[[exp]]
		
		cg.convert(_return.expression.getType(), functionType.returnType);
		
		cg.ret(functionType.returnType.numBytes(), 
				funDefinition.bytesLocalVariables, 
				functionType.bytesParameters);
		
		return null;
	}

	@Override
	public Object visit(PreArithmetic preArithmetic, Object param) {
		cg.lineDirective(preArithmetic.getLine());
		cg.comment("Pre Arithmetic");
		
		preArithmetic.accept(valueCGVisitor, param);		// VALUE[[preArithmetic]]
		
		cg.pop(preArithmetic.getType());
		
		return null;
	}

	@Override
	public Object visit(PostArithmetic postArithmetic, Object param) {
		cg.lineDirective(postArithmetic.getLine());
		cg.comment("Post Arithmetic");
		
		postArithmetic.accept(valueCGVisitor, param);	// VALUE[[postArithmetic]]
		
		cg.pop(postArithmetic.getType());
		
		return null;
	}
	
}
