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

	// Type
	@Override
	public Type dot(String fieldName) {
		// Si hay algún campo con ese nombre, retorno su tipo
		for (RecordField field : fields) {
			if(field.name.equals(fieldName))
				return field.getType();
		}
		return null;
	} 

	@Override
	public int numBytes() {
		int numBytes = 0;
		for (RecordField field : fields) 
			numBytes += field.getType().numBytes();
			
		return numBytes;
	}

	/**
	 * Returns the field of the struct with that name,
	 * or null if not exists.
	 */
	public RecordField get(String fieldName) {
		for (RecordField field : fields) {
			if(field.name.equals(fieldName))
				return field;
		}
		
		return null;
	}

	@Override
	public String getCommentInfo() {
		String info = this.getClass().getSimpleName();
		info += "[fields: [";
		
		int i = 0;
		for (RecordField field : fields) {
			info += field.getCommentInfo();
			if(i != fields.size()-1)
				info  += ", ";
			i++;
		}
		
		info += "]]";
		return info;
	}
	
	@Override
	public String getDirectiveInfo() {
		String info = "{";
		
		for (RecordField field : fields)
			info += "\n\t\t" + field.getDirectiveInfo();
		
		info += "\n\t}";
		
		return info;
	}
	
}
