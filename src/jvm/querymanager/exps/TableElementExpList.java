package querymanager.exps;

public class TableElementExpList extends Exp {
	TableElementTypeExp head;
	TableElementExpList tail;
	
	public TableElementExpList(TableElementTypeExp head, TableElementExpList tail){
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
