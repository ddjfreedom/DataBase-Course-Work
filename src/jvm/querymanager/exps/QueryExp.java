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

	public SelectFromExp getSelectFrom() {
		return selectFrom;
	}

	public void setSelectFrom(SelectFromExp selectFrom) {
		this.selectFrom = selectFrom;
	}

	public OptionalExp getOptions() {
		return options;
	}

	public void setOptions(OptionalExp options) {
		this.options = options;
	}

}
