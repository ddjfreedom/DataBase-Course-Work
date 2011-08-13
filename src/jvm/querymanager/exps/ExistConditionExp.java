package querymanager.exps;

public class ExistConditionExp extends ConditionExp {
	boolean isNot;
	QueryExp query;
	
	public ExistConditionExp(boolean isNot, QueryExp query){
		this.isNot = isNot;
		this.query = query;
	}

	public boolean getIsNot() {
		return isNot;
	}

	public void setIsNot(boolean isNot) {
		this.isNot = isNot;
	}

	public QueryExp getQuery() {
		return query;
	}

	public void setQuery(QueryExp query) {
		this.query = query;
	}
}
