package visitors;

import ast.definitions.Definition;
import ast.definitions.FunDefinition;
import ast.definitions.VarDefinition;
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
		varDefinition.getType().accept(this, param); //TODO - aqui o debajo??
		
		boolean insertOk = symbolTable.insert(varDefinition);
		predicate(insertOk, varDefinition, 
				"Semantical error: The variable '"+ varDefinition.getName() +"' has already been defined before.");

		return null;
	}

	@Override
	public Object visit(Variable variable, Object param) {
		Definition definition = symbolTable.find(variable.name);
		predicate(definition != null, variable, 
				"Semantical error: '"+ variable.name +"' has not been defined before.");
		
		variable.definition = definition;
		
		return null;
	}
	
}
