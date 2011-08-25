package querymanager.exps;

public class CreateIndexExp extends Exp {
	UniqueOrCluster uniqueOrCluster;
	String indexName;
	String listName;
	IndexElementExpList elements;
	
	public CreateIndexExp(UniqueOrCluster uniqueOrCluster, String indexName, String listName, IndexElementExpList elements){
		this.uniqueOrCluster = uniqueOrCluster;
		this.indexName = indexName;
		this.listName = listName;
		this.elements = elements;
	}
	@Override
	Result execute() {
		return null;
	}

}
