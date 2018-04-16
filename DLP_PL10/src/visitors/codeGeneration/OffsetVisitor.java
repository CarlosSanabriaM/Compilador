package visitors.codeGeneration;

import java.util.ListIterator;

import ast.definitions.FunDefinition;
import ast.definitions.Scope;
import ast.definitions.VarDefinition;
import ast.types.FunctionType;
import ast.types.RecordField;
import ast.types.RecordType;
import visitors.AbstractVisitor;

public class OffsetVisitor extends AbstractVisitor {

	private int numTotalBytesGlobalVars = 0;
	private int numTotalBytesLocalVars = 0;

	@Override
	public Object visit(FunctionType functionType, Object param) {
		// En lugar de visitar los parámetros, iteramos por ellos en orden inverso y les añadimos el offset
		int numTotalBytesParamsAtItsRight = 0;
		ListIterator<VarDefinition> iterator = functionType.param.listIterator(functionType.param.size());// iterador empieza al final de la lista
		
		while(iterator.hasPrevious()) {
			VarDefinition actualParam = iterator.previous();
			actualParam.offset = + 4 + numTotalBytesParamsAtItsRight; // BP + 4 + Σ(tam. param declarados a la dcha)
			numTotalBytesParamsAtItsRight = actualParam.getType().numBytes();
		}
		
		functionType.returnType.accept(this, param);
		
		return null;
	}
	
	@Override
	public Object visit(FunDefinition funDefinition, Object param) {
		numTotalBytesLocalVars = 0; // Se resetea a 0 al entrar en una función.
		
		funDefinition.getType().accept(this, param);
		funDefinition.statements.forEach( (stm) -> stm.accept(this, param) );

		return null;
	}

	@Override
	public Object visit(VarDefinition varDefinition, Object param) {
		// Si es global
		if(varDefinition.scope == Scope.GLOBAL) {
			varDefinition.offset = numTotalBytesGlobalVars; // Σ(tam. vars. globales anteriores)			
			numTotalBytesGlobalVars += varDefinition.getType().numBytes();			
		}
		// Si es local (aunque los parámetros también tienen scope=1, no visitamos los parámetros)
		else { // varDefinition.scope == Scope.LOCAL
			numTotalBytesLocalVars += varDefinition.getType().numBytes();
			varDefinition.offset = -numTotalBytesLocalVars; // BP + - Σ(tam. vars. locales anteriores, incluida la actual)
		}
		
		varDefinition.getType().accept(this, varDefinition.offset);// Le pasamos al struct su direccion base como parámetro
		
		return null;
	}
	
	@Override
	public Object visit(RecordType recordType, Object param) {
		// En lugar de visitar los campos, iteramos por ellos
		int structBaseDir = (int) param;
		int numTotalBytesFields = 0;
		
		for (RecordField field : recordType.fields) {
			field.offset = structBaseDir + numTotalBytesFields;
			numTotalBytesFields += field.getType().numBytes();
		}
			
		return null;
	}
	
}
