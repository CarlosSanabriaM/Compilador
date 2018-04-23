package visitors.codeGeneration;

import java.io.FileWriter;
import java.io.IOException;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
import ast.statements.Assignment;
import ast.statements.Read;
import ast.statements.Statement;
import ast.statements.Write;
import ast.types.FunctionType;
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
				def.accept(this, param); // EXECUTE[[def]]

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
		cg.varDefinitionComment(varDefinition);
		
		return null;
	}
	
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		FunctionType functionType = (FunctionType) funDefinition.getType();
		
		cg.lineDirective(funDefinition.getLine());
		cg.label(funDefinition.getName());

		// Info de los parametros
		cg.comment("Parameters");
		for (VarDefinition p : functionType.param) { // TODO - con este bucle o visit(FunctionType)???
			p.accept(this, param); // EXECUTE[[parami]]
		}
		
		// Info de las variables locales
		cg.comment("Local variables");
		for (Statement stm : funDefinition.statements)
			if(stm instanceof VarDefinition)
				stm.accept(this, param); // EXECUTE[[stm]]
		
		cg.enter(funDefinition.bytesLocalVariables);
		
		// Ejecutamos las sentencias que no son defs. vars.
		for (Statement stm : funDefinition.statements)
			if(! (stm instanceof VarDefinition) )
				stm.accept(this, param); // EXECUTE[[stm]]
		
		// Si el tipo de retorno de la función es VOID (no tiene returns) hay que añadir un ret
		if(functionType.returnType instanceof VoidType)
			cg.ret(0, funDefinition.bytesLocalVariables, functionType.bytesParameters);
		
		return null;
	}

	@Override
	public Object visit(Write write, Object param) {
		cg.lineDirective(write.expression.getLine());	// TODO - sacar la linea de  write o de write.expression?? En el expected_output usa la linea del write, pero se imprime mal en MAPL
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
		cg.lineDirective(assignment.getLine());// TODO - cada statement en su visit pone esta linea?? Porque si se quiere hacer en el for, while e if dan la linea mal, a menos q se acceda a su cond.
		cg.comment("Assignment");
		
		assignment.left.accept(addressCGVisitor, param); 	// ADDRESS[[left]]
		assignment.right.accept(valueCGVisitor, param); 	// VALUE[[right]]
		
		// En caso de que sea necesario transformar el tipo de la derecha
		// al tipo de la izquierda, se añaden las conversiones necesarias
		cg.convert(assignment.right.getType(), assignment.left.getType());
		
		cg.store(assignment.left.getType());
		
		return null;
	}
	
}
