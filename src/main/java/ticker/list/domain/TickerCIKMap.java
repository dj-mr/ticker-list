package ticker.list.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * CIK, ticker list as shared in https://www.sec.gov/files/company_tickers.json.
 * @author jdaruri
 *
 */
@Data
@RequiredArgsConstructor
public class TickerCIKMap {

    /**
     * CIK number.
     */
    private final String cik;

    /**
     * Ticker symbol.
     */
    private final String ticker;

    /**
     * Company Name.
     */
    private final String name;

}
