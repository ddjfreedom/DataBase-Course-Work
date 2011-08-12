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

}
