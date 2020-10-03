package ticker.list.domain;

import java.time.Clock;
import java.time.ZonedDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * CIK, ticker list as shared in https://www.sec.gov/files/company_tickers.json.
 * @author jdaruri
 *
 */
@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Cacheable
public class TickerCIKMap {

    /**
     * CIK number.
     */
    @Id
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
