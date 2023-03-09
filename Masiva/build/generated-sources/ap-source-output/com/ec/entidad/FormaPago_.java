package com.ec.entidad;

import com.ec.entidad.Factura;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(FormaPago.class)
public class FormaPago_ { 

    public static volatile SingularAttribute<FormaPago, Integer> idFormaPago;
    public static volatile SingularAttribute<FormaPago, String> forNombre;
    public static volatile SingularAttribute<FormaPago, String> plazo;
    public static volatile SingularAttribute<FormaPago, String> unidadTiempo;
    public static volatile SingularAttribute<FormaPago, Boolean> isprincipal;
    public static volatile SingularAttribute<FormaPago, String> forCodigo;
    public static volatile CollectionAttribute<FormaPago, Factura> facturaCollection;

}