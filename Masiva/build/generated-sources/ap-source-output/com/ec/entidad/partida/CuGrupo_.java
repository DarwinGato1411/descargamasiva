package com.ec.entidad.partida;

import com.ec.entidad.partida.CuClase;
import com.ec.entidad.partida.CuCuenta;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-03-07T13:38:45")
@StaticMetamodel(CuGrupo.class)
public class CuGrupo_ { 

    public static volatile SingularAttribute<CuGrupo, BigDecimal> grupTotal;
    public static volatile SingularAttribute<CuGrupo, String> grupNumero;
    public static volatile CollectionAttribute<CuGrupo, CuCuenta> cuCuentaCollection;
    public static volatile SingularAttribute<CuGrupo, BigDecimal> grupSaldo;
    public static volatile SingularAttribute<CuGrupo, Integer> idClase;
    public static volatile SingularAttribute<CuGrupo, CuClase> cuClase;
    public static volatile SingularAttribute<CuGrupo, String> grupNombre;
    public static volatile SingularAttribute<CuGrupo, BigDecimal> grupOtro;
    public static volatile SingularAttribute<CuGrupo, Integer> idGrupo;

}