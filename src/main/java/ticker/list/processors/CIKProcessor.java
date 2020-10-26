package ticker.list.processors;

import org.springframework.stereotype.Service;

@Service
public interface CIKProcessor {

    /**
     * This method is used to refresh data in CIK database.
     */
    public void refreshCIKData();

}
