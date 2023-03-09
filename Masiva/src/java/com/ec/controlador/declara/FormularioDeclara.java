/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador.declara;

import com.ec.entidad.Tipoambiente;
import com.ec.entidad.TotalizadoRubros;
import com.ec.entidad.TotalizadoRubrosVenta;
import com.ec.entidad.sri.CabeceraCompraSri;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.vista.servicios.ServicioTotalizadoRubros;
import com.ec.vista.servicios.ServicioTotalizadoRubrosVenta;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author gato
 */
public class FormularioDeclara {

    ServicioTotalizadoRubros servicioTotalizadoRubros = new ServicioTotalizadoRubros();
    ServicioTotalizadoRubrosVenta servicioTotalizadoRubrosVenta = new ServicioTotalizadoRubrosVenta();
    TotalizadoRubros totalizadoRubros = new TotalizadoRubros();
    TotalizadoRubrosVenta totalizadoRubrosVenta = new TotalizadoRubrosVenta();

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
    private Tipoambiente amb = new Tipoambiente();

    private Date fechainicio = new Date();
    private Date fechafin = new Date();

    UserCredential credential = new UserCredential();

    private Integer numeroCompro = 0;

    public FormularioDeclara() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        listaTipoambientes = servicioTipoAmbiente.findAll(credential.getUsuarioSistema());
        amb = servicioTipoAmbiente.finSelectFirst(credential.getUsuarioSistema());
        buscarRubros();
    }

    private void buscarRubros() {
        List<TotalizadoRubros> listRub = servicioTotalizadoRubros.findTotalizadoRubros(fechainicio, fechafin, amb);
        List<TotalizadoRubrosVenta> listRubV = servicioTotalizadoRubrosVenta.findTotalizadoRubrosVenta(fechainicio, fechafin, amb);
        for (TotalizadoRubros totalizadoRubros1 : listRub) {
            if (totalizadoRubros1.getClasificacion().equals("NEGOCIO")) {
                totalizadoRubros = totalizadoRubros1;
            }
        }

        totalizadoRubrosVenta = listRubV.isEmpty() ? null : listRubV.get(0);
        List<CabeceraCompraSri> numero = servicioTotalizadoRubrosVenta.totalFacturasVenta(fechainicio, fechafin, amb);
        numeroCompro = numero.isEmpty() ? 0 : numero.size();
    }

    @Command
    @NotifyChange({"totalizadoRubros", "totalizadoRubrosVenta", "fechainicio", "fechafin", "numeroCompro"})
    public void buscarFechas() {
        buscarRubros();
    }

    public TotalizadoRubros getTotalizadoRubros() {
        return totalizadoRubros;
    }

    public void setTotalizadoRubros(TotalizadoRubros totalizadoRubros) {
        this.totalizadoRubros = totalizadoRubros;
    }

    public TotalizadoRubrosVenta getTotalizadoRubrosVenta() {
        return totalizadoRubrosVenta;
    }

    public void setTotalizadoRubrosVenta(TotalizadoRubrosVenta totalizadoRubrosVenta) {
        this.totalizadoRubrosVenta = totalizadoRubrosVenta;
    }

    public List<Tipoambiente> getListaTipoambientes() {
        return listaTipoambientes;
    }

    public void setListaTipoambientes(List<Tipoambiente> listaTipoambientes) {
        this.listaTipoambientes = listaTipoambientes;
    }

    public Tipoambiente getAmb() {
        return amb;
    }

    public void setAmb(Tipoambiente amb) {
        this.amb = amb;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public Integer getNumeroCompro() {
        return numeroCompro;
    }

    public void setNumeroCompro(Integer numeroCompro) {
        this.numeroCompro = numeroCompro;
    }

}
