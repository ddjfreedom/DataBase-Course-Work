package querymanager.exps;

public class ConstantTargetExp extends TargetExp {
	Constant constant;
	public ConstantTargetExp(Constant constant){
		this.constant = constant;
	}
}
