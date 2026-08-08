package id.sis.service.accounting.businessprocess;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field86;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import id.sis.service.accounting.properties.SISApiProperties;
import id.sis.service.accounting.properties.SISIdProperties;
import id.sis.service.accounting.response.SISResponse;
import id.sis.service.accounting.utils.SISConstants;
import id.sis.service.accounting.utils.SISUtil;
import id.sis.service.accounting.utils.SIS_BisproUtils;
import id.sis.service.accounting.utils.SIS_FleetReportParser;
import id.sis.service.accounting.utils.SIS_FleetReportParser.FleetTransaction;

@Component
public class SISGlobalExecute {
	private final static Logger logger = LoggerFactory.getLogger(SISGlobalExecute.class);
	List<Map<String, Object>> listData = new ArrayList<>();
	SIS_BisproUtils bu = new SIS_BisproUtils();
	SISUtil u = new SISUtil(); 
	String sql = "";
	BigDecimal docCount = SISUtil.getBigDecimal(u.getRefNoTime());
	
	@Autowired
	@Qualifier("jdbcTemplateSource")
	private JdbcTemplate source;

	@Autowired
	private SISApiProperties sisApiProperties;
	
	@Autowired
	private SISIdProperties sisIdProperties;
	
	@Autowired
    private PlatformTransactionManager transactionManager;

//	@Transactional(rollbackFor = Exception.class)
//	public SISResponse executeFWspCreateMaterialEDX(String name) throws Exception {
//		SISResponse response = new SISResponse();
//
//		try {
//			String edxIdMaterial = UUID.randomUUID().toString().toUpperCase();
//			String itemIdMaterial = UUID.randomUUID().toString().toUpperCase();
//			String sqlMaterial = "exec FWsp_CreateMATERIALEDX " +
//				"@EDXID = '" + edxIdMaterial + "'" +
//				", @ItemID = '" + itemIdMaterial + "'" +
//				", @Item_Code = 'RM-22 " + name + "'" +
//				", @Description = 'RM-22 " + name + "'" +
//				", @Internal_UOM = 'kg'" +
//				", @Item_Group_Code = '1 AGG'" +
//				", @CompanyID = '" + sisIdProperties.getCompanyid() + "'" +
//				", @LocationID = '" + sisIdProperties.getLocationid() + "'" +
//				", @PlantID = '" + sisIdProperties.getPlantid() + "'" +
//				", @Item_Group_type = 'T'"; 
//			source.execute(sqlMaterial);
//			logger.info("material edx created");
//
//			String edxIdMix = UUID.randomUUID().toString().toUpperCase();
//			String itemIdMix = UUID.randomUUID().toString().toUpperCase();
//			String sqlMix = "exec FWsp_CreateMIXEDX " +
//				"@EDXID = '" + edxIdMix + "'" +
//				", @ItemID = '" + itemIdMix + "'" +
//				", @Item_Code = 'RMX-K22 " + name + "'" +
//				", @Description = 'RMX-K22 " + name + "'" +
//				", @Internal_UOM = 'm3'" +
//				", @CompanyID  = '" + sisIdProperties.getCompanyid() + "'" +
//				", @LocationID = '" + sisIdProperties.getLocationid() + "'" +
//				", @PlantID    = '" + sisIdProperties.getPlantid() + "'" +
//				", @Max_Batch_Size_UOM = 'm3'" +
//				", @ItemType = 'M'" +
//				", @Track_Usage_Flag = 1" +
//				", @Trade_Discount_Flag = 0";
//			source.execute(sqlMix);
//			logger.info("mix edx created");
//
//			String sqlMixIngre = "exec FWsp_CreateMIXIngredientEDX " +
//				"@MixEDXID = '" + edxIdMix + "'" +
//				", @IngredItemID = '" + itemIdMaterial + "'" +
//				", @MixCode = ''" +
//				", @IngredCode= ''" +
//				", @LocationID = '" + sisIdProperties.getLocationid() + "'" +
//				", @Entry_Qty = 15000" +
//				", @Entry_UOM = 'kg'" +
//				", @Item_Type = 'M'" +
//				", @Based_On_Qty = 17";
//			source.execute(sqlMixIngre);
//			logger.info("mix ingredient line created");
//
//			String orderId = UUID.randomUUID().toString();
//			String sqlOrder = "exec FWsp_CreateOrderEDX " +
//				"@EDXID = '" + edxIdMix + "'" +
//				", @CompanyID  = '" + sisIdProperties.getCompanyid() + "'" +
//				", @LocationID = '" + sisIdProperties.getLocationid() + "'" +
//				", @Order_Code = 'STO-2024.C'" +
//				", @OrderID = '" + orderId + "'" +
//				", @CustomerID = 'B6C005F2-8A28-4E05-9DE1-7C049889D176'" +
//				", @PO_Num = 'PO-2024'" +
//				", @Address_Line1 = 'Jl. Melati No.1'" +
//				", @Address_Line2 = 'Kec.Manyar, Kab.Gresik'" +
//				", @Address_Line3 = 'Jawa Timur'" +
//				", @Req_First_Load_On_Job_TDS = '2024-03-04'";
//			source.execute(sqlOrder);
//
//			String orderLineId = UUID.randomUUID().toString();
//			String sqlOrderLine = "exec FWsp_CreateOrderLineEDX " +
//				"@Order_LineID = '" + orderLineId + "'" +
//				", @OrderID = '" + orderId + "'" +
//				", @ItemID = '" + itemIdMix + "'" +
//				", @Load_Size = '6'" +
//				", @Ordered_Qty = '6'" +
//				", @Ordered_Qty_UOM = 'm3'" +
//				", @Sort_Line_Num='0'";
//			source.execute(sqlOrderLine);
//
//			response.setStatus("S");
//			response.setMessage("Material RM-22 " + name + " created");
//			logger.info("Create material Success");
//		} catch (Exception e) {
//			response.setStatus("E");
//			response.setMessage("Create material Failed: " + e.getMessage());
//			logger.info(e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//
//		return response;
//	}
//
//	
//
//	@Transactional(rollbackOn = Exception.class)
	public SISResponse processMT940() throws Exception {
		u = new SISUtil(source, sisApiProperties, transactionManager);
		logger.info("[SIS] processMT940");
		SISResponse response = new SISResponse();
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			List<Integer> listBSID = new ArrayList<>();
	        List<String> listErr = new ArrayList<>();
	        List<Integer> listID = u.execDir(listErr, sisApiProperties.getDirectory(), dirs -> {
	        	return readMT940(dirs);
			});
	        Set<Integer> uniqueSet = new HashSet<>(listID);
	        listBSID = new ArrayList<>(uniqueSet);
	        Map<String, Object> map = new LinkedHashMap<String, Object>();
	        map.put("list_bankstatement_id", listBSID);
	        map.put("list_error", listErr);
	        resultList.add(map);
			response = SISResponse.successResponse(resultList);
			logger.info(listErr.toString());
		} catch (Exception e) {
			response = SISResponse.errorResponse(e.getMessage());
		}
		return response;
	}
	
	List<Integer> readMT940(
			String filePath
			) throws Exception{
		u = new SISUtil(source, sisApiProperties, transactionManager);
		
		String mt940Text = "";
		try {
			mt940Text = Files.readString(Paths.get(filePath));
		} catch (Exception e) {
			mt940Text = Files.readString(
				    Paths.get(filePath),
				    StandardCharsets.ISO_8859_1
				);
		}
		
		String[] rawMessages = mt940Text.split("-\\}");

        List<MT940> listMT940 = new ArrayList<>();

        for (String raw : rawMessages) {

            if (raw.trim().isEmpty()) continue;

            String msgText = raw + "-}";

            SwiftParser parser = new SwiftParser(msgText);
            SwiftMessage msg = parser.message();

            if (msg != null && "940".equals(msg.getType())) {
            	listMT940.add(new MT940(msg));
            }
        }
        
        List<Integer> listBSID = new ArrayList<Integer>();
        for (MT940 mt940: listMT940) {
        	String openingBalanceAmount = mt940.getField60F().getAmount().replace(",", ".");
	        String accountNo = mt940.getField25().getAccount();
	        String closingBalanceDate = mt940.getField62F().getDate();
	        String closingBalanceAmount = mt940.getField62F().getAmount().replace(",", ".");
	        List<String> listDesc = new ArrayList<>();
	        for (Field86 f86: mt940.getField86()) {
	       	 listDesc.add(f86.getValue());
	        }
	        int c_bankaccount_id = u.getBankAccountID(accountNo);
	        if (c_bankaccount_id <= 0) {
	        	throw new Exception("Bank Account "+accountNo+" not found!");
	        }
	        BigDecimal mt940BeginAmt = SISUtil.getBigDecimal(openingBalanceAmount);
	        BigDecimal mt940EndAmt = SISUtil.getBigDecimal(closingBalanceAmount);
	        BigDecimal diffAmt = mt940EndAmt.subtract(mt940BeginAmt);
	        
	        // Iterate through transactions (Field 61)
	        int count = 0;
	        BigDecimal totalAmt = new BigDecimal(0);
	        String docno = u.getRefNoTime();
	        List<Field61> listTrans = mt940.getField61();
	        System.out.println(listTrans.size());
	    	for (Field61 transaction : listTrans) {
	    		count += 1;
	            String transactionDate = transaction.getDate();
	            String transactionAmount = transaction.getAmount().replace(",", "");
	            String transactionType = transaction.getTransactionType();
	            String debitCreditMark = transaction.getDebitCreditMark();
	            
	            String desc = listDesc.get(count-1);
	            Timestamp ts = SISUtil.getDateyyMMdd(transactionDate);
	        	BigDecimal amt = SISUtil.getBigDecimal(transactionAmount);
	        	if (debitCreditMark.equalsIgnoreCase("D")) {
	        		amt = amt.negate();
	        	}
	        	totalAmt = totalAmt.add(amt);
	        	Timestamp now = u.getCurrentTime();
	        	u.generateBS(
	        			listBSID, 
	        			ts, 
	        			c_bankaccount_id, 
	        			diffAmt, 
	        			docno, 
	        			desc, 
	        			mt940BeginAmt, 
	        			mt940EndAmt, 
	        			now, 
	        			amt, 
	        			count,
	        			0,
	        			0,
	        			false
				);
	        }
        }
        return listBSID;
    }
	
	public SISResponse processFleetReport() throws Exception {
		u = new SISUtil(source, sisApiProperties, transactionManager);
		logger.info("[SIS] processFleetReport");
		SISResponse response = new SISResponse();
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			List<Integer> listBSID = new ArrayList<>();
	        List<String> listErr = new ArrayList<>();
	        List<Integer> listID = u.execDir(listErr, sisApiProperties.getDirectory_fleet(), dirs -> {
	        	return readFleetReport(dirs);
			});
	        Set<Integer> uniqueSet = new HashSet<>(listID);
	        listBSID = new ArrayList<>(uniqueSet);
	        Map<String, Object> map = new LinkedHashMap<String, Object>();
	        map.put("list_bankstatement_id", listBSID);
	        map.put("list_error", listErr);
	        resultList.add(map);
			response = SISResponse.successResponse(resultList);
			logger.info(listErr.toString());
		} catch (Exception e) {
			response = SISResponse.errorResponse(e.getMessage());
		}
		return response;
	}
	
	List<Integer> readFleetReport(
			String filePath
			) throws Exception{
		u = new SISUtil(source, sisApiProperties, transactionManager);
		
		List<FleetTransaction> listFleet = SIS_FleetReportParser.parse(Path.of(filePath));
		
		HashMap<String, BigDecimal> mapBalance = new HashMap<>();
		HashMap<String, BigDecimal> mapEnd = new HashMap<>();
		HashMap<Integer, BigDecimal> mapTotal = new HashMap<>();
        List<Integer> listBSID = new ArrayList<Integer>();
        for (FleetTransaction fleet: listFleet) {
        	String accountNo = fleet.getNoKartu();
        	int c_bankaccount_id = u.getBankAccountID(accountNo);
        	if (c_bankaccount_id <= 0) {
	        	throw new Exception("Bank Account "+accountNo+" not found!");
	        }
        	BigDecimal amt = fleet.getNominal().negate();
	        String dates = SISUtil.getStringDate(fleet.getTimestamp());
	        String key = accountNo + ";" + dates;
	        if (!mapTotal.containsKey(c_bankaccount_id)) {
	        	mapTotal.put(c_bankaccount_id, u.getCurrentBalance(c_bankaccount_id));
	        }
	        if (!mapEnd.containsKey(key)) {
	        	mapEnd.put(key, mapTotal.get(c_bankaccount_id));
	        }
	        if (!mapBalance.containsKey(key)) {
	        	mapBalance.put(key, BigDecimal.ZERO);
	        }
	        mapTotal.put(c_bankaccount_id, mapTotal.get(c_bankaccount_id).add(amt));
	        mapEnd.put(key, mapEnd.get(key).add(amt));
	        mapBalance.put(key, mapBalance.get(key).add(amt));
	    }
        
        int count = 0;
        for (FleetTransaction fleet: listFleet) {
        	BigDecimal amt = fleet.getNominal().negate();
	        String accountNo = fleet.getNoKartu();
        	int c_bankaccount_id = u.getBankAccountID(accountNo);
	        if (c_bankaccount_id <= 0) {
	        	throw new Exception("Bank Account "+accountNo+" not found!");
	        }
	        String dates = SISUtil.getStringDate(fleet.getTimestamp());
	        String key = accountNo + ";" + dates;
	        
	        BigDecimal endAmt = mapEnd.get(key);
	        BigDecimal diffAmt = mapBalance.get(key);
            BigDecimal beginAmt = endAmt.add(diffAmt);
            
            String docno = u.getRefNoTime();
            count += 1;
            Timestamp ts = fleet.getTimestamp();
            String desc = SISUtil.getStringDateTime(ts)+" "+fleet.getTerminal();
            Timestamp now = u.getCurrentTime();
        	int c_doctype_id = u.getIntSysconfig(SISConstants.SIS_DEFAULT_DOC_TYPE_FLEET_ID, true);
        	int c_charge_id = u.getIntSysconfig(SISConstants.SIS_FLEET_CHARGE_ID, true);
        	Object oCh = u.getObject("c_doctype", "c_doctype_id", "sis_fleetcharge_id", c_doctype_id);
        	if (oCh != null) {
        		c_charge_id = (int)oCh;
        	}
        	ts = SISUtil.removeTime(ts);
        	u.generateBS(
        			listBSID, 
        			ts, 
        			c_bankaccount_id, 
        			diffAmt, 
        			docno, 
        			desc, 
        			beginAmt, 
        			endAmt, 
        			now, 
        			amt, 
        			count,
        			c_doctype_id,
        			c_charge_id,
        			true
			);
        }
        
        
        return listBSID;
    }
	
}