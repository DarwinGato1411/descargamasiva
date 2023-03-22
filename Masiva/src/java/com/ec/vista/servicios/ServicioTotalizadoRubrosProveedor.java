
package com.ec.vista.servicios;

import com.ec.entidad.Tipoambiente;
import com.ec.servicio.HelperPersistencia;
import com.ec.untilitario.ConsolidadoGastos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTotalizadoRubrosProveedor {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    
    public List<ConsolidadoGastos> findTotalizadoRubrosConsolidado(Date inicio, Date fin, Tipoambiente codTipoambiente) {
        System.out.println(inicio+" "+fin+" "+codTipoambiente.getCodTipoambiente());
        List<ConsolidadoGastos> listaTotalizadoRubross = new ArrayList<ConsolidadoGastos>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.untilitario.ConsolidadoGastos(a.cabRucProveedor,count(a.cabNumFactura),sum(a.baseimponible),a.clasificacion ) FROM TotalizadoRubrosProveedor a where a.cabFecha BETWEEN :inicio and :fin  and a.codTipoambiente=:codTipoambiente  GROUP BY a.clasificacion,a.cabRucProveedor");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setParameter("codTipoambiente", codTipoambiente.getCodTipoambiente());
            listaTotalizadoRubross = (List<ConsolidadoGastos>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta findTotalizadoRubrosConsolidado " + e.getMessage());
        } finally {
            em.close();
        }

        return listaTotalizadoRubross;
    }
}
