package querymanager.exps;

/*
 * Expression Target
 * eg. A.a + B.b
 * 	   c + AVG(B)
 */
public class ExpressionTargetExp extends TargetExp {
	String listName;
	MathOp mathOp;
	TargetExp operand1;
	TargetExp operand2;
	
	public ExpressionTargetExp(String listName,MathOp mathOp, TargetExp operand1, TargetExp operand2, String alias){
		super(alias);
		this.listName = listName;
		this.mathOp = mathOp;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
}
