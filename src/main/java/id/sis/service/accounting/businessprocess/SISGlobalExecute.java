package id.sis.service.accounting.businessprocess;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
	        map.put("list_data_id", listBSID);
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
		int chargeTolID = u.getIntSysconfig(SISConstants.SIS_FLEET_CHARGE_TOL_ID, true);
    	int chargeBBMID = u.getIntSysconfig(SISConstants.SIS_FLEET_CHARGE_BBM_ID, true);
    	int pricelistID = u.getIntSysconfig(SISConstants.SIS_FLEET_PRICE_LIST_ID, true);
    	int taxID = u.getIntSysconfig(SISConstants.SIS_FLEET_TAX_ID, true);
    	int paymentTermID = u.getIntSysconfig(SISConstants.SIS_FLEET_PAYMENT_TERM_ID, true);
    	int dtID = u.getIntSysconfig(SISConstants.SIS_FLEET_DEFAULT_DOC_TYPE_ID, true);
    	int currencyID = u.getIntSysconfig(SISConstants.SIS_FLEET_CURRENCY_ID, true);
    	int userID = u.getIntSysconfig(SISConstants.SIS_FLEET_USER_ID, true);
    	
		List<FleetTransaction> listFleet = SIS_FleetReportParser.parse(Path.of(filePath));
		
		LinkedHashMap<String, Integer> mapBA = new LinkedHashMap<>();
		LinkedHashMap<String, LinkedHashMap<String, Object>> mapFleet = new LinkedHashMap<>();
		List<Integer> listBSID = new ArrayList<Integer>();
		String del = ";";
    	for (FleetTransaction fleet: listFleet) {
        	String accountNo = fleet.getNoKartu();
        	int c_bankaccount_id = 0;
        	if (!mapBA.containsKey(accountNo)) {
	        	c_bankaccount_id = u.getBankAccountID(accountNo);
	        	if (c_bankaccount_id <= 0) {
		        	throw new Exception("Bank Account "+accountNo+" not found!");
		        }
        	} else {
        		c_bankaccount_id = mapBA.get(accountNo);
        	}
        		
        	BigDecimal amt = fleet.getNominal();
	        String dates = SISUtil.getStringDate(fleet.getTimestamp());
	        boolean isBBM = fleet.getTerminal().toLowerCase().contains("spbu");
	        String key = c_bankaccount_id + del + dates;
	        if (!mapFleet.containsKey(key)) {
	        	LinkedHashMap<String, Object> mapDetail = new LinkedHashMap<>();
	        	mapDetail.put("bbm", BigDecimal.ZERO);
	        	mapDetail.put("tol", BigDecimal.ZERO);
	        	mapDetail.put("total", BigDecimal.ZERO);
	        	mapDetail.put("desc", "");
	        	mapDetail.put("desc_bbm", "");
	        	mapDetail.put("desc_tol", "");
	        	mapFleet.put(key, mapDetail);
	        }
	        LinkedHashMap<String, Object> mapDetail = mapFleet.get(key);
	        String desc = String.valueOf(mapDetail.get("desc"));
	        if (!SISUtil.cekIsNull(desc)) {
	        	desc += ", ";
	        }
	        desc += fleet.getTerminal();
	        mapDetail.put("desc", desc);
	        mapDetail.put("total", SISUtil.getBigDecimal(mapDetail.get("total")).add(amt));
        	if (isBBM) {
	        	mapDetail.put("bbm", SISUtil.getBigDecimal(mapDetail.get("bbm")).add(amt));
	        	desc = String.valueOf(mapDetail.get("desc_bbm"));
		        if (!SISUtil.cekIsNull(desc)) {
		        	desc += ", ";
		        }
		        desc += fleet.getTerminal();
		        mapDetail.put("desc_bbm", desc);
	        } else {
	        	mapDetail.put("tol", SISUtil.getBigDecimal(mapDetail.get("tol")).add(amt));
	        	desc = String.valueOf(mapDetail.get("desc_tol"));
		        if (!SISUtil.cekIsNull(desc)) {
		        	desc += ", ";
		        }
		        desc += fleet.getTerminal();
		        mapDetail.put("desc_tol", desc);
	        }
	    }
        
        int count = 0;
        List<String> colInvs = List.of(
        	    "c_invoice_id",
        	    "ad_client_id",
        	    "ad_org_id",
        	    "documentno",
        	    "sis_status_docno",
        	    "c_doctype_id",
        	    "c_doctypetarget_id",
        	    "dateinvoiced",
        	    "dateacct",
        	    "c_bpartner_id",
        	    "c_bpartner_location_id",
        	    "ad_user_id",
        	    "m_pricelist_id",
        	    "c_currency_id",
        	    "salesrep_id",
        	    "paymentrule",
        	    "c_paymentterm_id",
        	    "c_tax_id",
        	    "totallines",
        	    "grandtotal",
        	    "docstatus",
        	    "docaction",
        	    "c_bankaccount_id",
        	    "c_invoice_uu",
        	    "ispaid",
        	    "isindispute",
        	    "isactive",
        	    "created",
        	    "updated",
        	    "createdby",
        	    "updatedby",
        	    "issotrx",
        	    "c_costcenter_id"
        	);
        List<String> colInvLines = List.of(
        		"ad_client_id",
        	    "ad_org_id",
        	    "isactive",
        	    "created",
        	    "updated",
        	    "createdby",
        	    "updatedby",
        	    "c_invoiceline_id",
        	    "c_invoice_id",
        	    "line",
        	    "c_charge_id",
        	    "description",
        	    "qtyentered",
        	    "qtyinvoiced",
        	    "c_uom_id",
        	    "priceentered",
        	    "priceactual",
        	    "c_tax_id",
        	    "pricelist",
        	    "taxamt",
        	    "linenetamt",
        	    "linetotalamt",
        	    "c_invoiceline_uu",
        	    "c_costcenter_id"
        	);
        for (String key: mapFleet.keySet()) {
        	LinkedHashMap<String, Object> mapDetail = mapFleet.get(key);
        	String[] keys = key.split(del);
        	int c_bankaccount_id = Integer.valueOf(keys[0]);
        	String dates = keys[1];
        	int c_costcenter_id = u.getIntFromObject("c_bankaccount", "c_bankaccount_id", "c_costcenter_id", c_bankaccount_id, true);
        	String costcenterValue = u.getStringFromObject("c_costcenter", "c_costcenter_id", "value", c_costcenter_id, true);
        	int c_bpartner_id = u.getIntFromObject("c_bpartner", "value", "c_bpartner_id", costcenterValue, true);
        	int c_bpartner_location_id = u.getIntFromObject("c_bpartner_location", "c_bpartner_id", "c_bpartner_location_id", c_bpartner_id, true);
        	Timestamp now = new Timestamp(new Date().getTime());
        	//cek existing data
        	String sql = 
        			"select "
        			+ "	i.documentno "
        			+ "from c_invoice i "
        			+ "where i.docstatus not in ('VO','RE','NA') "
        			+ "and i.c_doctypetarget_id = "+dtID+" "
        			+ "and i.c_bankaccount_id = "+c_bankaccount_id+" "
        			+ "and i.dateinvoiced = '"+dates+"'::date "
        			+ "and i.issotrx = 'N' "
        			+ "and i.isactive = 'Y' "
        			+ "fetch first 1 rows only "
        	        ;
        	String docExists = "";
    		List<Map<String, Object>> resultList = source.queryForList(sql);
    		if (!resultList.isEmpty()) {
    			for (Map<String, Object> map: resultList) {
    				docExists = (String)map.get("documentno");
    				break;
    			}
    		}
    		if (!SISUtil.cekIsNull(docExists)) {
    			throw new Exception("Invoice "+docExists +" already create for this fleet ("+key+")!");
    		}
    		
    		//generate invoice
    		String docno = u.getRefNoTime()+SISUtil.addZero(count, 4);
    		int c_invoice_id = u.getNextSysID("C_Invoice");
    		sql = "insert into c_invoice ( ";
    		for (int i = 0; i < colInvs.size(); i++) {
    			if (i > 0) {
    				sql += ",";
    			}
    			sql += colInvs.get(i);
    		}
    		sql += ") values (";
    		for (int i = 0; i < colInvs.size(); i++) {
    			if (i > 0) {
    				sql += ",";
    			}
    			sql += "?";
    		}
			sql += ") ";
            int rowsAffected = source.update(
                    sql, 
                    c_invoice_id, 
                    sisApiProperties.getAd_client_id(), 
                    sisApiProperties.getAd_org_id(), 
                    docno,
                    "R",
                    dtID,
                    dtID,
                    SISUtil.getDate(dates),
                    SISUtil.getDate(dates),
                    c_bpartner_id,
                    c_bpartner_location_id,
                    userID,
                    pricelistID,
                    currencyID,
                    userID,
                    "P",
                    paymentTermID,
                    taxID,
                    mapDetail.get("total"),
                    mapDetail.get("total"),
                    "DR",
                    "CO",
                    c_bankaccount_id,
                    UUID.randomUUID(),
                    "N",
                    "N",
                    "Y",
                    now,
                    now,
                    userID,
                    userID,
                    "N",
                    c_costcenter_id
                );
            
            int line = 0;
			if (mapDetail.containsKey("bbm") && SISUtil.getBigDecimal(mapDetail.get("bbm")).signum() > 0) {
				line += 10;
				generateInvLineFleet(colInvLines, mapDetail, now, "bbm", userID, c_invoice_id, line, chargeBBMID,
						taxID, c_costcenter_id);
			}
			if (mapDetail.containsKey("tol") && SISUtil.getBigDecimal(mapDetail.get("tol")).signum() > 0) {
				line += 10;
				generateInvLineFleet(colInvLines, mapDetail, now, "tol", userID, c_invoice_id, line, chargeTolID,
						taxID, c_costcenter_id);
			}

			listBSID.add(c_invoice_id);
    		count +=1;
        }
        
        return listBSID;
    }
	
	public SISResponse processFleetBT() throws Exception {
		u = new SISUtil(source, sisApiProperties, transactionManager);
		logger.info("[SIS] processFleetBT");
		SISResponse response = new SISResponse();
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			List<Integer> listBSID = new ArrayList<>();
	        List<String> listErr = new ArrayList<>();
	        List<Integer> listID = u.execDir(listErr, sisApiProperties.getDirectory_fleetbt(), dirs -> {
	        	return readFleetBT(dirs);
			});
	        Set<Integer> uniqueSet = new HashSet<>(listID);
	        listBSID = new ArrayList<>(uniqueSet);
	        Map<String, Object> map = new LinkedHashMap<String, Object>();
	        map.put("list_data_id", listBSID);
	        map.put("list_error", listErr);
	        resultList.add(map);
			response = SISResponse.successResponse(resultList);
			logger.info(listErr.toString());
		} catch (Exception e) {
			response = SISResponse.errorResponse(e.getMessage());
		}
		return response;
	}
	
	List<Integer> readFleetBT(
			String filePath
			) throws Exception{
		u = new SISUtil(source, sisApiProperties, transactionManager);
		int dtID = u.getIntSysconfig(SISConstants.SIS_FLEET_BT_DOCTYPE_ID, true);
		int baFromID = u.getIntSysconfig(SISConstants.SIS_FLEET_BT_BANKACCOUNT_ID, true);
    	int currencyID = u.getIntSysconfig(SISConstants.SIS_FLEET_CURRENCY_ID, true);
    	int userID = u.getIntSysconfig(SISConstants.SIS_FLEET_USER_ID, true);
    	int orgFromID = u.getIntFromObject("c_banktransfer", "c_banktransfer_id", "ad_org_id", baFromID, true);	
    	
    	Timestamp now = u.getCurrentTime();
    	List<String> colBTs = List.of(
        		"ad_client_id",
        	    "ad_org_id",
        	    "isactive",
        	    "created",
        	    "updated",
        	    "createdby",
        	    "updatedby",
        	    "c_banktransfer_id",
        	    "c_doctype_id",
        	    "documentno",
        	    "description",
        	    "paydate",
        	    "dateacct",
        	    "from_c_bankaccount_id",
        	    "from_ad_org_id",
        	    "from_c_currency_id",
        	    "from_amt",
        	    "to_c_bankaccount_id",
        	    "to_ad_org_id",
        	    "to_c_currency_id",
        	    "to_amt",
        	    "docstatus",
        	    "docaction",
        	    "processed"
        	);
    	
		List<FleetTransaction> listFleet = SIS_FleetReportParser.parse(Path.of(filePath));
		
		LinkedHashMap<String, Integer> mapBA = new LinkedHashMap<>();
		List<Integer> listBSID = new ArrayList<Integer>();
		int count = 0;
        for (FleetTransaction fleet: listFleet) {
        	String accountNo = fleet.getNoKartu();
        	int c_bankaccount_id = 0;
        	if (!mapBA.containsKey(accountNo)) {
	        	c_bankaccount_id = u.getBankAccountID(accountNo);
	        	if (c_bankaccount_id <= 0) {
		        	throw new Exception("Bank Account "+accountNo+" not found!");
		        }
        	} else {
        		c_bankaccount_id = mapBA.get(accountNo);
        	}
        	int ad_org_id = u.getIntFromObject("c_banktransfer", "c_banktransfer_id", "ad_org_id", c_bankaccount_id, true);	
        	BigDecimal amt = fleet.getNominal();
	        String dates = SISUtil.getStringDate(fleet.getTimestamp());
	        String desc = fleet.getTerminal();
	        
	        //generate BT
    		String docno = u.getRefNoTime()+SISUtil.addZero(count, 4);
    		int c_banktransfer_id = u.getNextSysID("C_BankTransfer");
    		sql = "insert into c_banktransfer ( ";
    		for (int i = 0; i < colBTs.size(); i++) {
    			if (i > 0) {
    				sql += ",";
    			}
    			sql += colBTs.get(i);
    		}
    		sql += ") values (";
    		for (int i = 0; i < colBTs.size(); i++) {
    			if (i > 0) {
    				sql += ",";
    			}
    			sql += "?";
    		}
			sql += ") ";
            int rowsAffected = source.update(
                    sql, 
                    sisApiProperties.getAd_client_id(), 
                    ad_org_id, 
                    "Y",
                    now,
                    now,
                    userID,
                    userID,
                    c_banktransfer_id,
                    dtID,
                    docno,
                    desc,
                    userID,
                    SISUtil.getDate(dates),
                    SISUtil.getDate(dates),
                    baFromID,
                    orgFromID,
                    currencyID,
                    amt.abs(),
                    c_bankaccount_id,
                    ad_org_id,
                    currencyID,
                    amt.abs(),
                    "DR",
                    "CO",
                    "N"
                );
            listBSID.add(c_banktransfer_id);
        	count +=1;
	    }
        
        return listBSID;
    }
	
	int generateInvLineFleet(
			List<String> colInvLines,
			LinkedHashMap<String, Object> mapDetail,
			Timestamp now,
			String type,
			int userID,
			int c_invoice_id,
			int line,
			int chargeID,
			int taxID,
			int c_costcenter_id
			) {
		int c_invoiceline_id = u.getNextSysID("C_InvoiceLine");
		sql = "insert into c_invoiceline ( ";
		for (int i = 0; i < colInvLines.size(); i++) {
			if (i > 0) {
				sql += ",";
			}
			sql += colInvLines.get(i);
		}
		sql += ") values (";
		for (int i = 0; i < colInvLines.size(); i++) {
			if (i > 0) {
				sql += ",";
			}
			sql += "?";
		}
		sql += ") ";
        int rowsAffected = source.update(
                sql, 
                sisApiProperties.getAd_client_id(), 
                sisApiProperties.getAd_org_id(), 
                "Y",
                now,
                now,
                userID,
                userID,
                c_invoiceline_id,
                c_invoice_id, 
                line,
                chargeID,
                mapDetail.get("desc_"+type),
                BigDecimal.ONE,
                BigDecimal.ONE,
                100,
                mapDetail.get(type),
                mapDetail.get(type),
                taxID,
                mapDetail.get(type),
                BigDecimal.ZERO,
                mapDetail.get(type),
                mapDetail.get(type),
                UUID.randomUUID(),
                c_costcenter_id
        );
        return c_invoiceline_id;
	}
	
}