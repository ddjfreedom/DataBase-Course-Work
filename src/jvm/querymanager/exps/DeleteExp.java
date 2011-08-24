package querymanager.exps;

public class DeleteExp extends Exp {
	FromExp relation;
	WhereExp where;
	
	public DeleteExp(FromExp relation, WhereExp where){
		this.relation = relation;
		this.where = where;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
