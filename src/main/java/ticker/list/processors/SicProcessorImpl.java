package ticker.list.processors;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ticker.list.data.SICDataRepository;
import ticker.list.domain.SicData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class implements SicProcessor interface.
 */
@Component
@Slf4j
public class SicProcessorImpl implements SicProcessor {

    /**
     * Variable that hold SIC codes' URL as string.
     */
    @Value("${sec.urls.sic-codes}")
    private String sicCodesUrl;

    /**
     * SICDataRepository accessor.
     */
    @Autowired
    private SICDataRepository sicDataRepository;

    /**
     * SIC Data with corresponding details is captured in this variable.
     */
    private List<SicData> sicDataList = new ArrayList<>();

    /**
     * This method refreshes SIC Codes. It fetches data from SIC Codes URL and saves it in database.
     */
    @Override
    public void refreshSicCodes() {

        try {
            Document document = Jsoup.connect(sicCodesUrl).get();
            ListIterator<Element> codeValues = document.select("td").listIterator();

            while (codeValues.hasNext()) {
                sicDataList.add(new SicData(
                        codeValues.next().ownText(),
                        codeValues.next().ownText(),
                        codeValues.next().ownText()
                ));
            }

            sicDataRepository.saveAll(sicDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
