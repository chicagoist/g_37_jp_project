package de.aittr.g_37_jp_supplier.scheduler;

import de.aittr.g_37_jp_shop.domain.dto.ProductSupplyDto;
import de.aittr.g_37_jp_supplier.service.interfaces.HttpService;
import de.aittr.g_37_jp_supplier.service.interfaces.RequestCalculator;
import de.aittr.g_37_jp_supplier.service.interfaces.SupplyRequestService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class SupplyTaskExecutor {


    private HttpService httpService;
    private RequestCalculator requestCalculator;
    private SupplyRequestService supplyRequestService;

    public SupplyTaskExecutor(HttpService httpService, RequestCalculator requestCalculator, SupplyRequestService supplyRequestService) {
        this.httpService = httpService;
        this.requestCalculator = requestCalculator;
        this.supplyRequestService = supplyRequestService;
    }

    @Scheduled(cron = "0,30 * * * * *")
    public void sendSupplyRequest() {

        List<ProductSupplyDto> products = httpService.getAvailableProducts();
        Map<String,Integer> supplyRequest =
                requestCalculator.calculateRequest(products);

        supplyRequestService.sendSupplyRequest(supplyRequest);
    }
}
