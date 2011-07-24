package querymanager.exps;

public class FromExpList extends Exp {
	FromExp head;
	FromExpList tail;
	
	public FromExpList(FromExp head, FromExpList tail){
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
