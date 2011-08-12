package querymanager.exps;

public class AggregationExp extends Exp {
	Aggregation aggr;
	DisOrAll disOrAll;
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
