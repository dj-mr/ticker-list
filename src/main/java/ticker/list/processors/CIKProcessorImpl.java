package ticker.list.processors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ticker.list.constants.Constants;
import ticker.list.data.TickerCIKMapRepository;
import ticker.list.domain.TickerCikMap;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static ticker.list.constants.Constants.CIK_PREFIX_CHAR;

@Component
@Slf4j
public class CIKProcessorImpl implements CIKProcessor {

    /**
     * Autowire TickerCIKMapRepository.
     */
    @Autowired
    private TickerCIKMapRepository tickerCIKMapRepository;

    /**
     * Variable that holds URI for CIK Data.
     */
    @Value("${sec.urls.ciks}")
    private String ciks;

    /**
     * Variable that holds path for CIK output folder.
     */
    @Value("${sec.local-folders.ciks}")
    private String cikLocalFolder;

    /**
     * Batch size for insert operations.
     */
    @Value("${database.batch-size.inserts}")
    private int insertBatchSize;

    /**
     * Refresh CIK data in CIK database.
     *
     * @return returns list of CIKs returned by Ticker-CIK-Name URI.
     */
    @Override
    public List<String> refreshCIKData() {

        List<TickerCikMap> tickerCIKMapList = new ArrayList<>();
        List<String> cikList = new ArrayList<>();

        try (JsonParser jsonParser = new JsonFactory().createParser(new URL(ciks))) {

            // Check if JSON array?
            if (jsonParser.nextToken() == JsonToken.START_OBJECT) {

                // If Array, loop until corresponding ARRAY end is found
                while ((jsonParser.nextToken() != JsonToken.END_OBJECT)) {

                    TickerCikMap tickerCikMap = new TickerCikMap();
                    // Read each element until "}" is reached.
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

                        String fieldName = jsonParser.getCurrentName();

                        switch (fieldName) {
                            case "cik_str":
                                // Extract next token
                                jsonParser.nextToken();

                                // Save value of token in CIK
                                tickerCikMap.setCik(StringUtils.leftPad(jsonParser.getText(),
                                        Constants.CIK_STRING_SIZE, CIK_PREFIX_CHAR));
                                cikList.add(StringUtils.leftPad(jsonParser.getText(),
                                        Constants.CIK_STRING_SIZE, CIK_PREFIX_CHAR));
                                break;
                            case "ticker":
                                // Extract next token
                                jsonParser.nextToken();

                                // Save value of token in Ticker
                                tickerCikMap.setTicker(jsonParser.getText());
                                break;
                            case "title":
                                // Extract next token
                                jsonParser.nextToken();

                                // Save value of token in Name
                                tickerCikMap.setName(jsonParser.getText());
                                break;
                            default:
                                break;

                        }
                    }
                    tickerCIKMapList.add(tickerCikMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save content to DB in batches
        Iterables.partition(tickerCIKMapList, insertBatchSize)
                .forEach(batch -> tickerCIKMapRepository.saveAll(batch));

        return cikList;

    }

}
