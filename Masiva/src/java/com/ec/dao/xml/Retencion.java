/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml;

/**
 *
 * @author Darwin
 */
public class Retencion {

    public int codigo;
    public int codigoRetencion;
    public double baseImponible;
    public int porcentajeRetener;
    public double valorRetenido;

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

    public int getPorcentajeRetener() {
        return porcentajeRetener;
    }

    public void setPorcentajeRetener(int porcentajeRetener) {
        this.porcentajeRetener = porcentajeRetener;
    }

    public double getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(double valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

}
