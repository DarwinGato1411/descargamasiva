package com.ec.dao.xml.retencion;

public class retencion {

    private int codigo;
    private int codigoRetencion;
    private double baseImponible;
    private double porcentajeRetener;
    private double valorRetenido;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoRetencion() {
        return codigoRetencion;
    }

    public void setCodigoRetencion(int codigoRetencion) {
        this.codigoRetencion = codigoRetencion;
    }

    public double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public double getPorcentajeRetener() {
        return porcentajeRetener;
    }

    public void setPorcentajeRetener(double porcentajeRetener) {
        this.porcentajeRetener = porcentajeRetener;
    }

    public double getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(double valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

}
