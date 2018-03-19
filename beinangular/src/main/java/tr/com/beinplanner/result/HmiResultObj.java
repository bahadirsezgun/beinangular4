package tr.com.beinplanner.result;

public class HmiResultObj {

	public String resultStatu;
	public String resultMessage;
	
	public Object resultObj;

	public int loadedValue;
	
	public int getLoadedValue() {
		return loadedValue;
	}

	public void setLoadedValue(int loadedValue) {
		this.loadedValue = loadedValue;
	}

	public String getResultStatu() {
		return resultStatu;
	}

	public void setResultStatu(String resultStatu) {
		this.resultStatu = resultStatu;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Object getResultObj() {
		return resultObj;
	}

	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}

}
