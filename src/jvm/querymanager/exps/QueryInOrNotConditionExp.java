package querymanager.exps;

public class QueryInOrNotConditionExp extends InOrNotConditionExp {
	QueryExp query;
	
	public QueryInOrNotConditionExp(ParameterExp parameter, QueryExp query, boolean isNot){
		super(parameter, isNot);
		this.query = query;
	}
}
