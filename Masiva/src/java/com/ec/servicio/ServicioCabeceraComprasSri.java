/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipoambiente;
import com.ec.entidad.sri.CabeceraCompraSri;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCabeceraComprasSri {

    ServicioDetalleCompra servicioIngresoProducto = new ServicioDetalleCompra();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(CabeceraCompraSri cabeceraCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cabeceraCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cabeceraCompraSri " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(CabeceraCompraSri cabeceraCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cabeceraCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cabeceraCompraSri" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminarbyClaveAcceso(String clave) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM CabeceraCompraSri c WHERE c.cabClaveAcceso=:clave");
            query.setParameter("clave", clave);
            int deletedCount = query.executeUpdate();

            System.out.println("deletedCount " + deletedCount);
            //  em.remove(em.merge(cabeceraCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cabeceraCompraSri" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(CabeceraCompraSri cabeceraCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cabeceraCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cabeceraCompraSri " + e.getMessage()
            );
        } finally {
            em.close();
        }

    }

    public List<CabeceraCompraSri> findByBetweenFechaSRI(Date incio, Date fin, Tipoambiente codTipoambiente, String tipo) {

        List<CabeceraCompraSri> listaCabeceraCompras = new ArrayList<CabeceraCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM CabeceraCompraSri c WHERE c.codTipoambiente=:codTipoambiente and  c.cabFechaEmision BETWEEN :inicio AND :fin AND c.cabHomologado='N' AND c.cabTipo=:tipo ORDER BY c.cabFechaEmision DESC");
            query.setParameter("inicio", incio);
            query.setParameter("fin", fin);
            query.setParameter("codTipoambiente", codTipoambiente);
            query.setParameter("tipo", tipo);
            listaCabeceraCompras = (List<CabeceraCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta compra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCabeceraCompras;
    }

    public List<CabeceraCompraSri> findByNombreOrNumero(Tipoambiente codTipoambiente, String tipo, String busqueda) {

        List<CabeceraCompraSri> listaCabeceraCompras = new ArrayList<CabeceraCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM CabeceraCompraSri c WHERE c.codTipoambiente=:codTipoambiente  AND c.cabHomologado='N' AND c.cabTipo=:tipo AND ( c.cabNumFactura  LIKE :param OR c.cabProveedor LIKE :param OR c.cabRucProveedor LIKE :param) ORDER BY c.cabFechaEmision DESC");
            query.setParameter("param", "%" + busqueda + "%");
//            query.setParameter("fin", fin);
            query.setParameter("codTipoambiente", codTipoambiente);
            query.setParameter("tipo", tipo);
            listaCabeceraCompras = (List<CabeceraCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta compra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCabeceraCompras;
    }

    public CabeceraCompraSri findByClaveAccesoSRI(String clave) {

        CabeceraCompraSri respuesta = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM CabeceraCompraSri c WHERE c.cabClaveAcceso=:clave");
            query.setParameter("clave", clave);

            respuesta = (CabeceraCompraSri) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta findByClaveAccesoSRI " + e.getMessage());
        } finally {
            em.close();
        }

        return respuesta;
    }

    public void eliminarCabeceraComprasSri(Date inicio, Date fin) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM CabeceraCompraSri a WHERE a.cabFecha BETWEEN :inicio and :fin");
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
