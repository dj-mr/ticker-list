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
import ticker.constants.Constants;
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
     * Variable used to for processing CIK data.
     */
    @Autowired
    CIKProcessor cikProcessor;
    /**
     * TickerCIKMapRepository.
     */
    @Autowired
    private TickerCIKMapRepository tickerCIKMapRepository;

    @Autowired
    private OrganizationDetailsProcessor organizationDetailsProcessor;

    @Autowired
    private SicProcessor sicProcessor;

    /**
     * Fetch Ticker Details for all tickers.
     *
     * @param numberOfSamples Number of samples to be returned as part of GET response.
     * @return List of all tickers
     */
    @GetMapping
    public Flux<TickerCikMap> getAllTickers(
             @RequestParam(name = "ticker_symbol", required = false) String ticker
            ,@RequestParam(name = "cik", required = false) String cik
            ,@RequestParam(name = "name", required = false) String name
            ,@RequestParam(name = "number_of_samples", required = false, defaultValue = "100") int numberOfSamples
    ) {
        return Flux.fromIterable(
                tickerCIKMapRepository
                        .findAll()
        )
                .filter(row -> ticker == null || row.getTicker().equalsIgnoreCase(ticker))
                .filter(row -> cik == null || row.getCik().equalsIgnoreCase(cik))
                .filter(row -> name == null || row.getName().toLowerCase().contains(name.toLowerCase()))
                .take(numberOfSamples)
                .take(Duration.ofMillis(Constants.SLA_RESPONSE_FOR_GET_REQUEST));
    }

    /**
     * Refresh content in database.
     */
    @PutMapping
    public void updateTickerInfo() {
        sicProcessor.refreshSicCodes();
        List<String> ciksRefreshed = cikProcessor.refreshCIKData();
        new Thread(() -> organizationDetailsProcessor.updateOrganizationDetails(ciksRefreshed)).start();
    }

}
