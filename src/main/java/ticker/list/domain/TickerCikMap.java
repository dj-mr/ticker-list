package ticker.list.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * CIK, ticker list as shared in https://www.sec.gov/files/company_tickers.json.
 * @author jdaruri
 *
 */
@Data
@Entity
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Cacheable
public class TickerCikMap {

    /**
     * CIK number.
     */
    @Id
    private String cik;

    /**
     * Ticker symbol.
     */
    private String ticker;

    /**
     * Company Name.
     */
    private String name;

}
