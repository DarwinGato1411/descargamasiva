/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@XmlRootElement(name="comprobanteRetencion")
public class ComprobanteRetencion {

    private InfoTributaria infoTributaria;
    private InfoCompRetencion infoCompRetencion;
     private Impuestos impuestos;

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    @XmlElement
    public void setInfoTributaria(InfoTributaria value) {
        this.infoTributaria = value;
    }

    public InfoCompRetencion getInfoCompRetencion() {
        return infoCompRetencion;
    }

    @XmlElement
    public void setInfoCompRetencion(InfoCompRetencion value) {
        this.infoCompRetencion = value;
    }

    public Impuestos getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Impuestos impuestos) {
        this.impuestos = impuestos;
    }


}
