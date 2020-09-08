package tech.geek.flu.pdf.merger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PDFMergerBot {
  private static String INPUT_DIR = "/Users/vn04q89/Documents/pdfIn";
  private static String OUTPUT_FILE = "/Users/vn04q89/Documents/joinedFile-{0}.pdf";

  public static void main(String[] args) throws IOException, DocumentException {

    if (args == null || args.length <= 0 || args[0].isEmpty()) {
      System.out.println("Error directories not set");
      System.exit(0);
    }

    INPUT_DIR = args[0];
    OUTPUT_FILE = args[1];

    FilenameFilter txtFileFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        if (name.endsWith(".pdf")) {
          return true;
        } else {
          return false;
        }
      }
    };

    OutputStream out = new FileOutputStream(new File(OUTPUT_FILE));
    Document joinedDocument = new Document();
    PdfWriter writer = PdfWriter.getInstance(joinedDocument, out);
    joinedDocument.open();
    PdfContentByte cb = writer.getDirectContent();

    List<Path> files = Files.list(Paths.get(INPUT_DIR)).filter(filePath -> filePath.toString().endsWith(".pdf")).collect(Collectors.toList());
    files.sort(Comparator.comparing(Path::toString));
    files.forEach(filePath -> {
      try {
        FileInputStream fis = new FileInputStream(filePath.toFile());
        PdfReader reader = new PdfReader(fis);
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
          joinedDocument.newPage();
          //import the page from source pdf
          PdfImportedPage page = writer.getImportedPage(reader, i);
          //add the page to the destination pdf
          cb.addTemplate(page, 0, 0);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    out.flush();
    joinedDocument.close();
    out.close();
  }
}
