package ticker.list.processors;

import java.util.List;

public interface OrganizationDetailsProcessor {

    /**
     * This method saves details about an organization given the CIK.
     * @param CIK of the organization that will be refreshed.
     * @param ciksToRefresh
     */
    void updateOrganizationDetails(List<String> ciksToRefresh);

}
