package querymanager.exps;

public class InOrNotConditionExp extends ConditionExp {
	ParameterExp parameter;
	
	public InOrNotConditionExp(ParameterExp parameter){
		this.parameter = parameter;
	}
}
