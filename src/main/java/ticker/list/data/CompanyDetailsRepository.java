package ticker.list.data;

import org.springframework.data.repository.CrudRepository;

import ticker.list.domain.CompanyDetails;

public interface CompanyDetailsRepository
    extends CrudRepository<CompanyDetails, String> {

}
