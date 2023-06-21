/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.xml.Comprobante;
import com.ec.dao.xml.ComprobanteRetencion;
import com.ec.dao.xml.Impuesto;
import com.ec.dao.xml.RetencionXML;
import com.ec.dao.xml.factura.Detalle;
import com.ec.dao.xml.factura.FacturaCompraXML;
import com.ec.dao.xml.factura.TotalImpuesto;
import com.ec.dao.xml.retencion.comprobanteRetencion;
import com.ec.dao.xml.retencion.retencion;
import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.ComprasSri;
import com.ec.entidad.DetalleCompra;
import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Tipokardex;
import com.ec.entidad.docsri.RetencionCompraSri;
import com.ec.entidad.sri.CabeceraCompraSri;
import com.ec.entidad.sri.DetalleCompraSri;
import com.ec.entidad.sri.DetalleRetencionCompraSri;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCabeceraComprasSri;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioComprasSri;
import com.ec.servicio.ServicioDetalleCompra;
import com.ec.servicio.ServicioDetalleComprasSri;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioDetalleRetencionSri;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioGeneral;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioRetencionSri;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoIdentificacionCompra;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.AutorizarDocumentos;
import com.ec.untilitario.TotalKardex;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
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
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

import org.apache.commons.io.FileDeleteStrategy;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author gato
 */
public class ListaRetencionesSri extends SelectorComposer<Component> {

    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();
    ServicioComprasSri servicioComprasSri = new ServicioComprasSri();
    ServicioDetalleCompra Servici = new ServicioDetalleCompra();
    ServicioCompra servicioCompra = new ServicioCompra();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioDetalleCompra servicioDetalleCompra = new ServicioDetalleCompra();

    ServicioCabeceraComprasSri servicioCabeceraComprasri = new ServicioCabeceraComprasSri();
    ServicioDetalleComprasSri servicioDetalleComprasSri = new ServicioDetalleComprasSri();

    private List<CabeceraCompra> listaCabeceraCompras = new ArrayList<CabeceraCompra>();
    private String buscar = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();
    ServicioProveedor servicioProveedor = new ServicioProveedor();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    UserCredential credential = new UserCredential();
    Parametrizar parametrizar = new Parametrizar();

    ServicioTipoIdentificacionCompra servicioTipoIdentificacionCompra = new ServicioTipoIdentificacionCompra();

    private List<ComprasSri> listaComprasSris = new ArrayList<ComprasSri>();

    /*subir rchivo*/
    //subir pdfArchivo
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;
//reporte
    AMedia fileContent = null;
    Connection con = null;

    private Date inicioComp = new Date();
    private Date finComp = new Date();

    /*Compras*/
    private ListModelList<CabeceraCompraSri> listaComprasSriModel;
    private Set<CabeceraCompraSri> registrosSeleccionados = new HashSet<CabeceraCompraSri>();
    private List<CabeceraCompraSri> listaCabeceraCompraSris = new ArrayList<CabeceraCompraSri>();

    ServicioRetencionSri retencionSri = new ServicioRetencionSri();
    ServicioDetalleRetencionSri detalleRetencionSri = new ServicioDetalleRetencionSri();
    private List<RetencionCompraSri> listaRetencioSri = new ArrayList<RetencionCompraSri>();
    private Date inicioRet = new Date();
    private Date finRet = new Date();

    private static String SRIFACCOMPRAS = "SRIFACCOMPRAS";
    private static String SRIRETENCION = "SRIRETENCION";
    /*DETALLE DEL KARDEX Y DETALLE KARDEX*/
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    ServicioGeneral servicioGeneral = new ServicioGeneral();

    private List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

    }

    public ListaRetencionesSri() {

        //muestra 7 dias atras
        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
        calendar.add(Calendar.DATE, -7); //el -3 indica que se le restaran 3 dias 
        inicio = calendar.getTime();

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        parametrizar = servicioParametrizar.finActivo();
        findByBetweenFecha();
        listaTipoambientes = servicioTipoAmbiente.findAll(credential.getUsuarioSistema());

        amb = servicioTipoAmbiente.finSelectFirst(credential.getUsuarioSistema());
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        if (amb != null) {
            PATH_BASE = amb.getAmUnidadDisco() + amb.getAmDirBaseArchivos() + File.separator
                        + amb.getAmDirXml();

            String folderComprasSri = PATH_BASE + File.separator + SRIFACCOMPRAS + File.separator;
            File folderGen = new File(folderComprasSri);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            String folderRetencionesSri = PATH_BASE + File.separator + SRIRETENCION + File.separator;
            File folderRet = new File(folderRetencionesSri);
            if (!folderRet.exists()) {
                folderRet.mkdirs();
            }
        }

        if (parametrizar.getParFijarFecha()) {
            inicio = parametrizar.getParFechanicio() == null ? new Date() : parametrizar.getParFechanicio();
            fin = parametrizar.getParFechaFin() == null ? new Date() : parametrizar.getParFechaFin();
        }

    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<CabeceraCompraSri>) getListaComprasSriModel()).getSelection();
    }

    private void buscarLikeNombre() {
        listaCabeceraCompras = servicioCompra.findCabProveedorSRI(buscar);
    }

    private void findByBetweenFecha() {
        listaCabeceraCompras = servicioCompra.findByBetweenFechaSRI(inicio, fin);
    }

    private void findComprasSriByBetweenFecha() {
        listaComprasSris = servicioComprasSri.findNoVerificadosBetweenFecha(inicio, fin, "RET", amb);

    }

    private void findCabeceraComprasSriByBetweenFecha() {

        listaCabeceraCompraSris = servicioCabeceraComprasri.findByBetweenFechaSRI(inicio, fin, amb, "RET");
        setListaComprasSriModel(new ListModelList<CabeceraCompraSri>(getListaCabeceraCompraSris()));
        ((ListModelList<CabeceraCompraSri>) listaComprasSriModel).setMultiple(true);

    }

    private void findByNumFac() {
        listaCabeceraCompras = servicioCompra.findByNumeroFacturaSRI(buscarNumFac);
    }

    private void findBetweenRetenciones() {
        listaRetencioSri = retencionSri.findRetencionesBetween(inicio, fin, amb);
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin", "listaComprasSriModel"})
    public void eliminarCabeceraSRI() {
        if (Messagebox.show("Esta seguro de eliminar los registros?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
            servicioComprasSri.eliminarCabeceraSri(inicio, fin, amb);
            findComprasSriByBetweenFecha();
            Clients.showNotification("Registros eliminados correctamente ",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);
        }
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin", "listaComprasSriModel", "listaRetencioSri"})
    public void eliminarRetencionSRI() {
        if (Messagebox.show("Esta seguro de eliminar los registros?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
            retencionSri.eliminarRetencionesSri(inicio, fin, amb);
            findBetweenRetenciones();
            Clients.showNotification("Registros eliminados correctamente ",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);
        }
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin", "listaComprasSriModel"})
    public void cargarfacturasSistema() {
        for (CabeceraCompraSri item : registrosSeleccionados) {
            if (servicioCompra.findByNumeroFacturaAndProveedor(item.getCabNumFactura(), item.getCabProveedor()).isEmpty()) {
                CabeceraCompra cabecera = ArchivoUtils.compraSriToCompra(item);
                servicioCompra.crear(cabecera);

                for (DetalleCompraSri object : servicioDetalleComprasSri.detallebyCompraSri(item)) {
                    DetalleCompra detalleCompra = ArchivoUtils.detalleSriToDetalleCompra(object, cabecera);
                    servicioDetalleCompra.crear(detalleCompra);

                    /*INGRESAMOS LO MOVIMIENTOS AL KARDEX*/
                    Kardex kardex = null;
                    DetalleKardex detalleKardex = null;
                    Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("ING");
                    if (servicioKardex.FindALlKardexs(detalleCompra.getIdProducto()) == null) {
                        kardex = new Kardex();
                        kardex.setIdProducto(detalleCompra.getIdProducto());
                        kardex.setKarDetalle("Inicio de inventario desde la facturacion para el producto: " + detalleCompra.getIdProducto().getProdNombre());
                        kardex.setKarFecha(new Date());
                        kardex.setKarFechaKardex(new Date());
                        kardex.setKarTotal(BigDecimal.ZERO);
                        servicioKardex.crear(kardex);
                    }
                    detalleKardex = new DetalleKardex();
                    kardex = servicioKardex.FindALlKardexs(detalleCompra.getIdProducto());
                    detalleKardex.setIdKardex(kardex);
                    detalleKardex.setDetkFechakardex(item.getCabFecha());
                    detalleKardex.setDetkFechacreacion(new Date());
                    detalleKardex.setIdTipokardex(tipokardex);
                    detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                    detalleKardex.setDetkDetalles("Aumenta al kardex facturacion con: FACTC-" + item.getCabNumFactura());
                    detalleKardex.setIdCompra(cabecera);

                    detalleKardex.setDetkCantidad(detalleCompra.getIprodCantidad());
                    servicioDetalleKardex.crear(detalleKardex);
                    TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                    BigDecimal total = totales.getTotalKardex();
                    kardex.setKarTotal(total);
                    servicioKardex.modificar(kardex);
                }

            }

        }
        servicioGeneral.corregirProductos();
        Clients.showNotification("Facturas cargadas correctamente ",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);

    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin", "listaComprasSriModel", "listaRetencioSri"})
    public void buscarComprasSri() {
        if (amb != null) {
            PATH_BASE = amb.getAmUnidadDisco() + amb.getAmDirBaseArchivos() + File.separator
                        + amb.getAmDirXml();

            String folderComprasSri = PATH_BASE + File.separator + SRIFACCOMPRAS + File.separator;
            File folderGen = new File(folderComprasSri);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            String folderRetencionesSri = PATH_BASE + File.separator + SRIRETENCION + File.separator;
            File folderRet = new File(folderRetencionesSri);
            if (!folderRet.exists()) {
                folderRet.mkdirs();
            }
        }
        findComprasSriByBetweenFecha();
        findBetweenRetenciones();
    }

    @Command
    @NotifyChange({"listaRetencioSri", "inicioRet", "finRet"})
    public void buscarRetencionesSRI() {
        findBetweenRetenciones();
    }

    @Command
    @NotifyChange({"listaComprasSriModel", "inicioComp", "finComp"})
    public void buscarComprasSriProcesadas() {

        findCabeceraComprasSriByBetweenFecha();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "buscar"})
    public void buscarForProveedor() {
        buscarLikeNombre();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "buscarNumFac"})
    public void buscarForNumFactura() {
        findByNumFac();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "inicio", "fin"})
    public void buscarForFechas() {
        findByBetweenFecha();
    }

    @Command
    public void modificarFactura(@BindingParam("valor") CabeceraCompra valor) {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, CabeceraCompra> map = new HashMap<String, CabeceraCompra>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/compra/modificarcompra.zul", null, map);
            window.doModal();
//            }
//            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public List<CabeceraCompra> getListaCabeceraCompras() {
        return listaCabeceraCompras;
    }

    public void setListaCabeceraCompras(List<CabeceraCompra> listaCabeceraCompras) {
        this.listaCabeceraCompras = listaCabeceraCompras;
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

    public String getBuscarNumFac() {
        return buscarNumFac;
    }

    public void setBuscarNumFac(String buscarNumFac) {
        this.buscarNumFac = buscarNumFac;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
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
        List<DetalleCompraSri> descargar = servicioDetalleComprasSri.findByBetweenFechaSRI(inicio, fin);
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
            ch7.setCellValue(new HSSFRichTextString("SUBTOTAL"));
            ch7.setCellStyle(estiloCelda);

            HSSFCell ch8 = r.createCell(j++);
            ch8.setCellValue(new HSSFRichTextString("TOTAL"));
            ch8.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (DetalleCompraSri item : descargar) {
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
                c7.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getIprodSubtotal(), 2).toString()));

                HSSFCell c8 = r.createCell(i++);
                c8.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getIprodTotal(), 2).toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= descargar.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "listaCabeceraCompraSris", "inicio", "fin", "listaComprasSriModel", "listaRetencioSri"})
    public void cargarComprasSRI()
                throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tipoambiente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String folderDescargados = PATH_BASE + File.separator + SRIRETENCION
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();
            String pathArchivoXML = "";

            File folderGen = new File(folderDescargados);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");

            File f = null;
            /*TRAEMOS EL DOCUMENTO DEL SRI*/
            for (ComprasSri noVerific : listaComprasSris) {
                // if (servicioCompra.findByAutorizacion(noVerific.getCsriAutorizacion()) == null) {

                RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(noVerific.getCsriAutorizacion());
                for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                    FileOutputStream nuevo = null;
                    pathArchivoXML = folderDescargados + File.separator + autorizacion.getNumeroAutorizacion() + ".xml";
//                CREA EL ARCHIVO XML AUTORIZADO

                    nuevo = new FileOutputStream(pathArchivoXML);
                    nuevo.write(autorizacion.getComprobante().getBytes());
                    System.out.println("autorizacion.getComprobante() " + autorizacion.getComprobante());
                    /*obtenemos el tipo de documento*/
                    f = new File(pathArchivoXML);
//                         String tipoDoc = ArchivoUtils.obtenerValorXML(f, "/*/infoTributaria/codDoc");
                    if (!autorizacion.getEstado().equals("AUTORIZADO")) {
                        System.out.println("COMPROBANTE NO AUTORIZADO");
                    }
                    System.out.println("noVerific.getCsriComprobante() " + noVerific.getCsriComprobante());
                    //para el caso que sea factura
                    if (noVerific.getCsriComprobante().trim().contains("Retenci")) {
                        System.out.println("RETENCION ");

//                        ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion adto
//                                    = ec.gob.sri.comprobantes.util.xml.XML2Java.unmarshalComprobanteRetencion(pathArchivoXML);
                        procesarXMLRetencionSRI(f, autorizacion.getComprobante() != null ? autorizacion.getComprobante().contains("version=\"2.0.0\"") ? "2" : "1" : "0");
                    }

                }
                findBetweenRetenciones();
                //}
            }
            findCabeceraComprasSriByBetweenFecha();
        } catch (Exception ex) {
            Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void procesaRetenciones(ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion dao, String xml) {
        retencionSri.eliminarbyClaveAcceso(dao.getInfoTributaria().getClaveAcceso());
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");
        RetencionCompraSri retencionCompraSri = new RetencionCompraSri();
        retencionCompraSri.setRcoRuc(dao.getInfoTributaria().getRuc());
        retencionCompraSri.setRcoNombre(dao.getInfoTributaria().getRazonSocial());
        retencionCompraSri.setRcoAutorizacion(dao.getInfoTributaria().getClaveAcceso());
        retencionCompraSri.setRcoDetalle("RETENCION OBTENIDA DESDE EL SRI");
        Date dt = null;
        try {
            dt = sm.parse(dao.getInfoCompRetencion().getFechaEmision());
        } catch (java.text.ParseException e) {
            System.out.println("ERROR FECHA " + e.getMessage());
        }

        /*total retenido*/
        BigDecimal valorRetenido = BigDecimal.ZERO;
        for (ec.gob.sri.comprobantes.modelo.rentencion.Impuesto item : dao.getImpuestos().getImpuesto()) {

            valorRetenido = valorRetenido.add(item.getValorRetenido());

        }
        retencionCompraSri.setRcoBaseGravaIva(valorRetenido);
        retencionCompraSri.setRcoFecha(dt);
        retencionCompraSri.setCabFechaEmision(dt);
        retencionCompraSri.setRcoIva(Boolean.FALSE);
        retencionCompraSri.setRcoPorcentajeIva(12);
        retencionCompraSri.setRcoPuntoEmision(dao.getInfoTributaria().getPtoEmi());
        retencionCompraSri.setRcoSecuencialText(dao.getInfoTributaria().getSecuencial());
        retencionCompraSri.setRcoSerie("1");
        retencionCompraSri.setRcoValorRetencionIva(0);
        retencionCompraSri.setDrcEstadosri("AUTORIZADO");
        retencionCompraSri.setRcoPorcentajeIva(12);
//        retencionCompraSri.setRcoNumFactura(12);
        retencionSri.crear(retencionCompraSri);
        DetalleRetencionCompraSri detalle = null;
        for (ec.gob.sri.comprobantes.modelo.rentencion.Impuesto item : dao.getImpuestos().getImpuesto()) {
            detalle = new DetalleRetencionCompraSri();
            detalle.setDrcBaseImponible(item.getBaseImponible().doubleValue());
            detalle.setDrcPorcentaje(item.getPorcentajeRetener().doubleValue());
            detalle.setDrcValorRetenido(item.getValorRetenido().doubleValue());
            detalle.setRcoCodigo(retencionCompraSri);
            detalle.setDrcCodImpuestoAsignado(item.getCodigo());
            detalle.setDrcDescripcion(item.getCodigo().equals("2") ? "IVA" : "RENTA");
            detalleRetencionSri.crear(detalle);
        }

    }

    private void procesaFactura(ec.gob.sri.comprobantes.modelo.factura.Factura adto, String xml) {
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");
        /*PARA UN PROVEEDOR EN ESPECIFICO*/
//                        if (adto.getInfoTributaria().getRuc().equals("1719631549001")) {
        servicioCabeceraComprasri.eliminarbyClaveAcceso(adto.getInfoTributaria().getClaveAcceso());

        CabeceraCompraSri cabeceraCompra;

        cabeceraCompra = new CabeceraCompraSri();
        cabeceraCompra.setCabAutorizacion(adto.getInfoTributaria().getClaveAcceso());
        cabeceraCompra.setCabClaveAcceso(adto.getInfoTributaria().getClaveAcceso());
        cabeceraCompra.setCabDescripcion("SRI");
        cabeceraCompra.setCabEstado("PA");
        cabeceraCompra.setCabXmlSri(xml);
        cabeceraCompra.setCabCasillero("500");
        cabeceraCompra.setCodTipoambiente(amb);
        cabeceraCompra.setCabTipo("COM");
        /*formato de la fecha*/
        try {
            Date dt = sm.parse(adto.getInfoFactura().getFechaEmision());
            cabeceraCompra.setCabFecha(dt);
            cabeceraCompra.setCabFechaEmision(dt);
        } catch (java.text.ParseException e) {
            System.out.println("ERROR FECHA " + e.getMessage());
        }

        cabeceraCompra.setCabHomologado("N");
        BigDecimal baseCero = BigDecimal.ZERO;
        BigDecimal baseGrabada = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto : adto.getInfoFactura().getTotalConImpuestos().getTotalImpuesto()) {
            if (totalImpuesto.getCodigoPorcentaje().equals("0")) {
                baseCero = baseCero.add(totalImpuesto.getBaseImponible());
            } else if (totalImpuesto.getCodigoPorcentaje().equals("2")) {
                baseGrabada = baseGrabada.add(totalImpuesto.getBaseImponible());
                iva = iva.add(totalImpuesto.getValor());
            }
        }
        cabeceraCompra.setCabIva(iva);
        cabeceraCompra.setCabNumFactura(adto.getInfoTributaria().getSecuencial());
        cabeceraCompra.setCabProveedor(adto.getInfoTributaria().getRazonSocial());
        cabeceraCompra.setCabRetencionAutori("N");
        cabeceraCompra.setCabSubTotal(baseGrabada);
        cabeceraCompra.setCabTotal(baseCero.add(baseGrabada).add(iva));
        cabeceraCompra.setCabTraeSri(Boolean.TRUE);
        cabeceraCompra.setCabRucProveedor(adto.getInfoTributaria().getRuc());
        cabeceraCompra.setDrcCodigoSustento(adto.getInfoTributaria().getCodDoc());
        cabeceraCompra.setIdEstado(servicioEstadoFactura.findByEstCodigo("PA"));
        cabeceraCompra.setIdUsuario(credential.getUsuarioSistema());
        cabeceraCompra.setCategoriaFactura("Inventario");
        cabeceraCompra.setCabSubTotalCero(baseCero);
        cabeceraCompra.setCabCorreo(buscar);
        cabeceraCompra.setCabDireccion(adto.getInfoTributaria().getDirMatriz());
        cabeceraCompra.setCabEstablecimiento(adto.getInfoTributaria().getEstab());
        cabeceraCompra.setCabPuntoEmision(adto.getInfoTributaria().getPtoEmi());

        /*VERIFICAMOS EL PROVEEDOR Y SI NO LO CREAMOS*/
//        Proveedores prov = servicioProveedor.findProvCedula(adto.getInfoTributaria().getRuc());
//        if (prov != null) {
//            cabeceraCompra.setIdProveedor(prov);
//        } else {
//            Proveedores provNuevo = new Proveedores();
//            //DEPENDE DEL PROVEEDOR SI ES CEDULA O RUC
//            TipoIdentificacionCompra identificacionCompra = null;
//            if (adto.getInfoTributaria().getRuc().length() == 13) {
//                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("04");
//            } else if (adto.getInfoTributaria().getRuc().length() == 10) {
//                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("05");
//            }
//            provNuevo.setIdTipoIdentificacionCompra(identificacionCompra);
//            provNuevo.setProvBanco("S/N");
//            provNuevo.setProvNombre(adto.getInfoTributaria().getRazonSocial());
//            provNuevo.setProvNomComercial(adto.getInfoTributaria().getNombreComercial());
//            provNuevo.setProvCedula(adto.getInfoTributaria().getRuc());
//            provNuevo.setProvDireccion(adto.getInfoTributaria().getDirMatriz());
//            servicioProveedor.crear(provNuevo);
//            cabeceraCompra.setIdProveedor(provNuevo);
//            prov = provNuevo;
//        }
        servicioCabeceraComprasri.crear(cabeceraCompra);

        DetalleCompraSri detalleCom = null;
        BigDecimal factorIva = BigDecimal.ONE.add(parametrizar.getParIvaActual().divide(BigDecimal.valueOf(100)));
        BigDecimal factorUtilidad = BigDecimal.ONE.add(BigDecimal.valueOf(0.47));
//                                CabeceraCompra_.cabEstado
        for (ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle detalle : adto.getDetalles().getDetalle()) {
            // detalleCom = new DetalleCompra();
            Producto buscado = servicioProducto.findByProdCodigo(detalle.getCodigoPrincipal());
            Producto nuevoProd = new Producto();
            detalleCom = new DetalleCompraSri();

            detalleCom.setIdCabeceraSri(cabeceraCompra);
            detalleCom.setIprodCantidad(detalle.getCantidad());
            detalleCom.setIprodDescripcion(detalle.getDescripcion());
            Boolean grabaIva = Boolean.TRUE;
            for (ec.gob.sri.comprobantes.modelo.factura.Impuesto impuesto : detalle.getImpuestos().getImpuesto()) {
                if (impuesto.getCodigo().equals("2")) {
                    if (impuesto.getCodigoPorcentaje().equals("0")) {
                        grabaIva = Boolean.FALSE;
                    } else if (impuesto.getCodigoPorcentaje().equals("2")) {
                        grabaIva = Boolean.TRUE;

                    }
                }
            }
            detalleCom.setIprodGrabaIva(grabaIva);
            detalleCom.setIprodSubtotal(detalle.getPrecioUnitario());
            detalleCom.setIprodTotal(detalle.getCantidad().multiply(detalle.getPrecioUnitario()));
//            detalleCom.setIprodGrabaIva(detalle.getImpuestos().getImpuesto().get(0).getCodigo().equals("2") ? Boolean.TRUE : Boolean.FALSE);
            detalleCom.setIprodCodigoProducto(detalle.getCodigoPrincipal());
            detalleCom.setIprodClasificacion("N");
            servicioDetalleComprasSri.crear(detalleCom);
            System.out.println("DETALLE " + detalle.getCantidad()
                        + " " + detalle.getCodigoPrincipal()
                        + " " + detalle.getDescripcion()
                        + " " + detalle.getPrecioUnitario()
                        + " " + detalle.getPrecioTotalSinImpuesto());
        }

        findCabeceraComprasSriByBetweenFecha();
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin"})
    public void actualizar(@BindingParam("valor") CabeceraCompraSri valor) {
        servicioCabeceraComprasri.modificar(valor);
        Clients.showNotification("Registro actualizado",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);
    }

    @Listen("onUpload = #upload")
    public void onUpload(UploadEvent event) {
        try {
            System.out.println("before upload " + event.getMedias()[0].getName());
            Media[] archivos = event.getMedias();
            for (Media archivo : archivos) {
                System.out.println("media " + archivo.getName());
                Files.copy(new File(PATH_BASE + File.separator + SRIRETENCION + File.separator + archivo.getName()),
                            new ByteArrayInputStream(archivo.getStringData().getBytes()));
            } //T
        } catch (Exception e) {
            e.printStackTrace();
            Messagebox.show("Upload failed");
        }
    }

    @Listen("onUpload = #uploadFacturas")
    public void onUploadFacturas(UploadEvent event) {
        try {
            System.out.println("before upload " + event.getMedias()[0].getName());
            Media[] archivos = event.getMedias();
            for (Media archivo : archivos) {
                System.out.println("media " + archivo.getName());
                Files.copy(new File(PATH_BASE + File.separator + SRIFACCOMPRAS + File.separator + archivo.getName()),
                            new ByteArrayInputStream(archivo.getStringData().getBytes()));
            } //T
        } catch (Exception e) {
            e.printStackTrace();
            Messagebox.show("Upload failed");
        }
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin"})
    public void subirArchivo() {

        try {
            System.out.println("");

            String folderDescargados = PATH_BASE + File.separator + "RETENCIONESDESCARGADASTXT"
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();

            /*EN EL CASO DE NO EXISTIR LOS DIRECTORIOS LOS CREA*/
            File folderGen = new File(folderDescargados);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            org.zkoss.util.media.Media media = Fileupload.get("Cargar su archivo que obtuvo en el SRI", "Subir Archivo SRI");

            if (media != null) {
                if (media.getName().contains(".txt")) {
                    String builder = media.getStringData();

                    String[] campos = builder.split("\t");
                    String[] campos1 = builder.split("\t|\n");
                    String datosFormateados = "";
                    for (int i = 0; i < campos1.length; i++) {
                        if (!campos1.equals("")) {
                            datosFormateados = datosFormateados + campos1[i] + "\t";
                        }

                    }
                    String[] campos2 = datosFormateados.split("\t");
                    String[] campos3 = new String[campos2.length];
                    int cantidadBlancos = 0;
                    int contador = 0;
                    for (int i = 0; i < campos2.length; i++) {
                        if (!campos2[i].equals("")) {
                            campos3[contador] = campos2[i];
                            contador++;
                        } else {
                            cantidadBlancos++;
                        }
                    }

                    ComprasSri comprasSri;
                    SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyy");
                    // myDate is the java.util.Date in yyyy-mm-dd format
                    // Converting it into String using formatter
                    //String strDate = sm.format(inicio);
                    //Converting the String back to java.util.Date
                    //    Date dt = sm.parse(strDate);
                    String existentes = "";
                    FileWriter flwriter = null;
                    String pathTXT = folderDescargados + File.separator + "repetidos.txt";
                    File descargar = new File(pathTXT);
                    if (!descargar.exists()) {
                        descargar.createNewFile();
                    }
                    flwriter = new FileWriter(pathTXT);
                    BufferedWriter bfwriter = new BufferedWriter(flwriter);
                    Boolean existenRepetido = Boolean.FALSE;
                    for (int i = 12; i <= campos2.length - (11); i++) {

                        comprasSri = new ComprasSri(
                                    campos2[i],
                                    campos2[++i],
                                    campos2[++i],
                                    AutorizarDocumentos.removeCaracteres(campos2[++i]),
                                    sm.parse(campos2[++i].trim().replace("/", "-")),
                                    sm.parse(campos2[++i].trim().replace("/", "-")),
                                    campos2[++i],
                                    campos2[++i],
                                    campos2[++i],
                                    campos2[++i],
                                    campos2[++i],
                                    "N");
                        ++i;

                        comprasSri.setCsriVerificado("N");
                        System.out.println("iiiii " + i);
                        comprasSri.setCsriTipoFactura("RET");

                        System.out.println("ComprasSri " + comprasSri.toString());
                        if (servicioComprasSri.findByAutorizacion(comprasSri.getCsriAutorizacion(), amb) == null) {

                            if (comprasSri.getCsriComprobante().contains("Retenc")) {
                                comprasSri.setCodTipoambiente(amb);
                                servicioComprasSri.crear(comprasSri);
                            }
                        } else {
                            existenRepetido = Boolean.TRUE;
                            existentes = existentes + ";" + comprasSri.getCsriAutorizacion();

                        }

                    }
                    if (existenRepetido) {
                        bfwriter.write(existentes);
                        FileInputStream inputStream = new FileInputStream(descargar);
                        Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(descargar), descargar.getName());
                    }
                    bfwriter.close();
                    Clients.showNotification("Informacion cargada", "info", null, "end_before", 1000, true);
                    findComprasSriByBetweenFecha();
                }
            }

        } catch (IOException e) {
            System.out.println("ERROR al subir la imagen IOException " + e.getMessage());
        } catch (java.text.ParseException e) {
            System.out.println("ERROR al subir la imagen IOException " + e.getMessage());
        }
    }

    /*SUBIR ARCHIVO EXCEL*/
    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void cargarExcelVentas() {

        try {
            org.zkoss.util.media.Media media = Fileupload.get();
            if (media instanceof org.zkoss.util.media.AMedia) {
                String nombre = media.getName();

                if (!nombre.contains("xls")) {
                    Clients.showNotification("Su documento debe ser un archivo excel",
                                Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);

                    return;
                }

                System.out.println("media " + nombre);
                Files.copy(new File(PATH_BASE + File.separator + "CARGAR" + File.separator + nombre),
                            new ByteArrayInputStream(media.getByteData()));

                String rutaArchivo = PATH_BASE + File.separator + "CARGAR" + File.separator + nombre;

                InputStream myFile = new FileInputStream(new File(rutaArchivo));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);

                HSSFCell cell;
                HSSFRow row;

                System.out.println("Apunto de entrar a loops");

                System.out.println("" + sheet.getLastRowNum());
                Producto prod = new Producto();
                for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i);
//                    for (int j = 0; j < row.getLastCellNum(); j++) {
                    for (int j = 0; j < 6; j++) {

                        /**
                         * campo del excel
                         */
//                        if (servicioComprasSri.findByAutorizacion(comprasSri.getCsriAutorizacion(), amb) == null) {
                        cell = row.getCell(j);
//                            prod = new Producto();
//                            prod.setProdCodigo(String.valueOf(row.getCell(0)));
//                            prod.setProdNombre(String.valueOf(row.getCell(1)));
//                            prod.setPordCostoVentaRef(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2)))));
//                            prod.setPordCostoVentaFinal(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(3)))));
//                            prod.setProdCostoPreferencial(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(4)))));
//                            prod.setProdCostoPreferencialDos(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(5)))));
//                            prod.setProdCostoPreferencialTres(BigDecimal.ZERO);
//                            prod.setCodTipoambiente(amb);
//                            prod.setProdCantMinima(BigDecimal.ONE);
//                            prod.setProdFechaRegistro(new Date());

                        if (row.getCell(6) != null) {
                            String valor = String.valueOf(row.getCell(6));
                            prod.setProdGrabaIva(String.valueOf(row.getCell(6)).contains("1") ? Boolean.TRUE : Boolean.FALSE);

                            if (prod.getProdGrabaIva()) {
                                BigDecimal precioIva = BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2))));
                                BigDecimal precioCompra = precioIva.divide(BigDecimal.valueOf(1.12), 4, RoundingMode.FLOOR);
                                prod.setPordCostoCompra(precioCompra);
                            } else {
                                prod.setPordCostoCompra(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2)))));
                            }

                        } else {
                            prod.setProdGrabaIva(Boolean.FALSE);
                            prod.setPordCostoCompra(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2)))));
                        }
                        prod.setProdCantidadInicial(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(6)))));
                        servicioProducto.crear(prod);
                        System.out.println("Valor: " + cell.toString());
//                        } else {
//                            System.out.println("El producto existe " + String.valueOf(row.getCell(1)));
//                        }

                    }
                }
                System.out.println("Finalizado");

                servicioGeneral.corregirProductos();

                Clients.showNotification("Productos cargados correctamente",
                            Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Verifique le archivo para cargar",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
            e.printStackTrace();
//            Messagebox.show("Upload failed");
        }

    }

    @Command
    public void descargarPlantilla() throws Exception {
        try {
            String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");
            String pathSalida = directorioReportes + File.separator + "ventas_pl.xls";
            File dosfile = new File(pathSalida);
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    public List<ComprasSri> getListaComprasSris() {
        return listaComprasSris;
    }

    public void setListaComprasSris(List<ComprasSri> listaComprasSris) {
        this.listaComprasSris = listaComprasSris;
    }

    public List<CabeceraCompraSri> getListaCabeceraCompraSris() {
        return listaCabeceraCompraSris;
    }

    public void setListaCabeceraCompraSris(List<CabeceraCompraSri> listaCabeceraCompraSris) {
        this.listaCabeceraCompraSris = listaCabeceraCompraSris;
    }

    public Date getInicioComp() {
        return inicioComp;
    }

    public void setInicioComp(Date inicioComp) {
        this.inicioComp = inicioComp;
    }

    public Date getFinComp() {
        return finComp;
    }

    public void setFinComp(Date finComp) {
        this.finComp = finComp;
    }

    @Command
    public void reporteFacturaCompra(@BindingParam("valor") CabeceraCompraSri valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getIdCabeceraSri(), "FACT");
    }

    @Command
    public void imprimirRetencion(@BindingParam("valor") RetencionCompraSri valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getRcoCodigo(), "RET");
    }

    public void reporteGeneral(Integer idCabera, String tipo) throws JRException, IOException, NamingException, SQLException {
        EntityManager emf = HelperPersistencia.getEMF();
        try {

            emf.getTransaction().begin();
            con
                        = emf.unwrap(Connection.class
                        );
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
            String reportPath = "";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            if (tipo.equals("RET")) {
                reportPath = reportFile + File.separator + "retencionsri.jasper";
                parametros.put("numfactura", idCabera);
            } else if (tipo.equals("FACT")) {
                reportPath = reportFile + File.separator + "facturacomprasri.jasper";
                parametros.put("id_cabecera", idCabera);
            }

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/venta/contenedorReporte.zul", null, map);
            window.doModal();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException " + e.getMessage());
        } catch (JRException e) {
            System.out.println("JRException " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    public List<RetencionCompraSri> getListaRetencioSri() {
        return listaRetencioSri;
    }

    public void setListaRetencioSri(List<RetencionCompraSri> listaRetencioSri) {
        this.listaRetencioSri = listaRetencioSri;
    }

    public Date getInicioRet() {
        return inicioRet;
    }

    public void setInicioRet(Date inicioRet) {
        this.inicioRet = inicioRet;
    }

    public Date getFinRet() {
        return finRet;
    }

    public void setFinRet(Date finRet) {
        this.finRet = finRet;
    }

    /*EXPOSTRTAR RETENCIONES
     */
    @Command
    public void exportarRetenciones() throws Exception {
        try {
            File dosfile = new File(exportarRetencionesExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarRetencionesExcel() throws FileNotFoundException, IOException, ParseException {
        List<DetalleRetencionCompraSri> descargar = detalleRetencionSri.findBetweenDetalle(inicio, fin);
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "retencionessri.xls";
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
            ch2.setCellValue(new HSSFRichTextString("COMPROBANTE"));
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
            ch4.setCellValue(new HSSFRichTextString("BASE IMPONIBLE"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("% RETENCION"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("VALOR RETENIDO"));
            ch6.setCellStyle(estiloCelda);

            HSSFCell ch7 = r.createCell(j++);
            ch7.setCellValue(new HSSFRichTextString("TIPO RETENCION"));
            ch7.setCellStyle(estiloCelda);

            HSSFCell ch8 = r.createCell(j++);
            ch8.setCellValue(new HSSFRichTextString("# FACTURA"));
            ch8.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (DetalleRetencionCompraSri item : descargar) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getRcoCodigo().getRcoSecuencialText()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getRcoCodigo().getRcoRuc()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getRcoCodigo().getRcoNombre()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getRcoCodigo().getCabFechaEmision())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(BigDecimal.valueOf(item.getDrcBaseImponible()), 2).toString().replace(".", ",")));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(BigDecimal.valueOf(item.getDrcPorcentaje()), 2).toString().replace(".", ",")));

                HSSFCell c6 = r.createCell(i++);
                c6.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(BigDecimal.valueOf(item.getDrcValorRetenido()), 2).toString().replace(".", ",")));
                HSSFCell c7 = r.createCell(i++);
                c7.setCellValue(new HSSFRichTextString(item.getDrcDescripcion()));
                HSSFCell c8 = r.createCell(i++);
                c8.setCellValue(new HSSFRichTextString(item.getDrcNumFactura()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= descargar.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    @Command
    public void exportarDocumentosSRI() throws Exception {
        try {
            File dosfile = new File(exportarDocumentosSriExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarDocumentosSriExcel() throws FileNotFoundException, IOException, ParseException {
//        List<ComprasSri> descargar = listaComprasSris.findBetweenDetalle(inicioRet, finRet);
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "retencionessri.xls";
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
            ch2.setCellValue(new HSSFRichTextString("COMPROBANTE"));
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
            ch4.setCellValue(new HSSFRichTextString("FECHA AUTORIZACION"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("TOTAL"));
            ch5.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (ComprasSri item : listaComprasSris) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getCsriComprobante()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getCsriRucEmisor()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCsriRazonSocial()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getCsriFechaEmision())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(sm.format(item.getCsriFechaAutorizacion())));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(BigDecimal.valueOf(Double.valueOf(item.getCsriTotal())), 2).toString()));

                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaComprasSris.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    //PROCESAR XML
    @Command
    public void procesarXMLRetencion() {
        String pathDoc = PATH_BASE + File.separator + SRIRETENCION;
        String pathArchivoXML = "";
        File folder = new File(pathDoc);
        File[] listaArvhivos = folder.listFiles();
        System.out.println("NUMERO ARCHIVOS " + listaArvhivos.length);
        for (File file : listaArvhivos) {

            try {

                String xmlParse = leerArchivo(file, "");
                System.out.println(xmlParse);
//                XmlMapper xmlMapper = new XmlMapper();
//                
//                RetencionXML value
//                        = xmlMapper.readValue(xmlParse, RetencionXML.class);
                JAXBContext jaxbContext;

                try {
                    jaxbContext = JAXBContext.newInstance(RetencionXML.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    RetencionXML retenciones = (RetencionXML) jaxbUnmarshaller.unmarshal(new StringReader(xmlParse));

//                    System.out.println(retenciones);
//                    procesaRetencionesXML(retenciones);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

                // Capturo el nombre del fichero antiguo
                try {
                    // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                    if (file.exists()) {
//                        System.gc();//Added this part
//                        Thread.sleep(2000);//This part gives the Bufferedreaders and the InputStreams time to close Completely
                        FileDeleteStrategy.FORCE.delete(file);
                    }

//                    Files.move(fNuevo.toPath(), fAntiguo.toPath());
                } catch (Exception e) {
                    System.out.println(e);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private String leerArchivo(File archivo, String version) {
        FileReader fr = null;
        BufferedReader br = null;
        try {

            return modificar(archivo, version);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }

    void Escribir(File fFichero, String cadena) {
        // Declaramos un buffer de escritura
        BufferedWriter bw;

        try {
            // Comprobamos si el archivo no existe y si es asi creamos uno nuevo.
            if (!fFichero.exists()) {
                fFichero.createNewFile();
            }

            // Luego de haber creado el archivo procedemos a escribir dentro de el.
            bw = new BufferedWriter(new FileWriter(fFichero, true));
            bw.write(cadena);
            bw.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * *************************************************************
     * El metodo "Borrar" como su nombre lo indica, nos ayuda a borrar un
     * fichero previamente creado, este metodo cuenta con un parametro, el cual
     * es el nombre del fichero que deseamos borrar
     * **************************************************************
     */
    void borrar(File Ffichero) {
        try {
            // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
            if (Ffichero.exists()) {
                Ffichero.delete();
                System.out.println("Ficherro Borrado");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * *************************************************************
     * Metodo "Modificar", este cuenta con tres parametros que debemos pasarle
     * para su correcto funcionamiento, los cuales son:
     *
     * fAntiguo: Este nos ayuda a saber cual es y donde esta el archivo que
     * deseamos modificar
     *
     * aCadena: Aqui debemos especificar la cadena de caracteres que deseamos
     * modificar o cambiar
     *
     * nCadena: Por ultimo escribimos el nuevo dato que va a sustituir el
     * existente.
     * *****************************************************************
     */
    String modificar(File fAntiguo, String version) {
        /*
            Las dos lienas de codigo siguientes, basicamente lo que hacen es generar un numero aleatorio y
            asignarselos a una nueva variable "nFnuevo" (Nombre Fichero Nuevo) la cual es igual a la ruta
            del directorio padre "fAntiguo" mas  la palabra auxiliar seguido del numero aletorio y la extension
            del archivo nuevo
       * */
        String xml = "";
        Random numaleatorio = new Random(3816L);
        String nFnuevo = fAntiguo.getParent() + File.separatorChar + "auxiliar" + String.valueOf(Math.abs(numaleatorio.nextInt())) + ".xml";

        // Creo un nuevo archivo
//        File fNuevo = new File(nFnuevo);
        // Declaro un nuevo buffer de lectura
        BufferedReader br;
        try {
            /*Valido si el fichero antiguo que pasamos como parametro existe, si es asi procedo a leer el
            contenido que hay dentro de el
             */

            if (fAntiguo.exists()) {
                br = new BufferedReader(new FileReader(fAntiguo));

                String linea;

                String salida = "</impuestos>";
                String textoInicial = "<![CDATA[";

                if (version.equals("1")) {
                    salida = "</impuestos>";
                } else {
                    salida = "</docsSustento>";
                }

                /* Mientras el contenido del archivo sea diferente de null procedo a comprar  la linea a modificar con
                lo que hay dentro del archivo, si linea es igual a aCadena escribo el contenido de aCadena en mi nuevo
                fichero(Auxiliar) de lo contrario escribo el mismo contenido que ya tenia el antiguo fichero en mi fichero auxiliar

                 */
                while ((linea = br.readLine()) != null) {
                    System.out.println("LINEA " + linea);
                    if (linea.trim().contains("<![CDATA[")) {
//                        Escribir(fNuevo, linea.replace(textoInicial, ""));
//                        Escribir(fNuevo, "\n");
                        String eliminaInicio = linea.replace(textoInicial, "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        eliminaInicio = eliminaInicio.replace("id=\"comprobante\" version=\"2.0.0\"", "");
                        String[] plistImpuesto = eliminaInicio.split("</impuestos>");
                        if (plistImpuesto.length > 1) {
                            xml = xml + plistImpuesto[0];
                            if (version.equals("1")) {

                                xml = xml
                                            + "</impuestos> \n"
                                            + "</comprobanteRetencion> \n";
//                                            + "</comprobante> \n"
//                                            + "</autorizacion> \n";

                            } else {
                                xml = xml
                                            + "</docsSustento> \n"
                                            + "</comprobanteRetencion> \n";
                            }
                            br.close();
//                            Escribir(fNuevo, xml);
                            xml = xml + "\n";

                            try {
                                // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                                if (fAntiguo.exists()) {
//                        System.gc();//Added this part
//                        Thread.sleep(2000);//This part gives the Bufferedreaders and the InputStreams time to close Completely
                                    FileDeleteStrategy.FORCE.delete(fAntiguo);
                                }

//                    Files.move(fNuevo.toPath(), fAntiguo.toPath());
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                            //Renombro el fichero auxiliar con el nombre del fichero antiguo
//                            if (fNuevo.renameTo(fAntiguo)) {
//                                System.out.println("El fichero ha sido renombrado");
//                            } else {
//                                System.out.println("El fichero no puede ser renombrado");
//                            }
                            System.out.println("XML FORMATEADO \n"
                                        + xml);
                            return xml;

                        } else {
                            xml = xml + eliminaInicio;
                        }

                    } else if (linea.trim().contains(salida)) {
//                        Escribir(fNuevo, linea);
//                        Escribir(fNuevo, "</comprobanteRetencion>"
//                                + "</comprobante></autorizacion> \n");
                        String[] plistImpuesto = linea.split(salida);
                        if (plistImpuesto.length > 0) {
                            String eliminaInicio = plistImpuesto[0].replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                            eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                            eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                            eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                            eliminaInicio = eliminaInicio.replace("id=\"comprobante\" version=\"2.0.0\"", "");
                            xml = xml + eliminaInicio;

                            if (version.equals("1")) {

                                xml = xml
                                            + "</impuestos> \n"
                                            + "</comprobanteRetencion> \n";
//                                            + "</comprobante> \n"
//                                            + "</autorizacion> \n";

                            } else {
                                xml = xml
                                            + "</docsSustento> \n"
                                            + "</comprobanteRetencion> \n";
                            }

//                                        + "</comprobante> \n"
//                                        + "</autorizacion> \n";
                        } else {
                            xml = xml
                                        + "</impuestos> \n"
                                        + "</comprobanteRetencion> \n";
                        }

                        br.close();
//                        Escribir(fNuevo, xml);
                        // Capturo el nombre del fichero antiguo
//                        String nAntiguo = fAntiguo.getName();
                        //System.out.println(xml);
                        try {
                            // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                            if (fAntiguo.exists()) {
//                        System.gc();//Added this part
//                        Thread.sleep(2000);//This part gives the Bufferedreaders and the InputStreams time to close Completely
                                FileDeleteStrategy.FORCE.delete(fAntiguo);
                            }

//                    Files.move(fNuevo.toPath(), fAntiguo.toPath());
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        //Renombro el fichero auxiliar con el nombre del fichero antiguo
//                        if (fNuevo.renameTo(fAntiguo)) {
//                            System.out.println("El fichero ha sido renombrado");
//                        } else {
//                            System.out.println("El fichero no puede ser renombrado");
//                        }
                        return xml;
                    } else {
//                        Escribir(fNuevo, linea);
//                        Escribir(fNuevo, "\n");
//                        if (!linea.contains("encoding")) {
                        linea = linea.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                        linea = linea.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        linea = linea.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                        linea = linea.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        xml = xml + linea + " \n";
//                        }

                    }
                }

                // Cierro el buffer de lectura
                br.close();

                // Borro el fichero nuevo
                try {
                    // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
//                    if (fNuevo.exists()) {
//                        FileDeleteStrategy.FORCE.delete(fNuevo);
//                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            } else {
                System.out.println("Fichero no Existe");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return xml;
    }

    private void procesaRetencionesXML(comprobanteRetencion dao) {
        retencionSri.eliminarbyClaveAcceso(dao.getInfoTributaria().getClaveAcceso());
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");
        RetencionCompraSri retencionCompraSri = new RetencionCompraSri();
        retencionCompraSri.setRcoRuc(dao.getInfoTributaria().getRuc());
        retencionCompraSri.setRcoNombre(dao.getInfoTributaria().getRazonSocial());
        retencionCompraSri.setRcoAutorizacion(dao.getInfoTributaria().getClaveAcceso());
        retencionCompraSri.setRcoDetalle("RETENCION OBTENIDA DESDE EL SRI");
        Date dt = null;
        try {
            dt = sm.parse(dao.getInfoCompRetencion().getFechaEmision());
        } catch (java.text.ParseException e) {
            System.out.println("ERROR FECHA " + e.getMessage());
        }

        /*total retenido*/
        BigDecimal valorRetenido = BigDecimal.ZERO;
        for (retencion item : dao.getDocsSustento().getDocSustento().getRetenciones().getRetencion()) {
//
            valorRetenido = valorRetenido.add(BigDecimal.valueOf(item.getValorRetenido()));
//
        }
        retencionCompraSri.setRcoBaseGravaIva(valorRetenido);
        retencionCompraSri.setRcoFecha(dt);
        retencionCompraSri.setCabFechaEmision(dt);
        retencionCompraSri.setRcoIva(Boolean.FALSE);
        retencionCompraSri.setRcoPorcentajeIva(12);
        retencionCompraSri.setRcoPuntoEmision(dao.getInfoTributaria().getPtoEmi());
        retencionCompraSri.setRcoSecuencialText(dao.getInfoTributaria().getSecuencial());
        retencionCompraSri.setRcoSerie("1");
        retencionCompraSri.setRcoValorRetencionIva(0);
        retencionCompraSri.setDrcEstadosri("AUTORIZADO");
        retencionCompraSri.setRcoPorcentajeIva(12);
        retencionCompraSri.setCodTipoambiente(amb);

        retencionSri.crear(retencionCompraSri);
        DetalleRetencionCompraSri detalle = null;
        String numeroFactura = dao.getDocsSustento().getDocSustento().getNumDocSustento();
        for (retencion item : dao.getDocsSustento().getDocSustento().getRetenciones().getRetencion()) {
            detalle = new DetalleRetencionCompraSri();
            detalle.setDrcBaseImponible(Double.valueOf(item.getBaseImponible()));
            detalle.setDrcPorcentaje(Double.valueOf(item.getPorcentajeRetener()));
            detalle.setDrcValorRetenido(Double.valueOf(item.getValorRetenido()));
            detalle.setRcoCodigo(retencionCompraSri);
            detalle.setDrcCodImpuestoAsignado(String.valueOf(item.getCodigo()));
            detalle.setDrcDescripcion(String.valueOf(item.getCodigo()).equals("2") ? "IVA" : "RENTA");
            detalle.setDrcNumFactura(numeroFactura);
            detalleRetencionSri.crear(detalle);
        }

    }

    /*PROCESAR FACTURA DE COMPRAS*/
    //PROCESAR XML
    @Command
    public void procesarXMLFactura() {
        String pathDoc = PATH_BASE + File.separator + SRIFACCOMPRAS;
        String pathArchivoXML = "";
        File folder = new File(pathDoc);
        File[] listaArvhivos = folder.listFiles();
        System.out.println("NUMERO ARCHIVOS " + listaArvhivos.length);
        for (File file : listaArvhivos) {

            try {

                String xmlParse = leerArchivoFactura(file);
                System.out.println(xmlParse);
//                XmlMapper xmlMapper = new XmlMapper();
//                
//                RetencionXML value
//                        = xmlMapper.readValue(xmlParse, RetencionXML.class);
                JAXBContext jaxbContext;

                try {
                    jaxbContext = JAXBContext.newInstance(FacturaCompraXML.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    FacturaCompraXML factura = (FacturaCompraXML) jaxbUnmarshaller.unmarshal(new StringReader(xmlParse));

                    System.out.println(factura);
                    procesaFacturaXML(factura, xmlParse);

                } catch (JAXBException e) {
                    e.printStackTrace();
                }

                // Capturo el nombre del fichero antiguo
                try {
                    // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                    if (file.exists()) {
//                        System.gc();//Added this part
//                        Thread.sleep(2000);//This part gives the Bufferedreaders and the InputStreams time to close Completely
                        FileDeleteStrategy.FORCE.delete(file);
                    }

//                    Files.move(fNuevo.toPath(), fAntiguo.toPath());
                } catch (Exception e) {
                    System.out.println(e);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private String leerArchivoFactura(File archivo) {
        FileReader fr = null;
        BufferedReader br = null;
        try {

            return modificarFactura(archivo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }

    /**
     * *************************************************************
     * Metodo "Modificar", este cuenta con tres parametros que debemos pasarle
     * para su correcto funcionamiento, los cuales son:
     *
     * fAntiguo: Este nos ayuda a saber cual es y donde esta el archivo que
     * deseamos modificar
     *
     * aCadena: Aqui debemos especificar la cadena de caracteres que deseamos
     * modificar o cambiar
     *
     * nCadena: Por ultimo escribimos el nuevo dato que va a sustituir el
     * existente.
     * *****************************************************************
     */
    String modificarFactura(File fAntiguo) {
        /*
            Las dos lienas de codigo siguientes, basicamente lo que hacen es generar un numero aleatorio y
            asignarselos a una nueva variable "nFnuevo" (Nombre Fichero Nuevo) la cual es igual a la ruta
            del directorio padre "fAntiguo" mas  la palabra auxiliar seguido del numero aletorio y la extension
            del archivo nuevo
       * */
        String xml = "";
        Random numaleatorio = new Random(3816L);
        String nFnuevo = fAntiguo.getParent() + File.separatorChar + "auxiliar" + String.valueOf(Math.abs(numaleatorio.nextInt())) + ".txt";

        // Creo un nuevo archivo
        File fNuevo = new File(nFnuevo);
        // Declaro un nuevo buffer de lectura
        BufferedReader br;
        try {
            /*Valido si el fichero antiguo que pasamos como parametro existe, si es asi procedo a leer el
            contenido que hay dentro de el
             */

            if (fAntiguo.exists()) {
                br = new BufferedReader(new FileReader(fAntiguo));

                String linea;

                String textoInicial = "<![CDATA[";
                String salida = "</detalles>";

                /* Mientras el contenido del archivo sea diferente de null procedo a comprar  la linea a modificar con
                lo que hay dentro del archivo, si linea es igual a aCadena escribo el contenido de aCadena en mi nuevo
                fichero(Auxiliar) de lo contrario escribo el mismo contenido que ya tenia el antiguo fichero en mi fichero auxiliar

                 */
                while ((linea = br.readLine()) != null) {
                    System.out.println("LINEA " + linea);
                    if (linea.trim().contains("<![CDATA[")) {
//                        Escribir(fNuevo, linea.replace(textoInicial, ""));
//                        Escribir(fNuevo, "\n");
                        String eliminaInicio = linea.replace(textoInicial, "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        eliminaInicio = eliminaInicio.replace("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                        eliminaInicio = eliminaInicio.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
                        String[] plistImpuesto = eliminaInicio.split("</detalles>");
                        if (plistImpuesto.length > 1) {
                            xml = xml + plistImpuesto[0].replace("<infoAdicional>", "");
                            xml = xml
                                        + "</detalles> \n"
                                        + "</factura> \n"
                                        + "</comprobante> \n"
                                        + "</autorizacion> \n";
                            br.close();
                            Escribir(fNuevo, xml);
                            xml = xml + "\n";

                            try {
                                // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                                if (fAntiguo.exists()) {
//                        System.gc();//Added this part
//                        Thread.sleep(2000);//This part gives the Bufferedreaders and the InputStreams time to close Completely
                                    FileDeleteStrategy.FORCE.delete(fAntiguo);
                                }

//                    Files.move(fNuevo.toPath(), fAntiguo.toPath());
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                            //Renombro el fichero auxiliar con el nombre del fichero antiguo
                            if (fNuevo.renameTo(fAntiguo)) {
                                System.out.println("El fichero ha sido renombrado");
                            } else {
                                System.out.println("El fichero no puede ser renombrado");
                            }

                            return xml;

                        } else {
                            if (linea.contains("infoAdicional") || linea.contains("campoAdicional")) {

                            } else {

                                xml = xml + eliminaInicio + " \n";
                            }
                        }

                    } else if (linea.trim().contains(salida)) {
//                        Escribir(fNuevo, linea);
//                        Escribir(fNuevo, "</comprobanteRetencion>"
//                                + "</comprobante></autorizacion> \n");
                        String[] plistImpuesto = linea.split("</detalles>");
                        if (plistImpuesto.length > 0) {
                            String eliminaInicio = plistImpuesto[0].replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                            eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                            eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                            eliminaInicio = eliminaInicio.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                            eliminaInicio = eliminaInicio.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
//                            eliminaInicio = eliminaInicio.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
                            eliminaInicio = eliminaInicio.replace("<![CDATA[", "");

                            xml = xml + eliminaInicio.replace("<infoAdicional>", "");;
                            xml = xml
                                        + "</detalles> \n"
                                        + "</factura> \n"
                                        + "</comprobante> \n"
                                        + "</autorizacion> \n";
                        } else {
                            xml = xml
                                        + "</detalles> \n"
                                        + "</factura> \n"
                                        + "</comprobante> \n"
                                        + "</autorizacion> \n";
                        }

                        br.close();
                        Escribir(fNuevo, xml);
                        // Capturo el nombre del fichero antiguo
//                        String nAntiguo = fAntiguo.getName();
                        //System.out.println(xml);
                        try {
                            // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                            if (fAntiguo.exists()) {
//                        System.gc();//Added this part
//                        Thread.sleep(2000);//This part gives the Bufferedreaders and the InputStreams time to close Completely
                                FileDeleteStrategy.FORCE.delete(fAntiguo);
                            }

//                    Files.move(fNuevo.toPath(), fAntiguo.toPath());
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        //Renombro el fichero auxiliar con el nombre del fichero antiguo
                        if (fNuevo.renameTo(fAntiguo)) {
                            System.out.println("El fichero ha sido renombrado");
                        } else {
                            System.out.println("El fichero no puede ser renombrado");
                        }

                        return xml;
                    } else {
//                        Escribir(fNuevo, linea);
//                        Escribir(fNuevo, "\n");
                        if (!linea.contains("encoding")) {
                            linea = linea.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                            linea = linea.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                            linea = linea.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
                            linea = linea.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                            linea = linea.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
                            if (linea.contains("infoAdicional") || linea.contains("campoAdicional")) {

                            } else {
                                xml = xml + linea + " \n";
                            }
                        }

                    }
                }

                // Cierro el buffer de lectura
                br.close();

                // Borro el fichero nuevo
                try {
                    // Comprovamos si el fichero existe  de ser así procedemos a borrar el archivo
                    if (fNuevo.exists()) {
                        FileDeleteStrategy.FORCE.delete(fNuevo);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            } else {
                System.out.println("Fichero no Existe");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return xml;
    }

    private void procesaFacturaXML(FacturaCompraXML adto, String xml) {
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");
        /*PARA UN PROVEEDOR EN ESPECIFICO*/
//                        if (adto.getInfoTributaria().getRuc().equals("1719631549001")) {
        servicioCabeceraComprasri.eliminarbyClaveAcceso(adto.getComprobante().getFactura().getInfoTributaria().getClaveAcceso());

        CabeceraCompraSri cabeceraCompra;

        cabeceraCompra = new CabeceraCompraSri();
        cabeceraCompra.setCabAutorizacion(adto.getComprobante().getFactura().getInfoTributaria().getClaveAcceso());
        cabeceraCompra.setCabClaveAcceso(adto.getComprobante().getFactura().getInfoTributaria().getClaveAcceso());
        cabeceraCompra.setCabDescripcion("SRI");
        cabeceraCompra.setCabEstado("PA");
        cabeceraCompra.setCabXmlSri(xml);
        cabeceraCompra.setCabCasillero("500");
        /*formato de la fecha*/
        try {
            Date dt = sm.parse(adto.getComprobante().getFactura().getInfoFactura().getFechaEmision());
            cabeceraCompra.setCabFecha(new Date());
            cabeceraCompra.setCabFechaEmision(dt);
        } catch (java.text.ParseException e) {
            System.out.println("ERROR FECHA " + e.getMessage());
        }

        cabeceraCompra.setCabHomologado("N");
        BigDecimal baseCero = BigDecimal.ZERO;
        BigDecimal baseGrabada = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        for (TotalImpuesto totalImpuesto : adto.getComprobante().getFactura().getInfoFactura().getTotalConImpuestos().getTotalImpuesto()) {
            if (totalImpuesto.getCodigoPorcentaje().equals("0")) {
                baseCero = baseCero.add(totalImpuesto.getBaseImponible());
            } else if (totalImpuesto.getCodigoPorcentaje().equals("2")) {
                baseGrabada = baseGrabada.add(totalImpuesto.getBaseImponible());
                iva = iva.add(totalImpuesto.getValor());
            }
        }
        cabeceraCompra.setCabIva(iva);
        cabeceraCompra.setCabNumFactura(adto.getComprobante().getFactura().getInfoTributaria().getSecuencial());
        cabeceraCompra.setCabProveedor(adto.getComprobante().getFactura().getInfoTributaria().getRazonSocial());
        cabeceraCompra.setCabRetencionAutori("N");
        cabeceraCompra.setCabSubTotal(baseGrabada);
        cabeceraCompra.setCabTotal(baseGrabada.add(baseCero).add(iva));
        cabeceraCompra.setCabTraeSri(Boolean.TRUE);
        cabeceraCompra.setCabRucProveedor(adto.getComprobante().getFactura().getInfoTributaria().getRuc());
        cabeceraCompra.setDrcCodigoSustento(adto.getComprobante().getFactura().getInfoTributaria().getCodDoc());
        cabeceraCompra.setIdEstado(servicioEstadoFactura.findByEstCodigo("PA"));
        cabeceraCompra.setIdUsuario(credential.getUsuarioSistema());
        cabeceraCompra.setCategoriaFactura("Inventario");
        cabeceraCompra.setCabSubTotalCero(baseCero);
        /*VERIFICAMOS EL PROVEEDOR Y SI NO LO CREAMOS*/
//        Proveedores prov = servicioProveedor.findProvCedula(adto.getComprobante().getFactura().getInfoTributaria().getRuc());
//        if (prov != null) {
//            cabeceraCompra.setIdProveedor(prov);
//        } else {
//            Proveedores provNuevo = new Proveedores();
//            //DEPENDE DEL PROVEEDOR SI ES CEDULA O RUC
//            TipoIdentificacionCompra identificacionCompra = null;
//            if (adto.getComprobante().getFactura().getInfoTributaria().getRuc().length() == 13) {
//                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("04");
//            } else if (adto.getComprobante().getFactura().getInfoTributaria().getRuc().length() == 10) {
//                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("05");
//            }
//            provNuevo.setIdTipoIdentificacionCompra(identificacionCompra);
//            provNuevo.setProvBanco("S/N");
//            provNuevo.setProvNombre(adto.getComprobante().getFactura().getInfoTributaria().getRazonSocial());
//            provNuevo.setProvNomComercial(adto.getComprobante().getFactura().getInfoTributaria().getNombreComercial());
//            provNuevo.setProvCedula(adto.getComprobante().getFactura().getInfoTributaria().getRuc());
//            provNuevo.setProvDireccion(adto.getComprobante().getFactura().getInfoTributaria().getDirMatriz());
//            servicioProveedor.crear(provNuevo);
//            cabeceraCompra.setIdProveedor(provNuevo);
//            prov = provNuevo;
//        }

        servicioCabeceraComprasri.crear(cabeceraCompra);

        DetalleCompraSri detalleCom = null;
        BigDecimal factorIva = BigDecimal.ONE.add(parametrizar.getParIvaActual().divide(BigDecimal.valueOf(100)));
        BigDecimal factorUtilidad = BigDecimal.ONE.add(BigDecimal.valueOf(0.47));
//                                CabeceraCompra_.cabEstado
        for (Detalle detalle : adto.getComprobante().getFactura().getDetalles().getDetalle()) {
            // detalleCom = new DetalleCompra();
//            Producto buscado = servicioProducto.findByProdCodigo(detalle.getCodigoPrincipal());
//            Producto nuevoProd = new Producto();
//            detalleCom = new DetalleCompraSri();
//            if (buscado == null && prov != null) {
//                BigDecimal costoInicial = detalle.getPrecioUnitario().setScale(2, RoundingMode.FLOOR);
//                BigDecimal calCostoCompr = (costoInicial.divide(factorIva, 3, RoundingMode.FLOOR));
//                System.out.println("PRODUCTO NUEVO " + detalle.getDescripcion());
//                nuevoProd = new Producto();
//                nuevoProd.setPordCostoCompra(calCostoCompr);
//                nuevoProd.setPordCostoVentaFinal(detalle.getPrecioUnitario().multiply(factorUtilidad).setScale(4, RoundingMode.CEILING));
//                nuevoProd.setPordCostoVentaRef(detalle.getPrecioUnitario().setScale(4, RoundingMode.CEILING));
//                nuevoProd.setProdAbreviado("");
//                nuevoProd.setProdCantMinima(BigDecimal.TEN);
//                nuevoProd.setProdCantidadInicial(detalle.getCantidad());
//                nuevoProd.setProdCodigo(detalle.getCodigoPrincipal().length() > 199 ? detalle.getCodigoPrincipal().substring(0, 199) : detalle.getCodigoPrincipal());
//                nuevoProd.setProdCostoPreferencial(BigDecimal.ZERO);
//                nuevoProd.setProdCostoPreferencialDos(BigDecimal.ZERO);
//                nuevoProd.setProdCostoPreferencialTres(BigDecimal.ZERO);
//                nuevoProd.setProdIsPrincipal(Boolean.FALSE);
//                nuevoProd.setProdIva(parametrizar.getParIvaActual());
//                nuevoProd.setProdManoObra(BigDecimal.ZERO);
//                nuevoProd.setProdNombre(detalle.getDescripcion().length() > 199 ? detalle.getDescripcion().substring(0, 199) : detalle.getDescripcion());
//                nuevoProd.setProdTrasnporte(BigDecimal.ZERO);
//                nuevoProd.setProdUtilidadNormal(BigDecimal.ZERO);
//                nuevoProd.setProdUtilidadPreferencial(BigDecimal.ZERO);
//                servicioProducto.crear(nuevoProd);
//                detalleCom.setIdProducto(nuevoProd);
//            }
//            
//            if (buscado != null) {
//                detalleCom.setIdProducto(buscado);
//            }
            // detalleCom.setIdProducto(buscado);
            detalleCom.setIdCabeceraSri(cabeceraCompra);
            detalleCom.setIprodCantidad(detalle.getCantidad());
            detalleCom.setIprodDescripcion(detalle.getDescripcion());
            detalleCom.setIprodSubtotal(detalle.getPrecioUnitario());
            detalleCom.setIprodTotal(detalle.getCantidad().multiply(detalle.getPrecioUnitario()));

            servicioDetalleComprasSri.crear(detalleCom);
            System.out.println("DETALLE " + detalle.getCantidad()
                        + " " + detalle.getCodigoPrincipal()
                        + " " + detalle.getDescripcion()
                        + " " + detalle.getPrecioUnitario()
                        + " " + detalle.getPrecioTotalSinImpuesto());
        }

        findCabeceraComprasSriByBetweenFecha();
    }

    @Command
    public void exportListboxToExcelCabe() throws Exception {
        try {
            File dosfile = new File(exportarExcelCabeceras());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarExcelCabeceras() throws FileNotFoundException, IOException, ParseException {
//        List<DetalleCompraSri> descargar = servicioDetalleComprasSri.findByBetweenFechaSRI(inicioComp, finComp);
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "comprassri.xls";
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
            ch4.setCellValue(new HSSFRichTextString("BASE 0%"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("BASE12%"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("IVA"));
            ch6.setCellStyle(estiloCelda);

            HSSFCell ch7 = r.createCell(j++);
            ch7.setCellValue(new HSSFRichTextString("TOTAL"));
            ch7.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (CabeceraCompraSri item : listaCabeceraCompraSris) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getCabNumFactura()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getCabRucProveedor()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCabProveedor()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getCabFechaEmision())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCabSubTotalCero(), 2).toString()));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCabSubTotal(), 2).toString()));

                HSSFCell c6 = r.createCell(i++);
                c6.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCabIva(), 2).toString()));
                HSSFCell c7 = r.createCell(i++);
                c7.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCabTotal(), 2).toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaCabeceraCompraSris.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    public ListModelList<CabeceraCompraSri> getListaComprasSriModel() {
        return listaComprasSriModel;
    }

    public void setListaComprasSriModel(ListModelList<CabeceraCompraSri> listaComprasSriModel) {
        this.listaComprasSriModel = listaComprasSriModel;
    }

    public Set<CabeceraCompraSri> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<CabeceraCompraSri> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    @Command
    @NotifyChange({"inicio", "fin", "listaComprasSriModel"})
    public void eliminarRetencionesSRI() {
        if (Messagebox.show("Esta seguro de eliminar los registros?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
            retencionSri.eliminarRetencionesSri(inicio, fin, amb);
            findCabeceraComprasSriByBetweenFecha();
            Clients.showNotification("Registros eliminados correctamente ",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);

        }
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

//PROCESAR XML desde SRI
    @Command
    @NotifyChange({"listaRetencioSri", "inicio", "fin"})
    public void procesarXMLRetencionSRI(File xml, String version) {
        String pathDoc = PATH_BASE + File.separator + SRIRETENCION;
        String pathArchivoXML = "";
        File folder = new File(pathDoc);
        File[] listaArvhivos = folder.listFiles();
        System.out.println("NUMERO ARCHIVOS " + listaArvhivos.length);
//        for (File file : listaArvhivos) {

        try {

            String xmlParse = leerArchivo(xml, version);

            xmlParse = xmlParse.replace("id=\"comprobante\" version=\"2.0.0\"", "");
            xmlParse = xmlParse.replace("id=\"comprobante\" version=\"1.0.0\"", "");
            System.out.println("IMPRIME LE ARCHIVO FORMATEADO \n" + xmlParse);
            JAXBContext jaxbContext;

            try {

                if (version.equals("1")) {
                    jaxbContext = JAXBContext.newInstance(ComprobanteRetencion.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    ComprobanteRetencion retenciones = (ComprobanteRetencion) jaxbUnmarshaller.unmarshal(new StringReader(xmlParse));

                    System.out.println(retenciones);
                    procesaRetencionesXMLV1(retenciones);
                } else if (version.equals("2")) {
                    jaxbContext = JAXBContext.newInstance(comprobanteRetencion.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    comprobanteRetencion retenciones = (comprobanteRetencion) jaxbUnmarshaller.unmarshal(new StringReader(xmlParse));

                    System.out.println(retenciones);
                    procesaRetencionesXML(retenciones);
                }

            } catch (JAXBException e) {
                e.printStackTrace();
            }

            findBetweenRetenciones();
        } catch (Exception e) {
            System.out.println(e);
        }
//        }

    }

    private void procesaRetencionesXMLV1(ComprobanteRetencion dao) {
        retencionSri.eliminarbyClaveAcceso(dao.getInfoTributaria().getClaveAcceso());
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");
        RetencionCompraSri retencionCompraSri = new RetencionCompraSri();
        retencionCompraSri.setRcoRuc(dao.getInfoTributaria().getRuc());
        retencionCompraSri.setRcoNombre(dao.getInfoTributaria().getRazonSocial());
        retencionCompraSri.setRcoAutorizacion(dao.getInfoTributaria().getClaveAcceso());
        retencionCompraSri.setRcoDetalle("RETENCION OBTENIDA DESDE EL SRI");
        retencionCompraSri.setCodTipoambiente(amb);
        Date dt = null;
        try {
            dt = sm.parse(dao.getInfoCompRetencion().getFechaEmision());
        } catch (java.text.ParseException e) {
            System.out.println("ERROR FECHA " + e.getMessage());
        }

        /*total retenido*/
        BigDecimal valorRetenido = BigDecimal.ZERO;
        for (Impuesto item : dao.getImpuestos().getImpuesto()) {

            valorRetenido = valorRetenido.add(BigDecimal.valueOf(Double.valueOf(item.getValorRetenido())));

        }
        retencionCompraSri.setRcoBaseGravaIva(valorRetenido);
        retencionCompraSri.setRcoFecha(dt);
        retencionCompraSri.setCabFechaEmision(dt);
        retencionCompraSri.setRcoIva(Boolean.FALSE);
        retencionCompraSri.setRcoPorcentajeIva(12);
        retencionCompraSri.setRcoPuntoEmision(dao.getInfoTributaria().getPtoEmi());
        retencionCompraSri.setRcoSecuencialText(dao.getInfoTributaria().getSecuencial());
        retencionCompraSri.setRcoSerie("1");
        retencionCompraSri.setRcoValorRetencionIva(0);
        retencionCompraSri.setDrcEstadosri("AUTORIZADO");
        retencionCompraSri.setRcoPorcentajeIva(12);
//        retencionCompraSri.setRcoNumFactura(dao.getImpuestos().getImpuesto().get(0));
        retencionSri.crear(retencionCompraSri);
        DetalleRetencionCompraSri detalle = null;
        for (Impuesto item : dao.getImpuestos().getImpuesto()) {
            detalle = new DetalleRetencionCompraSri();
            detalle.setDrcBaseImponible(Double.valueOf(item.getBaseImponible()));
            detalle.setDrcPorcentaje(Double.valueOf(item.getPorcentajeRetener()));
            detalle.setDrcValorRetenido(Double.valueOf(item.getValorRetenido()));
            detalle.setRcoCodigo(retencionCompraSri);
            detalle.setDrcCodImpuestoAsignado(item.getCodigo());
            detalle.setDrcDescripcion(item.getCodigo().equals("2") ? "IVA" : "RENTA");
            detalle.setDrcNumFactura(item.getNumDocSustento());
            detalleRetencionSri.crear(detalle);
        }

    }

}
