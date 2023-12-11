/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml;

import java.util.Date;

/**
 *
 * @author Darwin
 */
public class ImpuestoDocSustento {
    	public int codImpuestoDocSustento;
	public int codigoPorcentaje;
	public Date baseImponible;
	public int tarifa;
	public double valorImpuesto;

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
