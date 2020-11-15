package ticker.list.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ticker.list.data.TickerInfoRepository;
import ticker.list.domain.TickerInfo;
import ticker.list.processors.CIKProcessor;
import ticker.list.processors.OrganizationDetailsProcessor;

@Slf4j
@RestController
@EnableCaching
@RequestMapping(path = "/tickers", produces = "application/json")
@CrossOrigin(origins = "*")
public class TickerController {

    /**
     * TickerInfoRepository.
     */
    private TickerInfoRepository tickerInfoRepository;

    /**
     * Variable used to for processing CIK data.
     */
    @Autowired
    private CIKProcessor cikProcessor;
    
    @Autowired
    private OrganizationDetailsProcessor organizationDetailsProcessor;

    /**
     * EntityLinks is used to get data from related domain models.
     */
    @Autowired
    private EntityLinks entityLinks;

    /**
     * Fetch Ticker Details for all tickers.
     * @return List of all tickers
     */
    @GetMapping
    public Iterable<TickerInfo> getTickerInfo() {
        return tickerInfoRepository.findAll();
    }

    /**
     * Refresh content in database.
     */
    @PutMapping
    public void updateTickerInfo() {

        List<String> ciksRefreshed = cikProcessor.refreshCIKData();
        organizationDetailsProcessor.updateOrganizationDetails(ciksRefreshed);
    }

}
