package querymanager.exps;

public class ParameterExp extends Exp {
	String listName;
	String parameter;
	
	public ParameterExp(String listName, String parameter){
		this.listName = listName;
		this.parameter = parameter;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
