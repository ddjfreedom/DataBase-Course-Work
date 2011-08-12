package querymanager.exps;

public class InOrNotConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean isNot;
	public InOrNotConditionExp(ParameterExp parameter, boolean isNot){
		this.parameter = parameter;
		this.isNot = isNot;
	}
}
