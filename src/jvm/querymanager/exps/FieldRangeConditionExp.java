package querymanager.exps;

public class FieldRangeConditionExp extends RangeConditionExp {
	ParameterExp upLimit;
	ParameterExp downLimit;
	
	public FieldRangeConditionExp(ParameterExp parameter, ParameterExp downLimit, ParameterExp upLimit){
		super(parameter);
		this.downLimit = downLimit;
		this.upLimit = upLimit;
	}
}
