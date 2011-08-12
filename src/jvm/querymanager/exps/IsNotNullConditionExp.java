package querymanager.exps;

public class IsNotNullConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean isNot;
	
	public IsNotNullConditionExp(ParameterExp parameter, boolean isNot){
		this.parameter = parameter;
		this.isNot = isNot;
	}
}
