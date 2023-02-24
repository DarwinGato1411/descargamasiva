package com.ec.entidad;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-02-22T09:39:49")
@StaticMetamodel(TotalizadoRubros.class)
public class TotalizadoRubros_ { 

    public static volatile SingularAttribute<TotalizadoRubros, BigDecimal> total;
    public static volatile SingularAttribute<TotalizadoRubros, BigDecimal> subtotal;
    public static volatile SingularAttribute<TotalizadoRubros, Integer> codTipoambiente;
    public static volatile SingularAttribute<TotalizadoRubros, Integer> id;
    public static volatile SingularAttribute<TotalizadoRubros, String> clasificacion;
    public static volatile SingularAttribute<TotalizadoRubros, Date> cabFecha;

}