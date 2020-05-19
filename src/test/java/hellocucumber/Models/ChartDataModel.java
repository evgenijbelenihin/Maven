package hellocucumber.Models;

import java.util.Map;
import lombok.Data;

@Data
public class ChartDataModel {
        private String              security;
        private String              barType;
        private Map<String, Long>[] bars;
    }

