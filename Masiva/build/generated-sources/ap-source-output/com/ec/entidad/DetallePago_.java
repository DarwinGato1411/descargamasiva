package com.ec.entidad;

import com.ec.entidad.Factura;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(DetallePago.class)
public class DetallePago_ { 

    public static volatile SingularAttribute<DetallePago, BigDecimal> detpTotal;
    public static volatile SingularAttribute<DetallePago, BigDecimal> detpSubtotal;
    public static volatile SingularAttribute<DetallePago, BigDecimal> detpMulta;
    public static volatile SingularAttribute<DetallePago, Integer> idDetallePago;
    public static volatile SingularAttribute<DetallePago, BigDecimal> detpAbono;
    public static volatile SingularAttribute<DetallePago, Factura> idFactura;
    public static volatile SingularAttribute<DetallePago, Date> detpFechaPago;
    public static volatile SingularAttribute<DetallePago, Integer> detpNumPago;
    public static volatile SingularAttribute<DetallePago, Date> detpFechaCobro;
    public static volatile SingularAttribute<DetallePago, BigDecimal> detpSaldo;

}