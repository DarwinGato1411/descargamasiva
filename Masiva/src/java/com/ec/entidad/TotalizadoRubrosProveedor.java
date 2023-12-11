/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "totalizado_rubros_proveedor")
public class TotalizadoRubrosProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "clasificacion")
    private String clasificacion;
    @Column(name = "cab_ruc_proveedor")
    private String cabRucProveedor;
    @Column(name = "cab_num_factura")
    private String cabNumFactura;
    @Column(name = "baseimponible")
    private BigDecimal baseimponible;
    @Column(name = "cab_fecha")
    @Temporal(TemporalType.DATE)
    private Date cabFecha;
    @Column(name = "cod_tipoambiente")
    private Integer codTipoambiente;

    public TotalizadoRubrosProveedor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getCabRucProveedor() {
        return cabRucProveedor;
    }

    public void setCabRucProveedor(String cabRucProveedor) {
        this.cabRucProveedor = cabRucProveedor;
    }

    public String getCabNumFactura() {
        return cabNumFactura;
    }

    public void setCabNumFactura(String cabNumFactura) {
        this.cabNumFactura = cabNumFactura;
    }

    public BigDecimal getBaseimponible() {
        return baseimponible;
    }

    public void setBaseimponible(BigDecimal baseimponible) {
        this.baseimponible = baseimponible;
    }

    public Date getCabFecha() {
        return cabFecha;
    }

    public void setCabFecha(Date cabFecha) {
        this.cabFecha = cabFecha;
    }

    public Integer getCodTipoambiente() {
        return codTipoambiente;
    }

    public void setCodTipoambiente(Integer codTipoambiente) {
        this.codTipoambiente = codTipoambiente;
    }

}
