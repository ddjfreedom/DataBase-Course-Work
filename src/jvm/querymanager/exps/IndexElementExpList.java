package querymanager.exps;

public class IndexElementExpList extends Exp {
	IndexElementExp head;
	IndexElementExpList tail;
	
	public IndexElementExpList(IndexElementExp head, IndexElementExpList tail){
		this.head = head;
		this.tail = tail;
	}
	@Override
	Result execute() {
		return null;
	}

}
