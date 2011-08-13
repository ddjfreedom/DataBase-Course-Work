package querymanager.exps;

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

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public QueryExp getQuery() {
		return query;
	}

	public void setQuery(QueryExp query) {
		this.query = query;
	}

}
