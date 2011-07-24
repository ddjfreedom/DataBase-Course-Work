package querymanager.exps;

public class ConstantCompareConditionExp extends ConditionExp {
	ParameterExp operand1;
	Constant constant;
	MathOp mathOp;
	
	public ConstantCompareConditionExp(ParameterExp operand1, Constant constant, MathOp mathOp){
		this.operand1 = operand1;
		this.constant = constant;
		this.mathOp = mathOp;
	}
}
