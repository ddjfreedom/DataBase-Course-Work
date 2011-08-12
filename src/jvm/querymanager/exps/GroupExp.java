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

}
