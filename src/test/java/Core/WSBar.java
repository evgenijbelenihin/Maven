package Core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WSBar {
    public Long closeAsk;
    public Long closeBid;
    public Long highAsk;
    public Long highBid;
    public Long lowAsk;
    public Long lowBid;
    public Long openAsk;
    public Long openBid;
    public  Long time;
}
