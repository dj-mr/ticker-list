package ticker.list.data;

import org.springframework.data.repository.CrudRepository;

import ticker.list.domain.SICData;

public interface SICDataRepository
    extends CrudRepository<SICData, String> {

}
