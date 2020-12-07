package ticker.list.data;

import org.springframework.data.repository.CrudRepository;
import ticker.list.domain.SicData;

/**
 * Interface that defines CRUD methods on SicData model.
 */
public interface SICDataRepository
        extends CrudRepository<SicData, String> {

}
