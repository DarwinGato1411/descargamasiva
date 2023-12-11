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
public class DocSustento {
    public int codSustento;
	public int codDocSustento;
	public double numDocSustento;
	public Date fechaEmisionDocSustento;
	public int pagoLocExt;
	public int tipoRegi;
	public int paisEfecPago;
	public String aplicConvDobTrib;
	public String pagExtSujRetNorLeg;
	public String pagoRegFis;
	public int totalComprobantesReembolso;
	public int totalBaseImponibleReembolso;
	public int totalImpuestoReembolso;
	public int totalSinImpuestos;
	public int importeTotal;
	public ImpuestosDocSustento impuestosDocSustento;
	public Retenciones retenciones;
	public Pagos pagos;

    public int getCodSustento() {
        return codSustento;
    }

    public void setCodSustento(int codSustento) {
        this.codSustento = codSustento;
    }

    public int getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(int codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    public double getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(double numDocSustento) {
        this.numDocSustento = numDocSustento;
    }

    public Date getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(Date fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public int getPagoLocExt() {
        return pagoLocExt;
    }

    public void setPagoLocExt(int pagoLocExt) {
        this.pagoLocExt = pagoLocExt;
    }

    public int getTipoRegi() {
        return tipoRegi;
    }

    public void setTipoRegi(int tipoRegi) {
        this.tipoRegi = tipoRegi;
    }

    public int getPaisEfecPago() {
        return paisEfecPago;
    }

    public void setPaisEfecPago(int paisEfecPago) {
        this.paisEfecPago = paisEfecPago;
    }

    public String getAplicConvDobTrib() {
        return aplicConvDobTrib;
    }

    public void setAplicConvDobTrib(String aplicConvDobTrib) {
        this.aplicConvDobTrib = aplicConvDobTrib;
    }

    public String getPagExtSujRetNorLeg() {
        return pagExtSujRetNorLeg;
    }

    public void setPagExtSujRetNorLeg(String pagExtSujRetNorLeg) {
        this.pagExtSujRetNorLeg = pagExtSujRetNorLeg;
    }

    public String getPagoRegFis() {
        return pagoRegFis;
    }

    public void setPagoRegFis(String pagoRegFis) {
        this.pagoRegFis = pagoRegFis;
    }

    public int getTotalComprobantesReembolso() {
        return totalComprobantesReembolso;
    }

    public void setTotalComprobantesReembolso(int totalComprobantesReembolso) {
        this.totalComprobantesReembolso = totalComprobantesReembolso;
    }

    public int getTotalBaseImponibleReembolso() {
        return totalBaseImponibleReembolso;
    }

    public void setTotalBaseImponibleReembolso(int totalBaseImponibleReembolso) {
        this.totalBaseImponibleReembolso = totalBaseImponibleReembolso;
    }

    public int getTotalImpuestoReembolso() {
        return totalImpuestoReembolso;
    }

    public void setTotalImpuestoReembolso(int totalImpuestoReembolso) {
        this.totalImpuestoReembolso = totalImpuestoReembolso;
    }

    public int getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(int totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public int getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(int importeTotal) {
        this.importeTotal = importeTotal;
    }

    public ImpuestosDocSustento getImpuestosDocSustento() {
        return impuestosDocSustento;
    }

    public void setImpuestosDocSustento(ImpuestosDocSustento impuestosDocSustento) {
        this.impuestosDocSustento = impuestosDocSustento;
    }

    public Retenciones getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(Retenciones retenciones) {
        this.retenciones = retenciones;
    }

    public Pagos getPagos() {
        return pagos;
    }

    public void setPagos(Pagos pagos) {
        this.pagos = pagos;
    }
        
        
        
}
