package visitors.codeGeneration;

import java.io.FileWriter;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
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
				def.accept(this, param);

		cg.callMain();
		cg.halt();
		
		// Definiciones de funciones
		for (Definition def : program.definitions)
			if(def instanceof FunDefinition)
				def.accept(this, param);
		
		return null;
	}
	
}
