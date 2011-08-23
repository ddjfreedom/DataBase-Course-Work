package querymanager.exps;

public class ConstantAssignmentExp extends AssignmentExp {
	Constant value;
	public ConstantAssignmentExp(ParameterExp parameter, Constant value) {
		super(parameter);
		this.value = value;
	}
}
