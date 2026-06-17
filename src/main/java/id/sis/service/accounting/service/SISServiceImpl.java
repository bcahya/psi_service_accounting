package id.sis.service.accounting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import id.sis.service.accounting.businessprocess.SISApi;
import id.sis.service.accounting.businessprocess.SISGlobalExecute;
import id.sis.service.accounting.response.SISResponse;
import id.sis.service.accounting.utils.SISUtil;

@Service
@EnableScheduling
public class SISServiceImpl{

	private final static Logger logger = LoggerFactory.getLogger(SISServiceImpl.class);
	SISUtil ku = new SISUtil();
	
	@Autowired
	SISGlobalExecute ex;

    @Autowired
    SISApi sis;

    @Scheduled(cron = "0 */10 * * * *")
	public SISResponse processMT940() throws Exception {
        logger.info("execute processMT940");
        return ex.processMT940();
    }

//    @Scheduled(cron = "0 */10 * * * *")
//    @Transactional(rollbackFor = Exception.class)
//    public void createOrder() throws Exception {
//        logger.info("[SIS] Start Create Order");
//        SISResponseDto<DispatchScheduleDto> dsResponse = sis.getDispatchScheduler(0, "0", "3", null);
//        List<DispatchScheduleDto> resultData = dsResponse.getResultdata();
//
//        if (resultData.size() == 0) {
//            logger.info("[SIS] 0 Data Found");
//            logger.info("[SIS] End Create Order");
//            return;
//        }
//
//        for (DispatchScheduleDto ds : resultData) {
//            ItemDao mixItem = ex.getItem(ds.getItem_code(), "M");
//            
//            if (mixItem == null) {
//                mixItem = ex.executeCreateMixEdx(ds.getItem_code(), ds.getAlias_code(), ds.getOther_code(), ds.getUom());
//            }
//
//            List<ItemDao> materialItemList = new LinkedList<>();
//            for (BOMLineDto bl : ds.getBom_lines()) {
//                ItemDao materialItem = ex.getItem(bl.getItem_code(), "I");
//                if (materialItem == null) {
//                    materialItem = ex.executeCreateMaterialEdx(bl.getItem_name(), bl.getItem_code(), bl.getUom(), bl.getItem_group_name());
//                }
//                materialItemList.add(materialItem);
//            }
//
//            List<ItemLineDao> itemLineList = ex.getItemLine(mixItem.getItemID());
//            if (itemLineList.size() == 0) {
//                for (int i = 0; i < ds.getBom_lines().size(); i++) {
//                    BOMLineDto line = ds.getBom_lines().get(i);
//                    ItemLineDao itemLine = ex.executeCreateMIXIngredientEDX(mixItem.getEDXID(), materialItemList.get(i).getItemID(), line.getQty_rm() , line.getUom());
//                    itemLine.setItemID(mixItem.getItemID());
//                    itemLineList.add(itemLine);
//                }
//            } else {
//                for (BOMLineDto line : ds.getBom_lines()) {
//                    ItemDao materialTemp = materialItemList.stream().filter(m -> m.getItem_Code().equals(line.getItem_code())).findAny().orElse(null);
//
//                    if (materialTemp == null) {
//                        materialTemp = ex.executeCreateMaterialEdx(line.getItem_name(), line.getItem_code(), line.getUom(), line.getItem_group_name());
//                        materialItemList.add(materialTemp);
//                    }
//
//                    ItemDao material = materialTemp;
//                    ItemLineDao itemLine = itemLineList.stream().filter(l -> l.getIngredient_ItemID().equals(material.getItemID())).findAny().orElse(null);
//
//                    if (itemLine == null) {
//                        itemLine = ex.executeCreateMIXIngredientEDX(mixItem.getEDXID(), material.getItemID(), line.getQty_rm(), line.getUom());
//                        itemLine.setItemID(mixItem.getItemID());
//                        itemLineList.add(itemLine);
//                    } else {
//                        boolean uomMatch = itemLine.getEntry_UOM().equalsIgnoreCase(line.getUom());
//                        boolean qtyMatch = itemLine.getEntry_Qty().compareTo(BigDecimal.valueOf(line.getQty_rm())) == 0 ;
//                        if (!uomMatch || !qtyMatch) {
//                            int itemLineIndex = itemLineList.indexOf(itemLine);
//                            itemLine = ex.executeUpdateMIXIngredientEDX(mixItem.getEDXID(), material.getItemID(), line.getQty_rm(), line.getUom());
//                            itemLineList.set(itemLineIndex, itemLine);
//                        }
//                    }
//                }
//            }
//
//            OrderDao order = ex.executeCreateOrderEDX(ds.getSearch_key(), ds.getPo_num(), ds.getAddress1(), ds.getAddress2(), ds.getAddress3());
//            ex.executeCreateOrderLineEDX(order.getOrderID(), mixItem.getItemID(), ds.getQty().toString(), ds.getUom());  
//
//            //update cb order no id=sis_dispatchschedule_id, cb_order_no=?(order id atau order line id?)
//            sis.updateCbOrderNo(ds.getSIS_dispatchschedule_id(), order.getOrderID());
//
//            //update cb status id=sis_dispatchschedule_id, status=4(cb ok)
//            sis.updateCbStatus(ds.getSIS_dispatchschedule_id(), "4");
//
//        }
//
//        logger.info("[SIS] End Create Order");
//    }

   
}