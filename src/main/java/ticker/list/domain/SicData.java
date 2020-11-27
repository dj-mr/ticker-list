package ticker.list.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * SIC codes as defined in https://www.sec.gov/info/edgar/siccodes.htm.
 * @author jdaruri
 *
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Cacheable
public class SicData implements Serializable {

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
