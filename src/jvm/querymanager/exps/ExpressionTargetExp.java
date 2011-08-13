package querymanager.exps;

/*
 * Expression Target
 * eg. A.a + B.b
 * 	   c + AVG(B)
 */
public class ExpressionTargetExp extends TargetExp {
	MathExp mathExp;
	public ExpressionTargetExp(MathExp mathExp, String alias){
		super(alias);
		this.mathExp = mathExp;
	}
	public MathExp getMathExp() {
		return mathExp;
	}
	public void setMathExp(MathExp mathExp) {
		this.mathExp = mathExp;
	}
}
