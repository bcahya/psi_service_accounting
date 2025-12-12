package id.sis.service.accounting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import id.sis.service.accounting.pojo.RB_InventoryCharge;
import id.sis.service.accounting.pojo.RB_MO;
import id.sis.service.accounting.pojo.RB_Req;
import id.sis.service.accounting.response.SISResponse;
import id.sis.service.accounting.service.SISServiceImpl;

@Controller
@Configuration
public class SISController {

	@Autowired
	SISServiceImpl sisService;

	@ResponseBody
	@RequestMapping(value = "/test")
	SISResponse test() {
		SISResponse response = new SISResponse();
		try {
			response.setStatus("S");
			response.setMessage("Test Sukses!");
		} catch (Exception e) {
			response.setStatus("E");
			response.setMessage("Test Failed: " + e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/processMT940", method = RequestMethod.POST)
	SISResponse processMT940(
//			@RequestParam("m_inventory_id") Integer m_inventory_id
			) {
		SISResponse response = new SISResponse();

		try {
			response = sisService.processMT940();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
}