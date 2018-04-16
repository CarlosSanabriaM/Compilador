package visitors.codeGeneration;

import java.io.FileWriter;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
import ast.statements.Assignment;
import ast.statements.Read;
import ast.statements.Write;
import codeGeneration.CodeGenerator;

/**
 *	Para la función de código EXECUTE[[ - ]].
 *	Habrá un visit() por cada plantilla de código.
 *	Es el primero de los ExecuteVisitor que se llama.
 */
public class ExecuteCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	private ValueCGVisitor valueCGVisitor;
	private AddressCGVisitor addressCGVisitor;

	/**
	 * Es el primero de los ExecuteVisitor que se llama.
	 * Es el encargado de crear los demás ExecuteVisitor.
	 */
	public ExecuteCGVisitor(FileWriter out) { //TODO - aqui recibe el nombre del fichero o el FileWriter??
		cg = new CodeGenerator(out);

		// Les pasa el codeGenerator ya creado, en lugar del fileWriter, para que no tengan que crearlo.
		valueCGVisitor = new ValueCGVisitor(cg); // TODO - ???
		addressCGVisitor = new AddressCGVisitor(cg);
	}

	@Override
	public Object visit(Program program, Object param) {
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
		
		return null;
	}

	@Override
	public Object visit(Write write, Object param) {
		write.expression.accept(valueCGVisitor, param); // VALUE[[expr]]
		cg.out(write.expression.getType());
		
		return null;
	}

	@Override
	public Object visit(Read read, Object param) {
		read.expression.accept(addressCGVisitor, param); // ADDRESS[[expr]]
		cg.in(read.expression.getType());
		cg.store(read.expression.getType());
		
		return null;
	}

	@Override
	public Object visit(Assignment assignment, Object param) {
		assignment.left.accept(addressCGVisitor, param); 	// ADDRESS[[left]]
		assignment.right.accept(valueCGVisitor, param); 	// VALUE[[right]]
		
		// En caso de que sea necesario transformar el tipo de la derecha
		// al tipo de la izquierda, se añaden las conversiones necesarias
		cg.convert(assignment.right.getType(), assignment.left.getType());
		
		cg.store(assignment.left.getType());
		
		return null;
	}

	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		cg.varDefinitionComment(varDefinition);
		
		return null;
	}
	
}
