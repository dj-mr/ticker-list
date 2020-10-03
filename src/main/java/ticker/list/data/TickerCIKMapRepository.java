package ticker.list.data;

import org.springframework.data.repository.CrudRepository;

import ticker.list.domain.TickerCIKMap;

public interface TickerCIKMapRepository
    extends CrudRepository<TickerCIKMap, String> {

}
