package com.ec.entidad;

import com.ec.entidad.Subcategoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile CollectionAttribute<Categoria, Subcategoria> subcategoriaCollection;
    public static volatile SingularAttribute<Categoria, Subcategoria> subcategoria;
    public static volatile SingularAttribute<Categoria, String> catNombre;
    public static volatile SingularAttribute<Categoria, Integer> idCategoria;

}