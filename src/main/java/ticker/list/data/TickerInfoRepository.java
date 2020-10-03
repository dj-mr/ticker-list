package ticker.list.data;

import org.springframework.data.repository.CrudRepository;

import ticker.list.domain.TickerInfo;

public interface TickerInfoRepository
    extends CrudRepository<TickerInfo, Long> {

}
