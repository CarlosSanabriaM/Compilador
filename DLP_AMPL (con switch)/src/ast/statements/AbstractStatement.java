package ast.statements;

public abstract class AbstractStatement implements Statement {

	private int line, column;
	private boolean assignsValue;
	private boolean isntLoopOrSwitchAndHasBreak;
	
	public AbstractStatement(int line, int column) {
		this.line = line;
		this.column = column;
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public void setAssignsValue(boolean assignsValue) {
		this.assignsValue = assignsValue;
	}

	@Override
	public boolean getAssignsValue() {
		return this.assignsValue;
	}

	public boolean getIsntLoopOrSwitchAndHasBreak() {
		return isntLoopOrSwitchAndHasBreak;
	}

	public void setIsntLoopOrSwitchAndHasBreak(boolean isntLoopOrSwitchAndHasBreak) {
		this.isntLoopOrSwitchAndHasBreak = isntLoopOrSwitchAndHasBreak;
	}

}
