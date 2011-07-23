package querymanager.exps;

/*
 * Parameter
 * eg. a
 *     A.a
 */
public class ParameterTargetExp extends TargetExp {
	String listName;
	public ParameterTargetExp(String listName){
		this.listName = listName;
	}
}
