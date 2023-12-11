/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import java.math.BigDecimal;
import javax.persistence.Transient;

/**
 *
 * @author Darwin
 */
public class ConsolidadoGastos {

    private String cabRucProveedor;
    private Long cantidadDoc;
    private BigDecimal baseimponible;
    private String clasificacion;

    public ConsolidadoGastos(String cabRucProveedor, Long cantidadDoc, BigDecimal baseimponible, String clasificacion) {
        this.cabRucProveedor = cabRucProveedor;
        this.cantidadDoc = cantidadDoc;
        this.baseimponible = baseimponible;
        this.clasificacion = clasificacion;
    }

    public ConsolidadoGastos() {
    }

    public String getCabRucProveedor() {
        return cabRucProveedor;
    }

    public void setCabRucProveedor(String cabRucProveedor) {
        this.cabRucProveedor = cabRucProveedor;
    }

    public Long getCantidadDoc() {
        return cantidadDoc;
    }

    public void setCantidadDoc(Long cantidadDoc) {
        this.cantidadDoc = cantidadDoc;
    }

    public BigDecimal getBaseimponible() {
        return baseimponible;
    }

    public void setBaseimponible(BigDecimal baseimponible) {
        this.baseimponible = baseimponible;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    
    
}
