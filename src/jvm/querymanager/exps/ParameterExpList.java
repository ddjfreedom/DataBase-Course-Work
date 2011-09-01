package querymanager.exps;

public class ParameterExpList extends Exp {
	ParameterExp head;
	ParameterExpList tail;
	
	public ParameterExpList(ParameterExp head, ParameterExpList tail){
		this.head = head;
		this.tail = tail;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}
	public ParameterExp getHead() {
		return head;
	}
	public ParameterExpList getTail() {
		return tail;
	}
}
