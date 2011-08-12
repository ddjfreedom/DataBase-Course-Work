package querymanager.exps;

public class QueryExp extends Exp {
	SelectFromExp selectFrom;
	OptionalExp options;
	public QueryExp(SelectFromExp selectFrom, OptionalExp options){
		this.selectFrom = selectFrom;
		this.options = options;
	}
	
	public QueryExp(){
	}
	@Override
	QueryResult execute() {
		return null;
	}

}
