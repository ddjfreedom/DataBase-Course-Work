package querymanager.exps;

public class UpdateExp extends Exp {
	String listName;
	AssignmentExpList assign;
	WhereExp where;
	
	public UpdateExp(String listName, AssignmentExpList assign, WhereExp where){
		this.listName = listName;
		this.assign = assign;
		this.where = where;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
