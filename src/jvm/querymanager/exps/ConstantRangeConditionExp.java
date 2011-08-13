package querymanager.exps;

public class ConstantRangeConditionExp extends RangeConditionExp {
	Constant upLimit;
	Constant downLimit;
	
	public ConstantRangeConditionExp(ParameterExp parameter, Constant downLimit, Constant upLimit, boolean isNot){
		super(parameter, isNot);
		this.downLimit = downLimit;
		this.upLimit = upLimit;
	}

	public Constant getUpLimit() {
		return upLimit;
	}

	public void setUpLimit(Constant upLimit) {
		this.upLimit = upLimit;
	}

	public Constant getDownLimit() {
		return downLimit;
	}

	public void setDownLimit(Constant downLimit) {
		this.downLimit = downLimit;
	}
}
