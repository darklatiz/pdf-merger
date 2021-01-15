package tech.geek.flu.pdf.merger;

import com.itextpdf.kernel.pdf.PdfDocument;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Objects;

@Slf4j
public class PDFMergerBot {
  private static final FilenameFilter txtFileFilter;

  static {
    txtFileFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".pdf");
      }
    };
  }

  public static void main(String[] args) {
    if (Objects.isNull(args) || args.length <= 0) {
      log.error("Arguments needed:");
      log.error("source=<directory> target=<dir>/final.pdf");
    }

    List<PdfDocument> docs =  loadFolderSource();
  }

  private static List<PdfDocument> loadFolderSource() {

    return null;
  }

}
