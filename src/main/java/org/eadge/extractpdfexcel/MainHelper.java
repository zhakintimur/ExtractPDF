package org.eadge.extractpdfexcel;

import org.eadge.extractpdfexcel.data.ExtractedData;
import org.eadge.extractpdfexcel.data.RecordData;
import org.eadge.extractpdfexcel.data.SortedData;
import org.eadge.extractpdfexcel.data.XclPage;
import org.eadge.extractpdfexcel.data.block.Block;
import org.eadge.extractpdfexcel.exception.IncorrectFileTypeException;
import org.eadge.extractpdfexcel.models.TextBlockIdentifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainHelper {
  public static void peek(String sourcePDFPath) throws FileNotFoundException, IncorrectFileTypeException {
    Object parameters[] = {false, 1.0, 3.0, 2.0, 0.00001, 1.4, true, 0.0, 0.0};
    TextBlockIdentifier textBlockIdentifier = new TextBlockIdentifier(
        (double) parameters[1],
        (double) parameters[2],
        (double) parameters[3],
        (double) parameters[4],
        (double) parameters[5],
        (boolean) parameters[6]);
    int lineAxis = 0;
    int columnAxis = 1;
//    double lineFactor;
//    double columnFactor;

    List<RecordData> records = new ArrayList<>();

    // Extract data from the source pdf file
    ExtractedData extractedData = PdfConverter.extractFromFile(sourcePDFPath, textBlockIdentifier);

    // Sort Data
    SortedData sortedData = PdfConverter.sortExtractedData(extractedData, lineAxis, columnAxis);

    // Create 2D array pages containing information
    ArrayList<XclPage> excelPages = PdfConverter.createExcelPages(sortedData);

    boolean headerFlag = true;
    for (XclPage xclPage : excelPages) {
      for (int line = 0; line < xclPage.numberOfLines(); line++) {
//        if (lineFactor != 0)
//          createdLine.setHeight((short) (xclPage.getLineHeight(line) * 50));


        List<String> fields = new ArrayList<>();
        for (int col = 0; col < xclPage.numberOfColumns(); col++) {
          Block block = xclPage.getBlockAt(col, line);

          // If there is a block a the given position
          if (block != null) {
            fields.add(block.getFormattedText());
          }
        }

        if (fields.size() == 4) {
          records.add(
              new RecordData(
                  fields.get(0),
                  fields.get(1),
                  fields.get(2),
                  fields.get(3))
          );
        }
      }
    }

    for (RecordData record : records) {
      System.out.println(record);
    }
  }
}

