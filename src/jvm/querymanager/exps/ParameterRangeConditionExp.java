package querymanager.exps;

public class ParameterRangeConditionExp extends RangeConditionExp {
	ParameterExp upLimit;
	ParameterExp downLimit;
	
	public ParameterRangeConditionExp(ParameterExp parameter, ParameterExp downLimit, ParameterExp upLimit, boolean isNot){
		super(parameter, isNot);
		this.downLimit = downLimit;
		this.upLimit = upLimit;
	}

	public ParameterExp getUpLimit() {
		return upLimit;
	}

	public void setUpLimit(ParameterExp upLimit) {
		this.upLimit = upLimit;
	}

	public ParameterExp getDownLimit() {
		return downLimit;
	}

	public void setDownLimit(ParameterExp downLimit) {
		this.downLimit = downLimit;
	}
}
