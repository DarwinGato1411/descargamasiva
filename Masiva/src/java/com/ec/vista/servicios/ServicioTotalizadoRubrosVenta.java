package com.ec.vista.servicios;

import com.ec.entidad.Tipoambiente;
import com.ec.entidad.TotalizadoRubrosVenta;
import com.ec.entidad.sri.CabeceraCompraSri;
import com.ec.servicio.HelperPersistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTotalizadoRubrosVenta {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<TotalizadoRubrosVenta> findTotalizadoRubrosVenta(Date inicio, Date fin, Tipoambiente codTipoambiente) {
        System.out.println(inicio + " " + fin + " " + codTipoambiente.getCodTipoambiente());
        List<TotalizadoRubrosVenta> listaTotalizadoRubrosVentas = new ArrayList<TotalizadoRubrosVenta>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.entidad.TotalizadoRubrosVenta(a.clasificacion,sum(a.subtotal),sum(a.total), sum(a.total12),sum(a.total0) )FROM TotalizadoRubrosVenta a where a.cabFecha BETWEEN :inicio and :fin  and a.codTipoambiente=:codTipoambiente GROUP BY a.clasificacion");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setParameter("codTipoambiente", codTipoambiente.getCodTipoambiente());
            listaTotalizadoRubrosVentas = (List<TotalizadoRubrosVenta>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta TotalizadoRubrosVentaVenta " + e.getMessage());
        } finally {
            em.close();
        }

        return listaTotalizadoRubrosVentas;
    }

    public List<CabeceraCompraSri> totalFacturasVenta(Date inicio, Date fin, Tipoambiente codTipoambiente) {
        System.out.println(inicio + " " + fin + " " + codTipoambiente.getCodTipoambiente());
        List<CabeceraCompraSri> listado = new ArrayList<CabeceraCompraSri>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT  a FROM CabeceraCompraSri a where a.cabFecha BETWEEN :inicio and :fin  and a.codTipoambiente=:codTipoambiente AND a.cabTipo=:cabTipo ");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setParameter("codTipoambiente", codTipoambiente);
            query.setParameter("cabTipo", "VEN");
            listado = (List<CabeceraCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta TotalizadoRubrosVentaVenta " + e.getMessage());
        } finally {
            em.close();
        }

        return listado;
    }

}
