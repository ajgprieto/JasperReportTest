package testjasperreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRCsvDataSource;

public class CreateJasperReport {

	public static void main(String[] args) {

		String fileName = "export-UTF8.csv";
		String jasperFile = "C:\\Users\\AJ\\JaspersoftWorkspace\\MyReports\\DHS_IVR.jasper";

		// Date and time of creation will be appended to the end
		String exportFileName = "DHS-IVR";

		JRCsvDataSource dataSource = getDataSource(fileName);

		Map<String, Object> map = new HashMap<>();
		map.put("Stuff", "MOre stuff");
		map.put("OtherStuff", "More other stuff");
		JasperPrint print = getPrintObject(jasperFile, map, dataSource);

		printToPdf(print, exportFileName);
	}

	private static JRCsvDataSource getDataSource(String fileName) {
		JRCsvDataSource dataSource = null;

		try {
			dataSource = new JRCsvDataSource(new File(fileName), "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataSource.setUseFirstRowAsHeader(true);
		dataSource.setFieldDelimiter('|');
		dataSource.setRecordDelimiter("\r\n");

		return dataSource;
	}

	private static JasperPrint getPrintObject(String jasperFile,
			Map<String, Object> parameters, JRCsvDataSource dataSource) {

		JasperPrint print = null;
		try {
			print = JasperFillManager.fillReport(jasperFile, parameters,
					dataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return print;

	}

	private static void printToPdf(JasperPrint printObject,
			String exportFileName) {

		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyy-HH_mm");
		Calendar cal = Calendar.getInstance();

		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(exportFileName + "-"
					+ sdf.format(cal.getTime()) + ".pdf"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			JasperExportManager.exportReportToPdfStream(printObject, os);
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

}
