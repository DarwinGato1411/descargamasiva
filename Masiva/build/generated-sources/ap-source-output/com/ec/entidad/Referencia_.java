package com.ec.entidad;

import com.ec.entidad.Factura;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Referencia.class)
public class Referencia_ { 

    public static volatile SingularAttribute<Referencia, String> refCodigo;
    public static volatile SingularAttribute<Referencia, String> refNombre;
    public static volatile SingularAttribute<Referencia, Integer> idReferencia;
    public static volatile SingularAttribute<Referencia, Boolean> isprincipal;
    public static volatile CollectionAttribute<Referencia, Factura> facturaCollection;

}