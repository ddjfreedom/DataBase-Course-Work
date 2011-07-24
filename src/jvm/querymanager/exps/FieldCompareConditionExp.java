package querymanager.exps;

public class FieldCompareConditionExp extends CompareConditionExp {
	ParameterExp operand1;
	ParameterExp operand2;
	MathOp mathOp;
	
	public FieldCompareConditionExp(ParameterExp operand1, ParameterExp operand2, MathOp mathOp){
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.mathOp = mathOp;
	}
}
