package querymanager.exps;

public class InsertExp extends Exp {
	String listName;
	ParameterExpList parameterList;
	
	public InsertExp(String listName, ParameterExpList parameterList){
		this.listName = listName;
		this.parameterList = parameterList;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getListName() {
		return listName;
	}

	public ParameterExpList getParameterList() {
		return parameterList;
	}
}
