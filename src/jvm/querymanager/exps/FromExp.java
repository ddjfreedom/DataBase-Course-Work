package querymanager.exps;

public class FromExp extends Exp{

	String listName;
	String alias;

	public FromExp(String listName, String alias){
		this.listName = listName;
		this.alias = alias;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
