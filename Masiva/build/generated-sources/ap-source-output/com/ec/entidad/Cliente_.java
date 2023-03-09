package com.ec.entidad;

import com.ec.entidad.Factura;
import com.ec.entidad.Guiaremision;
import com.ec.entidad.OrdenTrabajo;
import com.ec.entidad.Tipoadentificacion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> cliRazonSocial;
    public static volatile SingularAttribute<Cliente, String> cliApellidos;
    public static volatile CollectionAttribute<Cliente, OrdenTrabajo> ordenTrabajoCollection;
    public static volatile SingularAttribute<Cliente, String> cliClave;
    public static volatile SingularAttribute<Cliente, String> cliNombre;
    public static volatile SingularAttribute<Cliente, String> cliMovil;
    public static volatile SingularAttribute<Cliente, Date> clieFechaRegistro;
    public static volatile CollectionAttribute<Cliente, Guiaremision> guiaremisionCollection;
    public static volatile SingularAttribute<Cliente, Tipoadentificacion> idTipoIdentificacion;
    public static volatile SingularAttribute<Cliente, String> cliDireccion;
    public static volatile SingularAttribute<Cliente, BigDecimal> cliMontoAsignado;
    public static volatile CollectionAttribute<Cliente, Factura> facturaCollection;
    public static volatile SingularAttribute<Cliente, Integer> idCliente;
    public static volatile SingularAttribute<Cliente, String> cliTelefono;
    public static volatile SingularAttribute<Cliente, String> cliNombres;
    public static volatile SingularAttribute<Cliente, String> ciudad;
    public static volatile SingularAttribute<Cliente, String> cliCorreo;
    public static volatile SingularAttribute<Cliente, Integer> clietipo;
    public static volatile SingularAttribute<Cliente, String> cliCedula;

}