package ticker.list.processors;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CIKProcessor {

    /**
     * This method is used to refresh data in CIK database.
     * @return returns list of CIKs returned by Ticker-CIK-Name URI.
     */
    List<String> refreshCIKData();

}
