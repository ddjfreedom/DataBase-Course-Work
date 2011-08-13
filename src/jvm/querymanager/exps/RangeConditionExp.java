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

	public ParameterExp getParameter() {
		return parameter;
	}

	public void setParameter(ParameterExp parameter) {
		this.parameter = parameter;
	}

	public boolean getIsNot() {
		return isNot;
	}

	public void setIsNot(boolean isNot) {
		this.isNot = isNot;
	}

}
