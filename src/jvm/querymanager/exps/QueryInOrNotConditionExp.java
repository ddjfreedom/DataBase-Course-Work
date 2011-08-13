package querymanager.exps;

public class QueryInOrNotConditionExp extends InOrNotConditionExp {
	QueryExp query;
	
	public QueryInOrNotConditionExp(ParameterExp parameter, QueryExp query, boolean isNot){
		super(parameter, isNot);
		this.query = query;
	}

	public QueryExp getQuery() {
		return query;
	}

	public void setQuery(QueryExp query) {
		this.query = query;
	}
}
