/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.ComprasSri;
import com.ec.entidad.Tipoambiente;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author gato
 */
public class ServicioComprasSri {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(ComprasSri comprasSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(comprasSri);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Error en insertar comprasSri " + e.getMessage());
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        } finally {
            em.close();
        }

    }

    public void eliminar(ComprasSri comprasSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(comprasSri));

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  comprasSri " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(ComprasSri comprasSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(comprasSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar comprasSri " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<ComprasSri> findAll() {

        List<ComprasSri> listaComprasSris = new ArrayList<ComprasSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ComprasSri a");
            listaComprasSris = (List<ComprasSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta comprasSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaComprasSris;
    }

    public ComprasSri findByAutorizacion(String csriAutorizacion, Tipoambiente codTipoambiente) {

        ComprasSri retorno = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ComprasSri a WHERE a.csriAutorizacion=:csriAutorizacion AND a.codTipoambiente=:codTipoambiente");
            query.setParameter("csriAutorizacion", csriAutorizacion);
            query.setParameter("codTipoambiente", codTipoambiente);
            List<ComprasSri> datos = (List<ComprasSri>) query.getResultList();
            if (datos.size() > 0) {
                retorno = datos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta comprasSri " + e.getMessage());
            return null;
        } finally {
            em.close();
        }

        return retorno;
    }

    public List<ComprasSri> findNoVerificados() {

        List<ComprasSri> listaComprasSris = new ArrayList<ComprasSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ComprasSri a WHERE a.csriVerificado='N'");
            listaComprasSris = (List<ComprasSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta comprasSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaComprasSris;
    }

    /*documentos no procesados por rango de fechas*/
    public List<ComprasSri> findNoVerificadosBetweenFecha(Date inicio, Date fin, String tipo,Tipoambiente codTipoambiente) {

        List<ComprasSri> listaComprasSris = new ArrayList<ComprasSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ComprasSri a WHERE a.codTipoambiente=:codTipoambiente and a.csriVerificado='N' AND a.csriFechaEmision BETWEEN :inicio and :fin and a.csriTipoFactura=:tipo");
            query.setParameter("codTipoambiente", codTipoambiente);
            query.setParameter("inicio", inicio);
            query.setParameter("tipo", tipo);
            query.setParameter("fin", fin);
            listaComprasSris = (List<ComprasSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta comprasSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaComprasSris;
    }
    
    

    public void eliminarCabeceraSri(Date inicio, Date fin) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM ComprasSri a WHERE a.csriFechaEmision BETWEEN :inicio and :fin");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            int i = query.executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  comprasSri " + e.getMessage());
        } finally {
            em.close();
        }

    }

}
