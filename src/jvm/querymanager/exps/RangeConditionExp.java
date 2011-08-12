package querymanager.exps;

public class RangeConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean isNot;
	public RangeConditionExp(ParameterExp parameter, boolean isNot){
		this.parameter = parameter;
		this.isNot = isNot;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
