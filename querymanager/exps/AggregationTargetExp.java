package querymanager.exps;

/*
 * Aggregation Targer
 * eg. COUNT([DISTINCT|ALL]*)
 */
public class AggregationTargetExp extends TargetExp {
	Aggregation aggr;
	DisOrAll disOrAll;
	ParameterTargetExp parameter;
	public AggregationTargetExp(Aggregation aggr, DisOrAll disOrAll, ParameterTargetExp parameter){
		this.aggr = aggr;
		this.disOrAll = disOrAll;
		this.parameter = parameter;
	}
}
