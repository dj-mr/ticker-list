package ticker.list.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ticker.list.data.TickerCIKMapRepository;
import ticker.list.processors.CIKProcessor;
import ticker.list.processors.OrganizationDetailsProcessor;
import ticker.list.processors.SicProcessor;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(TickerController.class)
public class WebConfigTest {

    @MockBean
    TickerCIKMapRepository tickerCIKMapRepository;
    @MockBean
    CIKProcessor cikProcessor;
    /**
     * Mock MVC used for creating Mocked MVC calls.
     */
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrganizationDetailsProcessor organizationDetailsProcessor;

    @MockBean
    private SicProcessor sicProcessor;

    /**
     * Testing Home Page Response.
     *
     * @throws Exception thrown by URI resource.
     */
    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Ticker")))
        ;

    }

}
