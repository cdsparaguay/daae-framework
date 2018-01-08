package daee.learner.framework.dto;

public class ParamDTO {

    private String method;
    private String value;
    private String dataType;

    public ParamDTO(String method, String value, String dataType) {
        this.method = method;
        this.value = value;
        this.dataType = dataType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
