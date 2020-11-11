package ticker.list.domain;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * SIC codes as defined in https://www.sec.gov/info/edgar/siccodes.htm.
 * @author jdaruri
 *
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@Cacheable
public class SicData {

    /**
     * SIC Code.
     */
    @Id
    private final String sicCode;

    /**
     * Industry code as defined in SEC.
     */
    private final String industry;

    /**
     * Industry categorization description as defined in SEC.
     */
    private final String sicTitle;

}
