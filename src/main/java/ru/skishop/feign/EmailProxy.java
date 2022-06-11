package ru.skishop.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.MailInfoDto;

@FeignClient(name = "notification1", url = "localhost:8081")
public interface EmailProxy {

    //    @RequestMapping(method = RequestMethod.POST, value = "/mail/send1")
//public ResponseEntity<Void> senEmail(String mailInfo);

    @RequestMapping(method = RequestMethod.POST, value = "/mail/send")
    ResponseEntity<Void> senEmail(MailInfoDto mailInfoDto);
}


//@FeignClient(name="forex-service" url="localhost:8000")
//public interface CurrencyExchangeServiceProxy {
//    @GetMapping("/currency-exchange/from/{from}/to/{to}")
//    public CurrencyConversionBean retrieveExchangeValue
//            (@PathVariable("from") String from, @PathVariable("to") String to);
//}