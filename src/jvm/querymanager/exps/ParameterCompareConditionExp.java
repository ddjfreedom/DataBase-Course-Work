package querymanager.exps;


public class ParameterCompareConditionExp extends CompareConditionExp {
	ParameterExp parameter1;
	ParameterExp parameter2;
	
	public ParameterCompareConditionExp(ParameterExp parameter1,CompareOp compareOp, ParameterExp parameter2){
		super(compareOp);
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
	}

	public ParameterExp getParameter1() {
		return parameter1;
	}

	public void setParameter1(ParameterExp parameter1) {
		this.parameter1 = parameter1;
	}

	public ParameterExp getParameter2() {
		return parameter2;
	}

	public void setParameter2(ParameterExp parameter2) {
		this.parameter2 = parameter2;
	}
}
