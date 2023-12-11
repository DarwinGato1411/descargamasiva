package com.ec.dao.xml.retencion;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="comprobanteRetencion")
public class comprobanteRetencion { 
	private infoTributaria infoTributaria;
	private infoCompRetencion infoCompRetencion;
	private docsSustento docsSustento;

    public infoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(infoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public infoCompRetencion getInfoCompRetencion() {
        return infoCompRetencion;
    }

    public void setInfoCompRetencion(infoCompRetencion infoCompRetencion) {
        this.infoCompRetencion = infoCompRetencion;
    }

    public docsSustento getDocsSustento() {
        return docsSustento;
    }

    public void setDocsSustento(docsSustento docsSustento) {
        this.docsSustento = docsSustento;
    }

   
        
}

