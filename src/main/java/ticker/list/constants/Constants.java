package ticker.list.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    /**
     * Size of CIK string as stored in database.
     */
    public static final int CIK_STRING_SIZE = 10;

    /**
     * Prefix for CIK string if its size is less than default size.
     */
    public static final char CIK_PREFIX_CHAR = '0';

    /**
     * SLA Response time for GET request.
     */
    public static final int SLA_RESPONSE_FOR_GET_REQUEST = 100;
}
