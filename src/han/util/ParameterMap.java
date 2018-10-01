package han.util;

import java.util.HashMap;

public class ParameterMap extends HashMap {

	static final long serialVersionUID = -7504168962230132297L;

	private String objectName;

	public ParameterMap() {
	}

	public ParameterMap(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

}