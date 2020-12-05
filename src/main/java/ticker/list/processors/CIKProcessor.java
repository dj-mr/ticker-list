package ticker.list.processors;

import java.util.List;

public interface CIKProcessor {

    /**
     * This method is used to refresh data in CIK database.
     *
     * @return returns list of CIKs returned by Ticker-CIK-Name URI.
     */
    List<String> refreshCIKData();

}
