package querymanager.exps;

public class QueryInOrNotConditionExp extends InOrNotConditionExp {
	QueryExp query;
	
	public QueryInOrNotConditionExp(ParameterExp parameter, QueryExp query){
		super(parameter);
		this.query = query;
	}
}
