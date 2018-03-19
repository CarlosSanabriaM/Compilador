package ast.types;

import java.util.List;

import visitors.Visitor;

public class RecordType extends AbstractType {

	public List<RecordField> fields;

	public RecordType(int line, int column, List<RecordField> fields) {
		super(line, column);
		this.fields = fields;
	}

	@Override
	public String toString() {
		String out = "struct {\n";
		for (RecordField field : fields)
			out += field + "\n";
		out += "}";
		
		return out;
	}

	// Visitor
	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	} 
	
}
