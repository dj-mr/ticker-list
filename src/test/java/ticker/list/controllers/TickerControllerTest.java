package ticker.list.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ticker.list.data.TickerCIKMapRepository;
import ticker.list.domain.TickerCikMap;
import ticker.list.processors.CIKProcessor;
import ticker.list.processors.OrganizationDetailsProcessor;
import ticker.list.processors.SicProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TickerController.class)
@Slf4j
public class TickerControllerTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    TickerCIKMapRepository tickerCIKMapRepository;
    @MockBean
    CIKProcessor cikProcessor;
    File getAllTickers = new File("src/test/resources/jsons/expected/getAll.json"),
            msftTicker = new File("src/test/resources/jsons/expected/getMsftTicker.json"),
            aalTicker = new File("src/test/resources/jsons/expected/getAalTicker.json");
    String allTickersAsString, msftTickerAsString, aalTickerAsString;
    List<TickerCikMap> allTickersList, msftTickerList, aalTickersList;
    @MockBean
    private OrganizationDetailsProcessor organizationDetailsProcessor;
    @MockBean
    private SicProcessor sicProcessor;
    @Autowired
    private MockMvc mockMvc;

    /**
     * Initial setup before executing test cases.
     */
    @Before
    public void setUp() {
        try {
            allTickersAsString = FileUtils.readFileToString(getAllTickers, Charset.defaultCharset());
            msftTickerAsString = FileUtils.readFileToString(msftTicker, Charset.defaultCharset());
            aalTickerAsString = FileUtils.readFileToString(aalTicker, Charset.defaultCharset());

            allTickersList = objectMapper.readValue(allTickersAsString, new TypeReference<List<TickerCikMap>>() {
            });
            msftTickerList = objectMapper.readValue(msftTickerAsString, new TypeReference<List<TickerCikMap>>() {
            });
            aalTickersList = objectMapper.readValue(aalTickerAsString, new TypeReference<List<TickerCikMap>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test Get all tickers.
     * Ticker result set is mocked to return data from getAll.json file instead of
     * getting data from database
     *
     * @throws Exception during perform statement in case of issues
     */
    @Test
    public void testGetAllTickers_mustGetAllTickers() throws Exception {
        Mockito.when(tickerCIKMapRepository.findAll()).thenReturn(allTickersList);
        mockMvc.perform(get("/tickers"))
                .andExpect(status().isOk())
                .andExpect(request().asyncResult(allTickersList))
        ;
    }

    /**
     * Test Get tickers given ticker value.
     * Ticker result set is mocked to return data from getMsftTicker.json file instead of
     * getting data from database
     *
     * @throws Exception during perform statement in case of issues
     */
    @Test
    public void testGetByTicker_mustGetSpecificTicker() throws Exception {
        Mockito.when(tickerCIKMapRepository.findAll()).thenReturn(allTickersList);
        mockMvc.perform(get("/tickers").param("ticker_symbol", "msft"))
                .andExpect(status().isOk())
                .andExpect(request().asyncResult(msftTickerList))
        ;
    }

    /**
     * Test Get tickers for a ticker that is not in database.
     * Ticker result set is mocked to return data from getAll.json file instead of
     * getting data from database. Since 'unknown' ticker is not in list, empty response
     * is expected.
     *
     * @throws Exception during perform statement in case of issues
     */
    @Test
    public void testGetByTicker_mustGetEmptyArrayAsTickerIsNotFound() throws Exception {
        Mockito.when(tickerCIKMapRepository.findAll()).thenReturn(allTickersList);
        mockMvc.perform(get("/tickers").param("ticker_symbol", "unknown"))
                .andExpect(status().isOk())
                .andExpect(request().asyncResult(aalTickersList))
        ;
    }

    /**
     * Test PUT REST request.
     * All processing beans are mocked. So actual calls will not be invoked
     *
     * @throws Exception during perform statement in case of issues
     */
    @Test
    public void testPutRequest_mustReturnStatusCode200() throws Exception {
        mockMvc.perform(put("/tickers"))
                .andExpect(status().isOk())
        ;
    }

}
