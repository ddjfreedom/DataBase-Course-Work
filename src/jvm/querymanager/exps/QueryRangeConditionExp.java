package querymanager.exps;

public class QueryRangeConditionExp extends RangeConditionExp {
	QueryExp upLimit;
	QueryExp downLimit;
	
	public QueryRangeConditionExp(ParameterExp parameter, QueryExp downLimit, QueryExp upLimit){
		super(parameter);
		this.downLimit = downLimit;
		this.upLimit = upLimit;
	}
}
