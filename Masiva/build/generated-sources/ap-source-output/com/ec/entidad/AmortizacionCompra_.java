package com.ec.entidad;

import com.ec.entidad.CabeceraCompra;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-02-22T09:39:49")
@StaticMetamodel(AmortizacionCompra.class)
public class AmortizacionCompra_ { 

    public static volatile SingularAttribute<AmortizacionCompra, Integer> detDias;
    public static volatile SingularAttribute<AmortizacionCompra, Integer> idAmortizacionCompra;
    public static volatile SingularAttribute<AmortizacionCompra, BigDecimal> detValor;
    public static volatile SingularAttribute<AmortizacionCompra, CabeceraCompra> idCompra;
    public static volatile SingularAttribute<AmortizacionCompra, Date> detFecha;

}