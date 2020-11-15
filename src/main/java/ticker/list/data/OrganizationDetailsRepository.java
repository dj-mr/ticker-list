package ticker.list.data;

import org.springframework.data.repository.CrudRepository;

import ticker.list.domain.OrganizationDetails;

public interface OrganizationDetailsRepository
    extends CrudRepository<OrganizationDetails, String> {

}
