package com.ec.entidad;

import com.ec.entidad.Canton;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Provincia.class)
public class Provincia_ { 

    public static volatile SingularAttribute<Provincia, Integer> idProvincia;
    public static volatile SingularAttribute<Provincia, String> provNombre;
    public static volatile SingularAttribute<Provincia, Boolean> provEstado;
    public static volatile CollectionAttribute<Provincia, Canton> cantonCollection;
    public static volatile SingularAttribute<Provincia, Integer> provNumero;

}