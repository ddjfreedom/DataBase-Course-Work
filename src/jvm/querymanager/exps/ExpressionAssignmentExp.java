package querymanager.exps;

public class ExpressionAssignmentExp extends AssignmentExp {
	MathExp mathExp;
	public ExpressionAssignmentExp(ParameterExp parameter, MathExp math) {
		super(parameter);
		this.mathExp = math;
	}

}
