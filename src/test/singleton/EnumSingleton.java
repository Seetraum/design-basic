package test.singleton;

public enum EnumSingleton {
    INSTABCE;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public static EnumSingleton getInstance(){
        return INSTABCE;
    }
}
