package funcs;

public abstract class Action {

	private String actionName;
	private String actionPath;

	public Action(String actionName) {
		this.actionName = actionName;
	}

	protected void setPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public String getPath() {
		return this.actionPath;
	}

	public String toString() {
		return this.actionName;
	}

	public abstract void execute();
}
