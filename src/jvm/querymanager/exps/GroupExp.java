package querymanager.exps;

public class GroupExp extends Exp {
	ParameterExp parameter;
	ConditionExpLists conditions;
	
	public GroupExp(ParameterExp parameter, ConditionExpLists conditions){
		this.parameter = parameter;
		this.conditions = conditions;
	}
	
	@Override
	Result execute() {
		return null;
	}

	public ParameterExp getParameter() {
		return parameter;
	}

	public void setParameter(ParameterExp parameter) {
		this.parameter = parameter;
	}

	public ConditionExpLists getConditions() {
		return conditions;
	}

	public void setConditions(ConditionExpLists conditions) {
		this.conditions = conditions;
	}

}
