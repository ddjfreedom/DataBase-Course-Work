package querymanager.exps;

public abstract class Exp {
	abstract Result execute();
	static enum DisOrAll{
		DISTICNT,
		ALL
	}
	static enum Aggregation{
		COUNT,
		SUM,
		AVG,
		MIN,
		MAX,
		NO_AGGR
	}
	static enum MathOp{
		PLUS,
		MINUS,
		TIMES,
		DIVIDE
	}
	static enum CompareOp{
		EQ,
		NEQ,
		LT,
		LE,
		GT,
		GE
	}
	static enum AnyOrAll{
		ANY,
		ALL
	}
}
