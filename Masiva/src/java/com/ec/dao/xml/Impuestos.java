/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Darwin
 */
public class Impuestos {

    private List<Impuesto> impuesto;

    public List<Impuesto> getImpuesto() {
        return impuesto;
    }

    @XmlElement
    public void setImpuesto(List<Impuesto> impuesto) {
        this.impuesto = impuesto;
    }

}
