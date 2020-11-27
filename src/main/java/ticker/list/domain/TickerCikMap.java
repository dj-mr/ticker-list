package ticker.list.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

/**
 * CIK, ticker list as shared in https://www.sec.gov/files/company_tickers.json.
 * @author jdaruri
 *
 */
@Data
@Entity
@RequiredArgsConstructor
@Cacheable
public class TickerCikMap implements Serializable {

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

    @OneToOne
    @PrimaryKeyJoinColumn
    private OrganizationDetails organizationDetails;

}
