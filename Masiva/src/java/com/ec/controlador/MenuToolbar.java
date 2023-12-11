/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.Parametrizar;
import com.ec.servicio.ServicioParametrizar;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author gato
 */
public class MenuToolbar {

    private Parametrizar parametrizar;
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();

    public MenuToolbar() {
        parametrizar = servicioParametrizar.finActivo();
    }

    @Command
    public void facturar(@BindingParam("valor") DetalleFacturaDAO valor) {
        Executions.sendRedirect("/venta/facturar.zul");
    }

    @Command
    public void nuevoCliente() {

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/nuevo/cliente.zul", null, null);
        window.doModal();

    }

    @Command
    public void nuevoProducto() {

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/nuevo/producto.zul", null, null);
        window.doModal();

    }

    @Command
    public void fejarFechaEstado() {

        servicioParametrizar.modificar(parametrizar);

    }

    @Command
    public void fejarFecha() {

        if (parametrizar.getParFijarFecha()) {
            servicioParametrizar.modificar(parametrizar);
            Clients.showNotification("Fecha de inicio y fin fijada",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);
        } else {
            Clients.showNotification("FDebe marcar la casilla establecer fecha",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 1000, true);
        }

    }

    public Parametrizar getParametrizar() {
        return parametrizar;
    }

    public void setParametrizar(Parametrizar parametrizar) {
        this.parametrizar = parametrizar;
    }

}
