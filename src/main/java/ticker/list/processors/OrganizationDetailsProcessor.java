package ticker.list.processors;

import java.util.List;

public interface OrganizationDetailsProcessor {

    /**
     * This method saves details about an organization given the CIK.
     * @param ciksToRefresh - List of tickers whose data must be refreshed.
     */
    void updateOrganizationDetails(List<String> ciksToRefresh);

}
