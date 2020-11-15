package ticker.list.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
public class OrganizationDetails {

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

}
