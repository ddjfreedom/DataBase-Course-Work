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

	public ParameterExp getParameter() {
		return parameter;
	}

	public void setParameter(ParameterExp parameter) {
		this.parameter = parameter;
	}

	public boolean getIsNot() {
		return isNot;
	}

	public void setIsNot(boolean isNot) {
		this.isNot = isNot;
	}

	public MatchPattern getPattern() {
		return pattern;
	}

	public void setPattern(MatchPattern pattern) {
		this.pattern = pattern;
	}
}
