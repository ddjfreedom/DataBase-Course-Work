package querymanager.exps;

/*
 * Aggregation Target
 * eg. COUNT([DISTINCT|ALL]*)
 */
public class AggregationTargetExp extends TargetExp {
	AggregationExp aggregationExp;
	public AggregationTargetExp(AggregationExp aggregationExp, String alias){
		super(alias);
		this.aggregationExp = aggregationExp;
	}
	public AggregationExp getAggregationExp() {
		return aggregationExp;
	}
	public void setAggregationExp(AggregationExp aggregationExp) {
		this.aggregationExp = aggregationExp;
	}
}
