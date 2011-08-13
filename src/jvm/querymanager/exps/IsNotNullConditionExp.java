package querymanager.exps;

public class IsNotNullConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean isNot;
	
	public IsNotNullConditionExp(ParameterExp parameter, boolean isNot){
		this.parameter = parameter;
		this.isNot = isNot;
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
