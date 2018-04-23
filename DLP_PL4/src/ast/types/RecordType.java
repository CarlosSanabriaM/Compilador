package ast.types;

import java.util.List;

public class RecordType extends AbstractType {

	private List<RecordField> fields;

	public RecordType(int line, int column, List<RecordField> fields) {
		super(line, column);
		this.fields = fields;
	}

	public List<RecordField> getFields() {
		return fields;
	}

	public void setFields(List<RecordField> fields) {
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
	
}
