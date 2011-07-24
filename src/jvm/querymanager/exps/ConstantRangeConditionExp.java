package querymanager.exps;

public class ConstantRangeConditionExp extends RangeConditionExp {
	Constant upLimit;
	Constant downLimit;
	
	public ConstantRangeConditionExp(ParameterExp parameter, Constant downLimit, Constant upLimit){
		super(parameter);
		this.downLimit = downLimit;
		this.upLimit = upLimit;
	}
}
