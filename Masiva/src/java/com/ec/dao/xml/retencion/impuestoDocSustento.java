package com.ec.dao.xml.retencion;

import java.util.Date;

public class impuestoDocSustento { 
	private int codImpuestoDocSustento;
	private int codigoPorcentaje;
	private Date baseImponible;
	private int tarifa;
	private double valorImpuesto;

    public int getCodImpuestoDocSustento() {
        return codImpuestoDocSustento;
    }

    public void setCodImpuestoDocSustento(int codImpuestoDocSustento) {
        this.codImpuestoDocSustento = codImpuestoDocSustento;
    }

    public int getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(int codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public Date getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(Date baseImponible) {
        this.baseImponible = baseImponible;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public double getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(double valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

   
        
}

