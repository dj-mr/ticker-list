package ticker.list.processors;

import org.springframework.stereotype.Service;

@Service
public interface SicProcessor {

    /**
     * Refresh Standard Industrial Classification (SIC) Codes.
     */
    void refreshSicCodes();

}
