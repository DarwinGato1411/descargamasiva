package com.ec.entidad;

import com.ec.entidad.Parroquia;
import com.ec.entidad.Provincia;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Canton.class)
public class Canton_ { 

    public static volatile SingularAttribute<Canton, Provincia> idProvincia;
    public static volatile SingularAttribute<Canton, Integer> cantNumero;
    public static volatile SingularAttribute<Canton, Integer> idCanton;
    public static volatile SingularAttribute<Canton, Boolean> cantEstado;
    public static volatile CollectionAttribute<Canton, Parroquia> parroquiaCollection;
    public static volatile SingularAttribute<Canton, String> cantNombre;

}