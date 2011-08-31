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

	public TableElementTypeExp getHead() {
		return head;
	}

	public void setHead(TableElementTypeExp head) {
		this.head = head;
	}

	public TableElementExpList getTail() {
		return tail;
	}

	public void setTail(TableElementExpList tail) {
		this.tail = tail;
	}
}
