package querymanager.exps;

/*
 * Parameter
 * eg. a
 *     A.a
 */
public class ParameterTargetExp extends TargetExp {
	ParameterExp parameter;
	public ParameterTargetExp(ParameterExp parameter, String alias){
		super(alias);
		this.parameter = parameter;
	}
}
