package id.sis.service.accounting.businessprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.sis.service.accounting.properties.SISApiProperties;

@Component
public class SISApi {
    private final static Logger logger = LoggerFactory.getLogger(SISApi.class);

    @Autowired
    private SISApiProperties sisApiProperties;

//    public SISResponseDto<DispatchScheduleDto> getDispatchScheduler(Integer cbBatchLoadId, String cbOrderNo, String status, Integer id) throws Exception {
//        logger.info("[SIS] Call API getCBDispatchSchedule");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.set("SIS-Token", sisApiProperties.getToken());
//
//        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
//        formData.add("ad_org_id", sisApiProperties.getOrgid());
//        
//        if (cbBatchLoadId != null)
//            formData.add("c_batch_load_id", cbBatchLoadId);
//         
//        if (cbOrderNo != null)
//            formData.add("cb_order_no", cbOrderNo);
//
//        if (status != null)
//            formData.add("status", status);
//            
//        if (id != null)
//            formData.add("id", id);
//
//        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(formData, httpHeaders);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String url = sisApiProperties.getUrl() + "api/ws/other/v1/getCBDispatchSchedule";
//
//        ParameterizedTypeReference<SISResponseDto<DispatchScheduleDto>> responseType = new ParameterizedTypeReference<SISResponseDto<DispatchScheduleDto>>() {};
//        ResponseEntity<SISResponseDto<DispatchScheduleDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
//        if (response.getBody().getCodestatus().equalsIgnoreCase("e")) {
//            throw new Exception(response.getBody().getMessage());
//        }
//
//        return response.getBody();
//    }

    
}