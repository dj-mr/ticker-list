package ticker.list.domain;

import java.time.Clock;
import java.time.ZonedDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Domain Model based on definitions at
 * https://www.edgarcompany.sec.gov/servlet/CompanyDBSearch?page=detailed&cik=0000789019&main_back=2.
 * @author jdaruri
 *
 */
@Data
@Entity
@Cacheable
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CompanyDetails {

    /**
     * Name of the company.
     */
    private final String name;

    /**
     * CIK of the company.
     */
    @Id
    private final String cik;

    /**
     * IRS Number of the company.
     */
    private final String irsNumber;

    /**
     * Reporting File Number of the company.
     */
    private final String reportingFileNumber;

    /**
     * Regulatory Entity Type of the company.
     */
    private final String regulatedEntityType;

    /**
     * Industry Code of the company.
     */
    private final String sicCode;

    /**
     * Address as updated in SEC.
     */
    private final String address;

    /**
     * Phone number as updated in SEC.
     */
    private final String phoneNumber;

    /**
     * State of incorporation of the company.
     */
    private final String stateOfIncorporation;

    /**
     * Fiscal Year end as updated in SEC.
     */
    private final String fiscalYearEnd;

    /**
     * Date when this data was updated in SEC.
     */
    private final String dateOfLastUpdate;

}
