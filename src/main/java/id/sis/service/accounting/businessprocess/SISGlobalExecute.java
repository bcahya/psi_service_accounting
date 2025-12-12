package id.sis.service.accounting.businessprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field86;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import id.sis.service.accounting.properties.SISApiProperties;
import id.sis.service.accounting.response.SISResponse;
import id.sis.service.accounting.utils.SISUtil;
import id.sis.service.accounting.utils.SIS_BisproUtils;

@Component
public class SISGlobalExecute {
	private final static Logger logger = LoggerFactory.getLogger(SISGlobalExecute.class);
	List<Map<String, Object>> listData = new ArrayList<>();
	SIS_BisproUtils bu = new SIS_BisproUtils();
	SISUtil u = new SISUtil(); 

	@Autowired
	@Qualifier("jdbcTemplateCdcSource")
	private JdbcTemplate source;

	@Autowired
	private SISApiProperties sisApiProperties;
	
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
		logger.info("[SIS] processMT940");
		SISResponse response = new SISResponse();
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			String dirPath = sisApiProperties.getDirectory();
			if (SISUtil.cekIsNull(dirPath)) {
	    		throw new Exception("Please SFTP Directory Local!");
	    	}
			
			File dir = new File(dirPath);
	        File[] files = dir.listFiles();
	        if (files.length == 0) {
	        	return SISResponse.successResponse(resultList);
	        }
	        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

	        List<Integer> listBSID = new ArrayList<>();
	        List<String> listErr = new ArrayList<>();
	        for (File file: files) {
	        	if (file.isDirectory()) {
	        		continue;
	        	}
	        	String filePath = dirPath+file.getName();
	        	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    		TransactionStatus status = transactionManager.getTransaction(def);
	    		try {
	        		List<Integer> listMT = readMT940(filePath);
	        		listBSID.addAll(listMT);
	        		transactionManager.commit(status);
	        		
	        		//move file to done
			        String dirDone = dirPath+"done/";
			        Path donePath = Paths.get(dirDone);
			        Files.createDirectories(donePath);
			        Path sourcePath = Paths.get(filePath);
			        Path targetPath = Paths.get(dirDone+file.getName());
			        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			        
	        	} catch (Exception e) {
	        		listErr.add("filename "+file.getName()+" - "+e.getMessage());
	        		transactionManager.rollback(status);
				}
	        }
			
	        Map<String, Object> map = new LinkedHashMap<String, Object>();
	        map.put("list_bankstatement_id", listBSID);
	        map.put("list_error", listErr);
	        resultList.add(map);
			response = SISResponse.successResponse(resultList);
		} catch (Exception e) {
			response = SISResponse.errorResponse(e.getMessage());
		}
		return response;
	}
	

//	@Transactional(rollbackOn = Exception.class)
	List<Integer> readMT940(
			String filePath
			) throws Exception{
		String sql = "";
    	InputStream is;
		MT940 mt940 = null;
		is = new FileInputStream(filePath);
		mt940 = new MT940(is);
     // Get the statement number
        String statementNumber = mt940.getField28C().getSequenceNumber();

        // Get the opening balance
        String openingBalanceDate = mt940.getField60F().getDate();
        String openingBalanceCurrency = mt940.getField60F().getCurrency();
        String openingBalanceAmount = mt940.getField60F().getAmount().replace(",", ".");
        String accountNo = mt940.getField25().getAccount();
        String closingBalanceDate = mt940.getField62F().getDate();
        String closingBalanceAmount = mt940.getField62F().getAmount().replace(",", ".");
        List<String> listDesc = new ArrayList<>();
        for (Field86 f86: mt940.getField86()) {
       	 listDesc.add(f86.getValue());
        }
        int c_bankaccount_id = getBankAccountID(accountNo);
        if (c_bankaccount_id <= 0) {
        	throw new Exception("Bank Account "+accountNo+" not found!");
        }
        BigDecimal mt940BeginAmt = SISUtil.getBigDecimal(openingBalanceAmount);
        BigDecimal mt940EndAmt = SISUtil.getBigDecimal(closingBalanceAmount);
        BigDecimal diffAmt = mt940EndAmt.subtract(mt940BeginAmt);
        
        // Iterate through transactions (Field 61)
        int count = 0;
        int c_bankstatement_id = 0;
        List<Integer> listBSID = new ArrayList<Integer>();
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
            if (count == 1) {
            	c_bankstatement_id = getBankStatementID(ts, c_bankaccount_id);
            	BigDecimal beginAmt = SISUtil.getBigDecimal(getObject("c_bankaccount", "c_bankaccount_id", "currentbalance", c_bankaccount_id));
            	BigDecimal endAmt = beginAmt.add(diffAmt);
            	if (c_bankstatement_id <= 0) {
            		c_bankstatement_id = getNextSysID("C_BankStatement");
	            	sql =
                		"insert into c_bankstatement ( "
                		+ "	c_bankstatement_id, "
                		+ "	ad_client_id, "
                		+ "	ad_org_id, "
                		+ "	c_bankaccount_id, "
                		+ "	documentno, "
                		+ "	name, "
                		+ "	statementdate, "
                		+ "	dateacct, "
                		+ "	description, "
                		+ "	ismanual, "
                		+ "	beginningbalance, "
                		+ "	statementdifference, "
                		+ "	endingbalance, "
                		+ "	docstatus, "
                		+ "	docaction, "
                		+ "	sis_status_docno, "
                		+ "	c_bankstatement_uu, "
                		+ " sis_mt940beginamt, "
                		+ "	sis_mt940endamt, "
                		+ "	isactive, "
                		+ "	created, "
                		+ "	updated, "
                		+ "	createdby, "
                		+ "	updatedby, "
                		+ "	c_doctype_id, "
                		+ "	createfrombatch "
                		+ ") values( "
                		+ "	?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? "
                		+ ") ";
                    int rowsAffected = source.update(
                            sql, 
                            c_bankstatement_id, 
                            sisApiProperties.getAd_client_id(), 
                            sisApiProperties.getAd_org_id(), 
                            c_bankaccount_id,
                            docno,
                            docno,
                            ts,
                            ts,
                            "Auto Generated from MT940",
                            "N",
                            beginAmt,
                            diffAmt,
                            endAmt,
                            "DR",
                            "CO",
                            "R",
                            UUID.randomUUID(),
                            mt940BeginAmt,
                            mt940EndAmt,
                            "Y",
                            now,
                            now,
                            sisApiProperties.getAd_user_id(),
                            sisApiProperties.getAd_user_id(),
                            sisApiProperties.getC_doctype_id(),
                            "N"
                        );
            	}	
                listBSID.add(c_bankstatement_id);
            }

            int c_bankstatementline_id = getBankStatementLineID(c_bankaccount_id, SISUtil.getStringDate(ts), desc.replace("'", "''"), amt);
            if (c_bankstatementline_id > 0) {
            	continue;
            }
            c_bankstatementline_id = getNextSysID("C_BankStatementLine");
            sql =
        		"insert into c_bankstatementline ( "
        		+ "	ad_client_id, "
        		+ "	ad_org_id, "
        		+ "	c_bankstatementline_id, "
        		+ "	c_bankstatement_id, "
        		+ "	line, "
        		+ "	description, "
        		+ "	isactive, "
        		+ "	ismanual, "
        		+ "	statementlinedate, "
        		+ "	dateacct, "
        		+ "	valutadate, "
        		+ "	c_currency_id, "
        		+ "	stmtamt, "
        		+ "	trxamt, "
        		+ "	chargeamt, "
        		+ "	c_charge_id, "
        		+ "	interestamt, "
        		+ "	created, "
        		+ "	updated, "
        		+ "	createdby, "
        		+ "	updatedby, "
        		+ "	c_bankstatementline_uu "
        		+ ") values ( "
        		+ "	?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? "
        		+ ") ";
            int rowsAffected = source.update(
                    sql, 
                    sisApiProperties.getAd_client_id(), 
                    sisApiProperties.getAd_org_id(), 
                    c_bankstatementline_id,
                    c_bankstatement_id,
                    (count) * 10,
                    desc,
                    "Y",
                    "N",
                    ts,
                    ts,
                    ts,
                    sisApiProperties.getC_currency_id(),
                    amt,
                    new BigDecimal(0),
                    amt,
                    sisApiProperties.getC_charge_id(),
                    new BigDecimal(0),
                    now,
                    now,
                    sisApiProperties.getAd_user_id(),
                    sisApiProperties.getAd_user_id(),
                    UUID.randomUUID()
                );
        }
        return listBSID;
    }
	
	public Object getObject(
			String tableName,
			String colParam,
			String colName,
			Object value
			) {
		Object id = 0;
		String cols = "";
		if (value instanceof String) {
			cols += "'"+value+"'";
		} else {
			cols += value;
			if (value instanceof Integer) {
				colName += "::int";
			}
		}
		String sql = 
			"SELECT " + 
	        "    "+colName+" " + 
	        "FROM "+tableName+" "+ 
	        "WHERE ad_client_id = "+sisApiProperties.getAd_client_id()+" " + 
	        "AND "+colParam+" = "+cols+" " +
	        "and isactive = 'Y' "
	        ;
		List<Map<String, Object>> resultList = source.queryForList(sql);
		if (!resultList.isEmpty()) {
			for (Map<String, Object> map: resultList) {
				id = map.get(colName.replace("::int", ""));
				break;
			}
		}
		return id;
	}

	public int getDefaultDocTypeID(
			String docBaseType
			) {
		int docTypeID = 0;
		String sql = 
			"SELECT " + 
	        "    c_doctype_id::int, " + 
	        "    isdefault " + 
	        "FROM c_doctype " + 
	        "WHERE ad_client_id = "+sisApiProperties.getAd_client_id()+" " + 
	        "AND docbasetype = '"+docBaseType+"' " +
	        "and isactive = 'Y' "
	        ;
		List<Map<String, Object>> resultList = source.queryForList(sql);
		if (!resultList.isEmpty()) {
			for (Map<String, Object> map: resultList) {
				docTypeID = (int)map.get("c_doctype_id");
				String isdefault = (String)map.get("isdefault");
				if (isdefault.equalsIgnoreCase("Y")) {
					break;
				}
			}
		}
		return docTypeID;
	}
	
	public int getNextSysID(
			String tableName
			) {
		int id = 0;
		String sql = 
			"select "
			+ "	nextidfunc(( "
			+ "	select "
			+ "		ad_sequence_id "
			+ "	from "
			+ "		ad_sequence "
			+ "	where "
			+ "		name = '"+tableName+"')::int, "
			+ "	'N')::int id "
	        ;
		List<Map<String, Object>> resultList = source.queryForList(sql);
		if (!resultList.isEmpty()) {
			for (Map<String, Object> map: resultList) {
				id = (int)map.get("id");
				break;
			}
		}
		return id;
	}
	
	public int getBankStatementID(
			Timestamp ts,
			int c_bankaccount_id
			) {
		int id = 0;
		String sql = 
			"select "
			+ "	bs.c_bankstatement_id::int id "
			+ "from c_bankstatement bs "
			+ "where bs.ad_client_id = "+sisApiProperties.getAd_client_id()+" "
			+ "and bs.isactive = 'Y' "
			+ "and trunc(bs.statementdate) = trunc('"+SISUtil.getStringDate(ts)+"'::date) "
			+ "and bs.c_bankaccount_id = "+c_bankaccount_id+" "
			+ "and bs.docstatus not in ('IP','CO') "
	        ;
		List<Map<String, Object>> resultList = source.queryForList(sql);
		if (!resultList.isEmpty()) {
			for (Map<String, Object> map: resultList) {
				id = (int)map.get("id");
				break;
			}
		}
		return id;
	}
	
	public int getBankAccountID(
			String accountNo
			) {
		int id = 0;
		String sql = 
			"select "
			+ "	ba.c_bankaccount_id::int "
			+ "from c_bankaccount ba "
			+ "where ba.accountno = '"+accountNo+"' "
			+ "and ba.ad_client_id = "+sisApiProperties.getAd_client_id()+" "
			+ "and ba.isactive = 'Y' "
	        ;
		List<Map<String, Object>> resultList = source.queryForList(sql);
		if (!resultList.isEmpty()) {
			for (Map<String, Object> map: resultList) {
				id = (int)map.get("c_bankaccount_id");
				break;
			}
		}
		return id;
	}
	
	public int getBankStatementLineID(
			int c_bankaccount_id,
			String date,
			String desc,
			BigDecimal amt
			) {
		int id = 0;
		String sql = 
			"select "
			+ "	bsl.c_bankstatementline_id::int id "
			+ "from c_bankstatementline bsl "
			+ "inner join c_bankstatement bs "
			+ "	on bs.c_bankstatement_id = bsl.c_bankstatement_id "
			+ "where bs.c_bankaccount_id = "+c_bankaccount_id+" "
			+ "and bs.isactive = 'Y' "
			+ "and trunc(bsl.statementlinedate) = trunc('"+date+"'::date) "
			+ "and bsl.description = '"+desc+"' "
			+ "and bsl.stmtamt = "+amt+" "
	        ;
		List<Map<String, Object>> resultList = source.queryForList(sql);
		if (!resultList.isEmpty()) {
			for (Map<String, Object> map: resultList) {
				id = (int)map.get("id");
				break;
			}
		}
		return id;
	}
}