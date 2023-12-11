/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Parametrizar;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.TotalizadoRubros;
import com.ec.entidad.sri.DetalleCompraSri;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioDetalleComprasSri;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
import com.ec.vista.servicios.ServicioTotalizadoRubros;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author gato
 */
public class ListaDetalleCompraSri extends SelectorComposer<Component> {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();
    ServicioDetalleComprasSri servicioDetalleComprasSri = new ServicioDetalleComprasSri();
    ServicioTotalizadoRubros servicioTotalizadoRubro = new ServicioTotalizadoRubros();

    private List<DetalleCompraSri> listaDetalleCompraSris = new ArrayList<DetalleCompraSri>();
    private List<TotalizadoRubros> listaTotalizadoRubros = new ArrayList<TotalizadoRubros>();

    private String buscar = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();

    UserCredential credential = new UserCredential();
    Parametrizar parametrizar = new Parametrizar();
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
    
    /**
     * busqueda
     */
    private String iprodClasificacio = "TODO";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public ListaDetalleCompraSri() {
        parametrizar = servicioParametrizar.finActivo();
        //muestra 7 dias atras
        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
        calendar.add(Calendar.DATE, -15); //el -3 indica que se le restaran 3 dias 
        inicio = calendar.getTime();

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;

        listaTipoambientes = servicioTipoAmbiente.findAll(credential.getUsuarioSistema());
        amb = servicioTipoAmbiente.finSelectFirst(credential.getUsuarioSistema());
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        if (parametrizar.getParFijarFecha()) {
            inicio = parametrizar.getParFechanicio() == null ? new Date() : parametrizar.getParFechanicio();
            fin = parametrizar.getParFechaFin() == null ? new Date() : parametrizar.getParFechaFin();
        }
    }

    @Command
    @NotifyChange({"listaDetalleCompraSris", "inicio", "fin"})
    public void buscarDetalleCompra() {
        findDetalleCompraSri();
    }

    private void findDetalleCompraSri() {
        listaDetalleCompraSris = servicioDetalleComprasSri.detalleCompraSriForTipoambiente(amb, iprodClasificacio, inicio, fin);
        setListaDetalleCompraSris(new ListModelList<DetalleCompraSri>(getListaDetalleCompraSris()));
    }

    @Command
    @NotifyChange({"listaDetalleCompraSris"})
    public void clasificar(@BindingParam("valor") DetalleCompraSri valor) {
        servicioDetalleComprasSri.modificar(valor);
    }

    @Command
    @NotifyChange({"listaTotalizadoRubros", "inicio", "fin"})
    public void buscarTotalizadoRubros() {
        findTotalizadoRubros();
    }

    private void findTotalizadoRubros() {
        System.out.println("asdasdasdasd");
        listaTotalizadoRubros = servicioTotalizadoRubro.findTotalizadoRubros(inicio, fin, amb);
        setListaTotalizadoRubros(new ListModelList<TotalizadoRubros>(getListaTotalizadoRubros()));
    }

    @Command
    public void exportListboxToExcel() throws Exception {
        try {
            File dosfile = new File(exportarExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarExcel() throws FileNotFoundException, IOException, ParseException {
//        List<DetalleCompraSri> descargar = servicioDetalleComprasSri.findByBetweenFechaSRI(inicio, fin);
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "comprassridetallado.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {
            int j = 0;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Comprassri");

            HSSFFont fuente = wb.createFont();
            fuente.setBoldweight((short) 700);
            HSSFCellStyle estiloCelda = wb.createCellStyle();
            estiloCelda.setWrapText(true);
            estiloCelda.setAlignment((short) 2);
            estiloCelda.setFont(fuente);

            HSSFCellStyle estiloCeldaInterna = wb.createCellStyle();
            estiloCeldaInterna.setWrapText(true);
            estiloCeldaInterna.setAlignment((short) 5);
            estiloCeldaInterna.setFont(fuente);

            HSSFCellStyle estiloCelda1 = wb.createCellStyle();
            estiloCelda1.setWrapText(true);
            estiloCelda1.setFont(fuente);

            HSSFRow r = null;

            HSSFCell c = null;
            r = s.createRow(0);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("FACTURA"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch0 = r.createCell(j++);
            ch0.setCellValue(new HSSFRichTextString("RUC"));
            ch0.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("NOMBRE"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("FECHA EMISION"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("CANTIDAD"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("DESCRIPCION"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("% IVA"));
            ch6.setCellStyle(estiloCelda);

            HSSFCell ch7 = r.createCell(j++);
            ch7.setCellValue(new HSSFRichTextString(" BASE 12"));
            ch7.setCellStyle(estiloCelda);

            HSSFCell ch8 = r.createCell(j++);
            ch8.setCellValue(new HSSFRichTextString(" BASE 0"));
            ch8.setCellStyle(estiloCelda);

            HSSFCell ch9 = r.createCell(j++);
            ch9.setCellValue(new HSSFRichTextString("TOTAL"));
            ch9.setCellStyle(estiloCelda);

            HSSFCell ch10 = r.createCell(j++);
            ch10.setCellValue(new HSSFRichTextString("RUBRO"));
            ch10.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;
            BigDecimal totalBase12 = BigDecimal.ZERO;
            BigDecimal totalBase0 = BigDecimal.ZERO;
            BigDecimal totalFinal = BigDecimal.ZERO;
            for (DetalleCompraSri item : listaDetalleCompraSris) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getIdCabeceraSri().getCabNumFactura()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getIdCabeceraSri().getCabRucProveedor()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getIdCabeceraSri().getCabProveedor()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getIdCabeceraSri().getCabFechaEmision())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(item.getIprodCantidad().toString()));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(item.getIprodDescripcion().toString()));

                HSSFCell c6 = r.createCell(i++);
                c6.setCellValue(new HSSFRichTextString(item.getIprodGrabaIva() ? "12" : "0"));

                HSSFCell c7 = r.createCell(i++);
                c7.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getBase12(), 2).toString()));
                totalBase12 = totalBase12.add(ArchivoUtils.redondearDecimales(item.getBase12(), 2));

                HSSFCell c71 = r.createCell(i++);
                c71.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getBase0(), 2).toString()));
                totalBase0 = totalBase0.add(ArchivoUtils.redondearDecimales(item.getBase0(), 2));

                HSSFCell c8 = r.createCell(i++);
                c8.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getIprodTotal(), 2).toString()));
                totalFinal = totalFinal.add(ArchivoUtils.redondearDecimales(item.getIprodTotal(), 2));

                HSSFCell c9 = r.createCell(i++);
                c9.setCellValue(new HSSFRichTextString(item.getIprodClasificacion().equals("N") ? "NEGOCIO"
                            : item.getIprodClasificacion().equals("S") ? "SALUD"
                            : item.getIprodClasificacion().equals("A") ? "ALIMENTACION"
                            : item.getIprodClasificacion().equals("V") ? "VESTIMENTA"
                            : item.getIprodClasificacion().equals("T") ? "TURISMO"
                            : item.getIprodClasificacion().equals("E") ? "EDUCACION"
                            : item.getIprodClasificacion().equals("VI") ? "VIVIENDA" : ""));
                /*autemta la siguiente fila*/
                rownum += 1;

            }

            j = 0;
            r = s.createRow(rownum);
            /*  HSSFCell ch201 = r.createCell(j++);
            ch201.setCellValue(new HSSFRichTextString(""));
            ch201.setCellStyle(estiloCelda);

            HSSFCell ch001 = r.createCell(j++);
            ch001.setCellValue(new HSSFRichTextString(""));
            ch001.setCellStyle(estiloCelda);

            HSSFCell ch101 = r.createCell(j++);
            ch101.setCellValue(new HSSFRichTextString(""));
            ch101.setCellStyle(estiloCelda);

            HSSFCell ch301 = r.createCell(j++);
            ch301.setCellValue(new HSSFRichTextString(""));
            ch301.setCellStyle(estiloCelda);

            HSSFCell ch401 = r.createCell(j++);
            ch401.setCellValue(new HSSFRichTextString(""));
            ch401.setCellStyle(estiloCelda);

            HSSFCell ch501 = r.createCell(j++);
            ch501.setCellValue(new HSSFRichTextString(""));
            ch501.setCellStyle(estiloCelda);

            HSSFCell ch601 = r.createCell(j++);
            ch601.setCellValue(new HSSFRichTextString(""));
            ch601.setCellStyle(estiloCelda);
             */
            HSSFCell ch701 = r.createCell(8);
            ch701.setCellValue(new HSSFRichTextString(totalBase12.toString()));
            ch701.setCellStyle(estiloCelda);

            HSSFCell ch801 = r.createCell(9);
            ch801.setCellValue(new HSSFRichTextString(totalBase0.toString()));
            ch801.setCellStyle(estiloCelda);

            HSSFCell ch901 = r.createCell(10);
            ch901.setCellValue(new HSSFRichTextString(totalFinal.toString()));
            ch901.setCellStyle(estiloCelda);

//            HSSFCell ch1001= r.createCell(j++);
//            ch1001.setCellValue(new HSSFRichTextString(""));
//            ch1001.setCellStyle(estiloCelda);
            for (int k = 0; k <= listaDetalleCompraSris.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    public List<Tipoambiente> getListaTipoambientes() {
        return listaTipoambientes;
    }

    public void setListaTipoambientes(List<Tipoambiente> listaTipoambientes) {
        this.listaTipoambientes = listaTipoambientes;
    }

    public Tipoambiente getAmb() {
        return amb;
    }

    public void setAmb(Tipoambiente amb) {
        this.amb = amb;
    }

    public List<DetalleCompraSri> getListaDetalleCompraSris() {
        return listaDetalleCompraSris;
    }

    public void setListaDetalleCompraSris(List<DetalleCompraSri> listaDetalleCompraSris) {
        this.listaDetalleCompraSris = listaDetalleCompraSris;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getBuscarNumFac() {
        return buscarNumFac;
    }

    public void setBuscarNumFac(String buscarNumFac) {
        this.buscarNumFac = buscarNumFac;
    }

    public String getIprodClasificacio() {
        return iprodClasificacio;
    }

    public void setIprodClasificacio(String iprodClasificacio) {
        this.iprodClasificacio = iprodClasificacio;
    }

    public List<TotalizadoRubros> getListaTotalizadoRubros() {
        return listaTotalizadoRubros;
    }

    public void setListaTotalizadoRubros(List<TotalizadoRubros> listaTotalizadoRubros) {
        this.listaTotalizadoRubros = listaTotalizadoRubros;
    }

}
