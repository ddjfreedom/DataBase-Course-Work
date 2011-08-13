package querymanager.exps;

public class InOrNotConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean isNot;
	public InOrNotConditionExp(ParameterExp parameter, boolean isNot){
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
