package querymanager.exps;

public class QueryRangeConditionExp extends RangeConditionExp {
	QueryExp upLimit;
	QueryExp downLimit;
	
	public QueryRangeConditionExp(ParameterExp parameter, QueryExp downLimit, QueryExp upLimit, boolean isNot){
		super(parameter, isNot);
		this.downLimit = downLimit;
		this.upLimit = upLimit;
	}

	public QueryExp getUpLimit() {
		return upLimit;
	}

	public void setUpLimit(QueryExp upLimit) {
		this.upLimit = upLimit;
	}

	public QueryExp getDownLimit() {
		return downLimit;
	}

	public void setDownLimit(QueryExp downLimit) {
		this.downLimit = downLimit;
	}
}
