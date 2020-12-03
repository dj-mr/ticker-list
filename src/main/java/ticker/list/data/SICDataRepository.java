package ticker.list.data;

import org.springframework.data.repository.CrudRepository;
import ticker.list.domain.SicData;

public interface SICDataRepository
    extends CrudRepository<SicData, String> {

}
