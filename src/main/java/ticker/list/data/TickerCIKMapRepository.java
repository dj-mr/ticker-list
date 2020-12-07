package ticker.list.data;

import org.springframework.data.repository.CrudRepository;
import ticker.list.domain.TickerCikMap;

/**
 * Interface that defines CRUD methods on TickerCikMap model.
 */
public interface TickerCIKMapRepository
        extends CrudRepository<TickerCikMap, String> {

}
