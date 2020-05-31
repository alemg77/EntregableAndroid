package com.example.entregableandroid.Modelo.ApiML;

public class ContenedorFirebase {
    private String documento;
    private Object data;

    public ContenedorFirebase(String documento, Object data) {
        this.documento = documento;
        this.data = data;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
