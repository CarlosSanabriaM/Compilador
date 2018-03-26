package visitors;

import ast.definitions.Definition;
import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
import ast.errors.ErrorType;
import ast.expressions.Variable;
import symboltable.SymbolTable;

public class IdentificationVisitor extends AbstractVisitor {

	SymbolTable symbolTable = new SymbolTable();
	
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		boolean insertOk = symbolTable.insert(funDefinition);
		predicate(insertOk, funDefinition, 
				"Semantical error: The function '"+ funDefinition.getName() +"' has already been defined before.");

		symbolTable.set();
		
		funDefinition.getType().accept(this, param);
		funDefinition.statements.forEach( (stm) -> stm.accept(this, param) );
				
		symbolTable.reset();
		
		return null;
	}

	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		varDefinition.getType().accept(this, param);
		
		boolean insertOk = symbolTable.insert(varDefinition);
		predicate(insertOk, varDefinition, 
				"Semantical error: The variable '"+ varDefinition.getName() +"' has already been defined before.");

		return null;
	}

	@Override
	public Object visit(Variable variable, Object param) {
		Definition definition = symbolTable.find(variable.name);
		// predicate (definition != null)
		if(definition != null)
			variable.definition = definition;
		else {
			variable.definition = new VarDefinition(variable.getLine(), variable.getColumn(), variable.name,
					new ErrorType(variable, "Semantical error: '" + variable.name + "' has not been defined before."));
		}
		
		return null;
	}
	
}
