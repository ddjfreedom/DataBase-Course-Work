package querymanager.exps;

public class ConstantCompareConditionExp extends CompareConditionExp {
	ParameterExp operand1;
	Constant constant;
	public ConstantCompareConditionExp(ParameterExp operand1,CompareOp compareOp, Constant constant){
		super(compareOp);
		this.operand1 = operand1;
		this.constant = constant;
	}
	public ParameterExp getOperand1() {
		return operand1;
	}
	public void setOperand1(ParameterExp operand1) {
		this.operand1 = operand1;
	}
	public Constant getConstant() {
		return constant;
	}
	public void setConstant(Constant constant) {
		this.constant = constant;
	}
}
