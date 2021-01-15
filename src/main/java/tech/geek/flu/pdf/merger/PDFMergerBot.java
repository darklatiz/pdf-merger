package tech.geek.flu.pdf.merger;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PDFMergerBot {

  public static void main(String[] args) throws IOException {
    if (Objects.isNull(args) || args.length <= 0) {
      log.error("Arguments needed:");
      log.error("source=<directory> target=<dir>/final.pdf");
    }
    String source = args[0];
    String target = args[1];
    List<PdfDocument> docs =  loadFolderSource(source.split("=")[1]);
    log.info("Lista de archivos = {}", docs);
    PdfDocument targetDoc = new PdfDocument(new PdfWriter(target.split("=")[1]));
    PdfMerger merger = new PdfMerger(targetDoc);

    docs.forEach(pdfDocumentReader -> merger.merge(pdfDocumentReader, 1, pdfDocumentReader.getNumberOfPages()));
    docs.forEach(PdfDocument::close);
    targetDoc.close();
  }

  private static List<PdfDocument> loadFolderSource(String source) throws IOException {
    List<Path> files = Files.list(Paths.get(source))
        .filter(path -> path.toString().toLowerCase(Locale.ROOT).endsWith(".pdf")).sorted(Comparator.comparing(Path::toString)).collect(Collectors.toList());

    return files.stream().map(path -> {
      try {
        return new PdfDocument(new PdfReader(path.toString()));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }).collect(Collectors.toList());
  }

}
