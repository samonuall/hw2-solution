package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
  private static final String HEADER = "RowNum,Amount,Category,Date";

  public void export(List<Transaction> transactions, String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath)) {
      writer.append(HEADER).append("\n");
      int serial = 1;
      for (Transaction t : transactions) {
        writer.append(String.valueOf(serial++))
              .append(",").append(String.valueOf(t.getAmount()))
              .append(",").append(escapeSpecialChars(t.getCategory()))
              .append(",").append(t.getTimestamp().toString())
              .append("\n");
      }
    }
  }

  private String escapeSpecialChars(String data) {
    if (data == null) return "";
    if (data.contains("\"") || data.contains(",") || data.contains("\n")) {
      String escaped = data.replace("\"", "\"\"");
      return "\"" + escaped + "\"";
    }
    return data;
  }
}
