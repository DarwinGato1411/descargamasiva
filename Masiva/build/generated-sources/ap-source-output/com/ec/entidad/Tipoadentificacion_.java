package com.ec.entidad;

import com.ec.entidad.Cliente;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Tipoadentificacion.class)
public class Tipoadentificacion_ { 

    public static volatile SingularAttribute<Tipoadentificacion, String> tidCodigo;
    public static volatile SingularAttribute<Tipoadentificacion, Integer> idTipoIdentificacion;
    public static volatile SingularAttribute<Tipoadentificacion, String> tidNombre;
    public static volatile CollectionAttribute<Tipoadentificacion, Cliente> clienteCollection;

}