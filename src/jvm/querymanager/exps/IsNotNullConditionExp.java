package querymanager.exps;

public class IsNotNullConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean not;
	
	public IsNotNullConditionExp(ParameterExp parameter, boolean not){
		this.parameter = parameter;
		this.not = not;
	}
}
