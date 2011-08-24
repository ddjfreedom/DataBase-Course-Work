package querymanager.exps;

public class QueryInsertExp extends InsertExp {
	QueryExp query;
	public QueryInsertExp(String listName, ParameterExpList parameterList, QueryExp query) {
		super(listName, parameterList);
		this.query = query;
	}

}
