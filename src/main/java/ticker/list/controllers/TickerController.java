package ticker.list.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ticker.list.data.TickerCIKMapRepository;
import ticker.list.domain.TickerCikMap;
import ticker.list.processors.CIKProcessor;
import ticker.list.processors.OrganizationDetailsProcessor;
import ticker.list.processors.SicProcessor;

import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@EnableCaching
@RequestMapping(path = "/tickers", produces = "application/json")
@CrossOrigin(origins = "*")
public class TickerController {

    /**
     * TickerCIKMapRepository.
     */
    @Autowired
    private TickerCIKMapRepository tickerCIKMapRepository;

    /**
     * Variable used to for processing CIK data.
     */
    @Autowired
    CIKProcessor cikProcessor;
    
    @Autowired
    private OrganizationDetailsProcessor organizationDetailsProcessor;

    @Autowired
    private SicProcessor sicProcessor;

    /**
     * Fetch Ticker Details for all tickers.
     * @return List of all tickers
     */
    @GetMapping
    public Flux<TickerCikMap> getAllTickers(@RequestParam(name = "number_of_samples", required = false, defaultValue = "100") int numberOfSamples) {
        return Flux.fromIterable(tickerCIKMapRepository.findAll()).take(numberOfSamples).take(Duration.ofSeconds(2));
    }

    /**
     * Refresh content in database.
     */
    @PutMapping
    public void updateTickerInfo() {
        sicProcessor.refreshSicCodes();
        List<String> ciksRefreshed = cikProcessor.refreshCIKData();
        organizationDetailsProcessor.updateOrganizationDetails(ciksRefreshed);
    }

}
