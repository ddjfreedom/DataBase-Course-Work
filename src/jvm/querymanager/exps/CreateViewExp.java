package querymanager.exps;

public class CreateViewExp extends Exp {
	String viewName;
	ParameterExpList parameters;
	QueryExp query;
	boolean withCheckOption;
	
	public CreateViewExp(String viewName, ParameterExpList parameters, QueryExp query, boolean withCheckOption){
		this.viewName = viewName;
		this.parameters = parameters;
		this.query = query;
		this.withCheckOption = withCheckOption;
	}
	@Override
	Result execute() {
		return null;
	}
	public String getViewName() {
		return viewName;
	}
	public QueryExp getQuery() {
		return query;
	}
}
