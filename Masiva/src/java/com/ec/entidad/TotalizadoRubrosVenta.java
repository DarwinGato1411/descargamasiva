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
@Table(name = "totalizado_rubros_venta")
public class TotalizadoRubrosVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "clasificacion")
    private String clasificacion;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "cod_tipoambiente")
    private Integer codTipoambiente;
    @Column(name = "cab_fecha")
    @Temporal(TemporalType.DATE)
    private Date cabFecha;

    @Column(name = "total_12")
    private BigDecimal total12;
    @Column(name = "total_0")
    private BigDecimal total0;

    public TotalizadoRubrosVenta() {
    }

    public TotalizadoRubrosVenta(String clasificacion, BigDecimal subtotal, BigDecimal total) {
        this.clasificacion = clasificacion;
        this.subtotal = subtotal;
        this.total = total;
    }

    public TotalizadoRubrosVenta(String clasificacion, BigDecimal subtotal, BigDecimal total, BigDecimal total12, BigDecimal total0) {
        this.clasificacion = clasificacion;
        this.subtotal = subtotal;
        this.total = total;

        this.total12 = total12;
        this.total0 = total0;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getCodTipoambiente() {
        return codTipoambiente;
    }

    public void setCodTipoambiente(Integer codTipoambiente) {
        this.codTipoambiente = codTipoambiente;
    }

    public Date getCabFecha() {
        return cabFecha;
    }

    public void setCabFecha(Date cabFecha) {
        this.cabFecha = cabFecha;
    }

    public BigDecimal getTotal12() {
        return total12;
    }

    public void setTotal12(BigDecimal total12) {
        this.total12 = total12;
    }

    public BigDecimal getTotal0() {
        return total0;
    }

    public void setTotal0(BigDecimal total0) {
        this.total0 = total0;
    }

}
