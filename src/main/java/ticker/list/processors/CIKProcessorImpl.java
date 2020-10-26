package ticker.list.processors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import ticker.list.data.TickerCIKMapRepository;
import ticker.list.domain.TickerCikMap;

@Component
@Slf4j
public class CIKProcessorImpl implements CIKProcessor {

    /**
     * Autowire TickerCIKMapRepository.
     */
    @Autowired
    TickerCIKMapRepository tickerCIKMapRepository;

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
     */
    @Override
    public void refreshCIKData() {

        List<TickerCikMap> tickerCIKMapList = new ArrayList<>();

        try (JsonParser jsonParser = new JsonFactory().createParser(new URL(ciks));) {

            // Check if JSON array?
            if (jsonParser.nextToken() == JsonToken.START_OBJECT) {

                // If Array, loop until corresponding ARRAY end is found
                while ((jsonParser.nextToken() != JsonToken.END_OBJECT)) {

                    TickerCikMap tickerCikMap =  new TickerCikMap();
                    // Read each element until "}" is reached.
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

                        String fieldName = jsonParser.getCurrentName();

                        if ("cik_str".equals(fieldName)) {

                            // Extract next token
                            jsonParser.nextToken();

                            // Save value of token in CIK
                            tickerCikMap.setCik(jsonParser.getText());
                        }

                        if ("ticker".equals(fieldName)) {

                            // Extract next token
                            jsonParser.nextToken();

                            // Save value of token in Ticker
                            tickerCikMap.setTicker(jsonParser.getText());
                        }
                        if ("title".equals(fieldName)) {

                            // Extract next token
                            jsonParser.nextToken();

                            // Save value of token in Name
                            tickerCikMap.setName(jsonParser.getText());
                        }
                    }
                    tickerCIKMapList.add(tickerCikMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save content to DB in batches
        Iterables.partition(tickerCIKMapList, insertBatchSize).forEach(batch -> {
            log.info("Processed batch with size: {}. 1st Record: {}", batch.size(), batch.get(0));
            tickerCIKMapRepository.saveAll(batch);
        });

    }

}
