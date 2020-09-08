package ticker.list.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * SIC codes as defined in https://www.sec.gov/info/edgar/siccodes.htm.
 * @author jdaruri
 *
 */
@Data
@RequiredArgsConstructor
public class SICData {

    /**
     * SIC Code.
     */
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
