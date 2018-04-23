package symboltable;

import java.util.*;
import ast.definitions.Definition;
import ast.definitions.VarDefinition;

public class SymbolTable {
	
	private int scope=0;
	private List<Map<String,Definition>> table;
	
	public SymbolTable()  {
		table = new LinkedList<Map<String,Definition>>();
		table.add(new HashMap<String,Definition>());
	}

	public void set() {
		scope++;
		table.add(new HashMap<String,Definition>());
	}
	
	public void reset() {
		table.remove(scope);
		scope--;
	}
	
	public boolean insert(Definition definition) {
		// Si ya ha sido definida en el mismo ámbito, no podemos insertar
		if(findInCurrentScope(definition.getName()) != null)
			return false;
		
		// Si es una definición de variable, le asignamos el scope que tiene la tabla de símbolos
		if(definition instanceof VarDefinition)
			((VarDefinition) definition).setScope(scope);
		
		// Añadimos la definición a la tabla de símbolos
		table.get(scope).put(definition.getName(), definition);
		
		return true;
	}
	
	public Definition find(String id) {
		for (int i = scope; i >=0; i--) {
			if(table.get(i).containsKey(id))
				return table.get(i).get(id);
		}
		return null;
	}

	public Definition findInCurrentScope(String id) {
		return table.get(scope).get(id);
	}
}
