package querymanager.exps;

public class DropIndexExp extends Exp {
	String indexName;
	
	public DropIndexExp(String indexName){
		this.indexName = indexName;
	}
	@Override
	Result execute() {
		return null;
	}

}
