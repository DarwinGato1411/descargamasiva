package com.ec.entidad;

import com.ec.entidad.CabeceraCompra;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(AmortizacionCompra.class)
public class AmortizacionCompra_ { 

    public static volatile SingularAttribute<AmortizacionCompra, Integer> detDias;
    public static volatile SingularAttribute<AmortizacionCompra, Integer> idAmortizacionCompra;
    public static volatile SingularAttribute<AmortizacionCompra, BigDecimal> detValor;
    public static volatile SingularAttribute<AmortizacionCompra, CabeceraCompra> idCompra;
    public static volatile SingularAttribute<AmortizacionCompra, Date> detFecha;

}