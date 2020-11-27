package ticker.list.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;

/**
 * Domain Model based on definitions at
 * https://www.edgarcompany.sec.gov/servlet/CompanyDBSearch?page=detailed&cik=0000789019&main_back=2.
 * @author jdaruri
 *
 */
@Data
@Entity
@RequiredArgsConstructor
@Cacheable
public class OrganizationDetails implements Serializable {

    /**
     * Name of the Organization.
     */
    private String name;

    /**
     * CIK of the company.
     */
    @Id
    private String cik;

    /**
     * IRS Number of the company.
     */
    private String irsNumber;

    /**
     * Reporting File Number of the company.
     */
    private String reportingFileNumber;

    /**
     * Regulatory Entity Type of the company.
     */
    private String regulatedEntityType;

    /**
     * Industry Code of the company.
     */
    private String sicCode;

    /**
     * Address as updated in SEC.
     */
    private String address;

    /**
     * Phone number as updated in SEC.
     */
    private String phoneNumber;

    /**
     * State of incorporation of the company.
     */
    private String stateOfIncorporation;

    /**
     * Fiscal Year end as updated in SEC.
     */
    private String fiscalYearEnd;

    /**
     * Date when this data was updated in SEC.
     */
    private String dateOfLastUpdate;

    /**
     * Date Record is added to the ticker-list database.
     */
    private Date createdAt;
    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    @OneToOne
    @JoinColumn(name = "sicCode", insertable = false, updatable = false)
    private SicData sicData;

}
