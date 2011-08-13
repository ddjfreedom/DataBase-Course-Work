package querymanager.exps;

public class QueryCompareConditionExp extends CompareConditionExp {
	ParameterExp parameter;
	QueryExp query;
	AnyOrAll anyOrAll;
	
	public QueryCompareConditionExp(ParameterExp parameter,CompareOp compareOp, QueryExp query, AnyOrAll anyOrAll){
		super(compareOp);
		this.parameter = parameter;
		this.query = query;
		this.anyOrAll = anyOrAll;
	}

	public ParameterExp getParameter() {
		return parameter;
	}

	public void setParameter(ParameterExp parameter) {
		this.parameter = parameter;
	}

	public QueryExp getQuery() {
		return query;
	}

	public void setQuery(QueryExp query) {
		this.query = query;
	}

	public AnyOrAll getAnyOrAll() {
		return anyOrAll;
	}

	public void setAnyOrAll(AnyOrAll anyOrAll) {
		this.anyOrAll = anyOrAll;
	}
}
