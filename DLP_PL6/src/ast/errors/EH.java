package ast.errors;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class EH {
	
	private static final EH instance = new EH();
	
	private List<ErrorType> errors;
	
	private EH() {
		errors = new LinkedList<>();
	}

	public static EH getEH() {
		return instance;
	}
	
	public void addError(ErrorType error) {
		errors.add(error);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public void showErrors(PrintStream ps) {
		for (ErrorType error : errors)
			ps.println(error);
	}

}
