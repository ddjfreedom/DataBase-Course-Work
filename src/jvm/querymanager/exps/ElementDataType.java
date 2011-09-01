package querymanager.exps;

public class ElementDataType extends Exp {
	DataType dataType;
	int num;
	public ElementDataType(DataType dataType, int num){
		this.dataType = dataType;
		this.num = num;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}
	public DataType getDataType() {
		return dataType;
	}
	public int getNum() {
		return num;
	}
}
