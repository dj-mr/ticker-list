package ticker.list.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ticker.list.data.OrganizationDetailsRepository;
import ticker.list.domain.OrganizationDetails;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class OrganizationDetailsProcessorImpl implements OrganizationDetailsProcessor {

    @Value("${sec.urls.company-details.prefix}")
    String urlPrefix;

    @Value("${sec.urls.company-details.suffix}")
    String urlSuffix;

    /**
     * Autowire OrganizationDetailsRepository.
     */
    @Autowired
    OrganizationDetailsRepository organizationDetailsRepository;

    /**
     * Batch size for insert operations.
     */
    @Value("${database.batch-size.inserts}")
    private int insertBatchSize;

    @Override
    public void updateOrganizationDetails(List<String> ciksToRefresh) {

        Function<OrganizationDetails, String> getCikValue
                = OrganizationDetails::getCik;

        /*
         * This is an expensive update. So remove records for which
         * data is already available.
         */
        List<String> ciksAlreadyInDatabase = StreamSupport.stream(
                organizationDetailsRepository
                        .findAll()
                        .spliterator(), false)
                .map(getCikValue)
                .collect(Collectors.toList())
        ;

        /*
         * Get CIK data for only those CIKs which are not already downloaded.
         */
        ciksToRefresh
                .parallelStream()
                .filter(cik -> !ciksAlreadyInDatabase.contains(StringUtils.leftPad(cik, 10, '0')))
                .forEach(this::extractContentFromUrl)
        ;

    }

    void extractContentFromUrl(String cik) {

        OrganizationDetails organizationDetail = new OrganizationDetails();

        try {
            Document document = Jsoup.connect(urlPrefix + cik + urlSuffix).get();
            ListIterator<Element> headerElements = document.select(".c0").listIterator();
            ListIterator<Element> valueElements = document.select(".c2").listIterator();

            while (headerElements.hasNext() && valueElements.hasNext()) {

                String headerName = headerElements.next().ownText().strip();
                String value = valueElements.next().ownText().strip();

                switch (headerName) {
                    case "Company Name:":
                        organizationDetail.setName(value.equals("")? "N/A": value);
                        break;
                    case "CIK:":
                        organizationDetail.setCik(value.equals("")? "N/A": value);
                        break;
                    case "IRS Number:":
                        organizationDetail.setIrsNumber(value.equals("")? "N/A": value);
                        break;
                    case "Reporting File Number:":
                        organizationDetail.setReportingFileNumber(value.equals("")? "N/A": value);
                        break;
                    case "Regulated Entity Type:":
                        organizationDetail.setRegulatedEntityType(value.equals("")? "N/A": value);
                        break;
                    case "SIC Code:":
                        organizationDetail.setSicCode(value.equals("")? "N/A": value);
                        break;
                    case "Address:":
                        organizationDetail.setAddress(value.equals("")? "N/A": value);
                        break;
                    case "Phone Number:":
                        organizationDetail.setPhoneNumber(value.equals("")? "N/A": value);
                        break;
                    case "State of Incorporation:":
                        organizationDetail.setStateOfIncorporation(value.equals("")? "N/A": value);
                        break;
                    case "Fiscal Year End:":
                        organizationDetail.setFiscalYearEnd(value.equals("")? "N/A": value);
                        break;
                    case "Date of Last Update:":
                        organizationDetail.setDateOfLastUpdate(value.equals("")? "N/A": value);
                        break;
                    default:
                        log.error("Unexpected element for CIK {}, element {}", cik, headerName);
                        break;
                }

            }
            organizationDetailsRepository.save(organizationDetail);
            log.debug("Saved data for CIK: {}", cik);
        } catch (HttpStatusException httpStatusException) {
            log.error("CIK {} processing resulted in HTTP Status exception {}", cik, httpStatusException.getStatusCode());
        } catch (IOException e) {
            log.error("CIK {} processing resulted in IOexception {}", cik, e.getMessage());
            e.printStackTrace();
        }

    }

}
