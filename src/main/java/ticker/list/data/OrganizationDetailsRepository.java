package ticker.list.data;

import org.springframework.data.repository.CrudRepository;
import ticker.list.domain.OrganizationDetails;

/**
 * Interface that defines CRUD methods on OrganizationDetails model.
 */
public interface OrganizationDetailsRepository
        extends CrudRepository<OrganizationDetails, String> {

}
