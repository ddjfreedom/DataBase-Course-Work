package querymanager.exps;

public class ParameterExp extends Exp {
	String listName;
	String parameter;
	static final String STAR = "1111";
	public ParameterExp(String listName, String parameter){
		this.listName = listName;
		this.parameter = parameter;
	}
	@Override
	Result execute() {
		return null;
	}

}
