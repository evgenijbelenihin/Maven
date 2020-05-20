package Core;

import java.util.List;
import lombok.Data;

@Data
public class ChartDataModel {
    private String              security;
    private String      barType;
    private List<WSBar> bars;
}

