package jvm.querymanager.exps;

public class FromExp extends Exp{

	String listName;
	String alias;
	QueryExp query;
	public FromExp(String listName, String alias, QueryExp query){
		this.listName = listName;
		this.alias = alias;
		this.query = query;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
