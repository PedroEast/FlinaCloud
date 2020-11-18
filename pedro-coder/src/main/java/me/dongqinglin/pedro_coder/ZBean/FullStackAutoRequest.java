package me.dongqinglin.pedro_coder.ZBean;

public class FullStackAutoRequest {
    private String createTableStr;
    private String daoApi;
    private String backendApi;
    private String frontendApi;

    public FullStackAutoRequest() {
    }

    public FullStackAutoRequest(String createTableStr, String daoApi, String backendApi, String frontendApi) {
        this.createTableStr = createTableStr;
        this.daoApi = daoApi;
        this.backendApi = backendApi;
        this.frontendApi = frontendApi;
    }

    public String getDaoApi() {
        return daoApi;
    }

    public String getBackendApi() {
        return backendApi;
    }

    public String getFrontendApi() {
        return frontendApi;
    }

    public String getCreateTableStr() {
        return createTableStr;
    }

    @Override
    public String toString() {
        return "FullStackAutoRequest{" +
                "CreateTableStr='" + createTableStr + '\'' +
                ", daoApi='" + daoApi + '\'' +
                ", backendApi='" + backendApi + '\'' +
                ", frontendApi='" + frontendApi + '\'' +
                '}';
    }
}
