package com.ec.entidad;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(Acumuladoventas.class)
public class Acumuladoventas_ { 

    public static volatile SingularAttribute<Acumuladoventas, BigDecimal> facTotalBaseGravaba;
    public static volatile SingularAttribute<Acumuladoventas, String> tidCodigo;
    public static volatile SingularAttribute<Acumuladoventas, Date> facFecha;
    public static volatile SingularAttribute<Acumuladoventas, Integer> id;
    public static volatile SingularAttribute<Acumuladoventas, Integer> numCompro;
    public static volatile SingularAttribute<Acumuladoventas, String> cliCedula;
    public static volatile SingularAttribute<Acumuladoventas, BigDecimal> facIva;
    public static volatile SingularAttribute<Acumuladoventas, String> forCodigo;

}