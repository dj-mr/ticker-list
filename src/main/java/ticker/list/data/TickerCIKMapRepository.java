package ticker.list.data;

import org.springframework.data.repository.CrudRepository;
import ticker.list.domain.TickerCikMap;

public interface TickerCIKMapRepository
    extends CrudRepository<TickerCikMap, String> {

}
