package querymanager.exps;

public class AggregationExp extends Exp {
	Aggregation aggr;
	DisOrAll disOrAll;
	public Aggregation getAggr() {
		return aggr;
	}
	public void setAggr(Aggregation aggr) {
		this.aggr = aggr;
	}
	public DisOrAll getDisOrAll() {
		return disOrAll;
	}
	public void setDisOrAll(DisOrAll disOrAll) {
		this.disOrAll = disOrAll;
	}
	public ParameterExp getParameter() {
		return parameter;
	}
	public void setParameter(ParameterExp parameter) {
		this.parameter = parameter;
	}
	ParameterExp parameter;
	
	public AggregationExp(Aggregation aggr, DisOrAll disOrAll, ParameterExp parameter){
		this.aggr = aggr;
		this.disOrAll = disOrAll;
		this.parameter = parameter;
	}
	@Override
	Result execute() {
		return null;
	}

}
