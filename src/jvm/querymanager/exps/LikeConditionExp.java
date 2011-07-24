package querymanager.exps;

public class LikeConditionExp extends ConditionExp {
	ParameterExp parameter;
	boolean not;
	MatchPattern pattern;
	
	public LikeConditionExp(ParameterExp parameter, boolean not, MatchPattern pattern){
		this.parameter = parameter;
		this.not = not;
		this.pattern = pattern;
	}
}
