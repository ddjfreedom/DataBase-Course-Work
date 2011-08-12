package querymanager.exps;

public abstract class Exp {
	abstract Result execute();
	public static enum DisOrAll{
		DISTINCT,
		ALL
	}
	public static enum Aggregation{
		COUNT,
		SUM,
		AVG,
		MIN,
		MAX,
		NO_AGGR
	}
	public static enum MathOp{
		PLUS,
		MINUS,
		TIMES,
		DIVIDE
	}
	public static enum CompareOp{
		EQ,
		NEQ,
		LT,
		LE,
		GT,
		GE
	}
	public static enum AnyOrAll{
		ANY,
		ALL
	}
	public static enum AscOrDesc{
		ASC,
		DESC
	}
}
