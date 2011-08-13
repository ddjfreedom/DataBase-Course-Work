package querymanager.exps;

public class MathExp extends Exp {
	MathOp mathOp;
	TargetExp operand1;
	TargetExp operand2;
	
	public MathExp(TargetExp operand1, TargetExp operand2, MathOp mathOp){
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.mathOp = mathOp;
	}
	@Override
	Result execute() {
		return null;
	}
	public MathOp getMathOp() {
		return mathOp;
	}
	public void setMathOp(MathOp mathOp) {
		this.mathOp = mathOp;
	}
	public TargetExp getOperand1() {
		return operand1;
	}
	public void setOperand1(TargetExp operand1) {
		this.operand1 = operand1;
	}
	public TargetExp getOperand2() {
		return operand2;
	}
	public void setOperand2(TargetExp operand2) {
		this.operand2 = operand2;
	}

}
