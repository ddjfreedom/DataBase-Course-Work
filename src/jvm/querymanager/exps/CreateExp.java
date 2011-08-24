package querymanager.exps;

public class CreateExp extends Exp {
	String tableName;
	TableElementExpList elements;
	
	public CreateExp(String tableName, TableElementExpList elements){
		this.tableName = tableName;
		this.elements = elements;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
