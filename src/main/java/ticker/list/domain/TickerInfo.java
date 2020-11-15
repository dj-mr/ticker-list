package ticker.list.domain;

import java.time.Clock;
import java.time.ZonedDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Data;

/**
 * This is the structure of data exposed by this application.
 * @author jdaruri
 *
 */
@Data
@Cacheable
@Entity
public class TickerInfo {

    /**
     * Primary key for TickerInfoRepository. This is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Ticker CIK Mapping.
     */
    @OneToOne(targetEntity = TickerCikMap.class)
    private TickerCikMap tickerCikMap;

    /**
     * Ticker Organization details.
     */
    @OneToOne(targetEntity = OrganizationDetails.class)
    private OrganizationDetails organizationDetails;

    /**
     * Ticker Industry Code details.
     */
    @ManyToOne(targetEntity = SicData.class)
    private SicData sicData;

    /**
     * Date and time when record was updated.
     */
    private ZonedDateTime zonedDateTime;

    @PrePersist
    void lastUpdatedTimestamp() {
        this.zonedDateTime = ZonedDateTime.now(Clock.systemUTC());
    }


}
