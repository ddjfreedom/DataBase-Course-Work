package querymanager.exps;

public class OptionalExp extends Exp {

	WhereExp where;
	GroupExp group;
	OrderExp order;
	
	public OptionalExp(WhereExp where, GroupExp group, OrderExp order){
		this.where = where;
		this.group = group;
		this.order = order;
	}
	
	//TODO add optional Exp
	
	@Override
	Result execute() {
		return null;
	}

	public WhereExp getWhere() {
		return where;
	}

	public void setWhere(WhereExp where) {
		this.where = where;
	}

	public GroupExp getGroup() {
		return group;
	}

	public void setGroup(GroupExp group) {
		this.group = group;
	}

	public OrderExp getOrder() {
		return order;
	}

	public void setOrder(OrderExp order) {
		this.order = order;
	}

}
