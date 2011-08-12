package querymanager.exps;

public class LikeConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean isNot;
	MatchPattern pattern;
	
	public LikeConditionExp(ParameterExp parameter, MatchPattern pattern, boolean isNot){
		this.parameter = parameter;
		this.isNot = isNot;
		this.pattern = pattern;
	}
}
