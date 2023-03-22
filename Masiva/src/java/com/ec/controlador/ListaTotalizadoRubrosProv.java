/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Parametrizar;
import com.ec.entidad.Tipoambiente;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.ConsolidadoGastos;
import com.ec.vista.servicios.ServicioTotalizadoRubrosProveedor;
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
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;

/**
 *
 * @author gato
 */
public class ListaTotalizadoRubrosProv extends SelectorComposer<Component> {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();
    ServicioTotalizadoRubrosProveedor servicioTotalizadoRubro = new ServicioTotalizadoRubrosProveedor();

    private List<ConsolidadoGastos> listaTotalizadoRubros = new ArrayList<ConsolidadoGastos>();

    private String buscar = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();

    UserCredential credential = new UserCredential();
    Parametrizar parametrizar = new Parametrizar();
    private List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
    /**
     * busqueda
     */
    private String iprodClasificacio = "TODO";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public ListaTotalizadoRubrosProv() {

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
    }

    @Command
    @NotifyChange({"listaTotalizadoRubros", "inicio", "fin"})
    public void buscarTotalizadoRubros() {
        findTotalizadoRubros();
    }

    private void findTotalizadoRubros() {
        System.out.println("asdasdasdasd");
        listaTotalizadoRubros = servicioTotalizadoRubro.findTotalizadoRubrosConsolidado(inicio, fin, amb);
//        setListaTotalizadoRubros(new ListModelList<ConsolidadoGastos>(getListaTotalizadoRubros()));
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

        String pathSalida = directorioReportes + File.separator + "totalizado_rubros.xls";
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
            HSSFSheet s = wb.createSheet("Totalizado_rubros");

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
            ch2.setCellValue(new HSSFRichTextString("RUC PROVEEDOR"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch0 = r.createCell(j++);
            ch0.setCellValue(new HSSFRichTextString("CANTIDAD DE COMPROBANTES"));
            ch0.setCellStyle(estiloCelda);

            HSSFCell ch01 = r.createCell(j++);
            ch01.setCellValue(new HSSFRichTextString("BASE IMPONIBLE"));
            ch01.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("TIPO GASTO"));
            ch1.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;
            BigDecimal totalBaseImp = BigDecimal.ZERO;
            for (ConsolidadoGastos item : listaTotalizadoRubros) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCabRucProveedor()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(item.getCantidadDoc().toString()));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getBaseimponible(), 2).toString()));
                totalBaseImp = totalBaseImp.add(ArchivoUtils.redondearDecimales(item.getBaseimponible(), 2));
                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(item.getClasificacion()));

                /*autemta la siguiente fila*/
                rownum += 1;

            }
   r = s.createRow(rownum);
            j = 0;

            HSSFCell ch11 = r.createCell(j++);
            ch11.setCellValue(new HSSFRichTextString(""));
            ch11.setCellStyle(estiloCelda);

            HSSFCell ch12 = r.createCell(j++);
            ch12.setCellValue(new HSSFRichTextString(""));
            ch12.setCellStyle(estiloCelda);

            HSSFCell ch13 = r.createCell(j++);
            ch13.setCellValue(new HSSFRichTextString(totalBaseImp.toString()));
            ch13.setCellStyle(estiloCelda);

            HSSFCell ch14 = r.createCell(j++);
            ch14.setCellValue(new HSSFRichTextString(""));
            ch14.setCellStyle(estiloCelda);
            for (int k = 0; k <= listaTotalizadoRubros.size(); k++) {
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

    public List<ConsolidadoGastos> getListaTotalizadoRubros() {
        return listaTotalizadoRubros;
    }

    public void setListaTotalizadoRubros(List<ConsolidadoGastos> listaTotalizadoRubros) {
        this.listaTotalizadoRubros = listaTotalizadoRubros;
    }

}
