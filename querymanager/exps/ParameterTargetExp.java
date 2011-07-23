package querymanager.exps;

/*
 * Parameter
 * eg. a
 *     A.a
 */
public class ParameterTargetExp extends TargetExp {
	String listName;
	String parameter;
	public ParameterTargetExp(String listName, String parameter, String alias){
		super(alias);
		this.listName = listName;
		this.parameter = parameter;
	}
}
