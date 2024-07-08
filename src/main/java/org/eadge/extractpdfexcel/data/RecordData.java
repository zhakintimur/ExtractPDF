package org.eadge.extractpdfexcel.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordData {
  private String date;
  private String sum;
  private String info;
  private String details;
}
