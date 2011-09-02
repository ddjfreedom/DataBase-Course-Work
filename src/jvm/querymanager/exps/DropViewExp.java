package querymanager.exps;

public class DropViewExp extends Exp {
	String viewName;
	boolean cascade;
	
	public DropViewExp(String viewName, boolean cascade){
		this.viewName = viewName;
		this.cascade = cascade;
	}
	@Override
	Result execute() {
		return null;
	}
	public String getViewName() {
		return viewName;
	}
}
