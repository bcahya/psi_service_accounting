package id.sis.service.accounting.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import id.sis.service.accounting.pojo.RB_MO;
import id.sis.service.accounting.pojo.RB_MOBOM;
import id.sis.service.accounting.pojo.RB_MOProduct;
import id.sis.service.accounting.pojo.RB_MOProductReplenish;
import id.sis.service.accounting.pojo.RB_MORouting;
import id.sis.service.accounting.pojo.RB_MOSOH;
import id.sis.service.accounting.pojo.RB_MOWH;

public class SIS_BisproUtils {
	
	public List<LinkedHashMap<String,Object>> groupingMove(
			List<LinkedHashMap<String,Object>> listMove
			){
		List<LinkedHashMap<String, Object>> listMoveSum = new ArrayList<>();
		for (LinkedHashMap<String, Object> mapMove: listMove) {
			LinkedHashMap<String, Object> mapMv = new LinkedHashMap<>();
			boolean isExists = false;
			for (LinkedHashMap<String, Object> mapMS: listMoveSum) {
				if (mapMS.get("locatorfrom_id").equals(mapMove.get("locatorfrom_id"))
						&& mapMS.get("locatorto_id").equals(mapMove.get("locatorto_id"))) {
					isExists = true;
					mapMv = mapMS;
					break;
				}
			}
			if (mapMv.isEmpty()) {
				mapMv.put("locatorfrom_id", mapMove.get("locatorfrom_id"));
				mapMv.put("locatorto_id", mapMove.get("locatorto_id"));
				mapMv.put("list_product", new ArrayList<>());
			}
			List<LinkedHashMap<String,Object>> listProduct = (List<LinkedHashMap<String,Object>>)mapMv.get("list_product");
			LinkedHashMap<String, Object> mapProduct = new LinkedHashMap<>();
			boolean isProductExists = false;
			for (LinkedHashMap<String, Object> mapP: listProduct) {
				if (mapP.get("product_id").equals(mapMove.get("product_id"))) {
					isProductExists = true;
					mapProduct = mapP;
					break;
				}
			}
			if (mapProduct.isEmpty()) {
				mapProduct.put("product_id", mapMove.get("product_id"));
				mapProduct.put("qty", new BigDecimal(0));
			}
			mapProduct.put("qty", ((BigDecimal)mapProduct.get("qty")).add((BigDecimal)mapMove.get("qty")));
			if (!isProductExists) {
				listProduct.add(mapProduct);
			}
			if (!isExists) {
				listMoveSum.add(mapMv);
			}
		}
		
		return listMoveSum;
	}
	
	public RB_MORouting getNextRouting(
			List<RB_MORouting> listProductRouting,
			int locatorID,
			boolean isFrom
			) {
		RB_MORouting routing = null;
		for (RB_MORouting r: listProductRouting) {
			int locID = r.getLocatorfrom_id();
			if (!isFrom) {
				locID = r.getLocatorto_id();
			}
			if (locID == locatorID) {
				routing = r;
				break;
			}
		}
		return routing;
	}
	
	public RB_MORouting getNextRouting(
			List<RB_MORouting> listProductRouting,
			int locatorID,
			boolean isFrom,
			String action
			) {
		RB_MORouting routing = null;
		for (RB_MORouting r: listProductRouting) {
			int locID = r.getLocatorfrom_id();
			if (!isFrom) {
				locID = r.getLocatorto_id();
			}
			if (locID == locatorID
					&& r.getAction().equalsIgnoreCase(action)) {
				routing = r;
				break;
			}
		}
		return routing;
	}
	
	public List<RB_MORouting> getListProductRouting(
			List<RB_MORouting> listRouting,
			List<RB_MOProduct> listProduct,
			int productID
			){
		List<RB_MORouting> listProductRouting = new ArrayList<RB_MORouting>();
		RB_MOProduct product = getProduct(listProduct, productID);
		for(Integer rID: product.getList_routing()) {
			RB_MORouting routing = getRouting(listRouting, rID);
			if (routing != null) {
				listProductRouting.add(routing);
			}
		}
		return listProductRouting;
	}
	
	public RB_MOWH getWarehouse(
			List<RB_MOWH> listWH,
			int whID
			){
		RB_MOWH wh = null;
		for (RB_MOWH warehouse: listWH) {
			if (warehouse.getWarehouse_id() == whID) {
				wh = warehouse;
				break;
			}
		}
		return wh;
	}
	
	public RB_MOBOM getBOM(
			List<RB_MOBOM> listBOM,
			int bomSourceID,
			int productID
			){
		RB_MOBOM bom = null;
		for (RB_MOBOM b: listBOM) {
			if (b.getBom_source_id() == bomSourceID
					&& b.getProduct_id() == productID) {
				bom = b;
				break;
			}
		}
		return bom;
	}
	
	public RB_MOBOM getBOM(
			List<RB_MOBOM> listBOM,
			int bomID
			){
		RB_MOBOM bom = null;
		for (RB_MOBOM b: listBOM) {
			if (b.getBom_id() == bomID) {
				bom = b;
				break;
			}
		}
		return bom;
	}
	
	public RB_MOProduct getProduct(
			List<RB_MOProduct> listProduct,
			int productID
			){
		RB_MOProduct p = null;
		for (RB_MOProduct product: listProduct) {
			if (product.getProduct_id() == productID) {
				p = product;
				break;
			}
		}
		return p;
	}
	
	public RB_MOProductReplenish getProductReplenish(
			List<RB_MOProductReplenish> listReplenish,
			int productID,
			int warehouseID
			){
		RB_MOProductReplenish r = null;
		for (RB_MOProductReplenish replenish: listReplenish) {
			if (replenish.getProduct_id() == productID
					&& replenish.getWarehouse_id() == warehouseID) {
				r = replenish;
				break;
			}
		}
		return r;
	}
	
	public RB_MORouting getRouting(
			List<RB_MORouting> listRouting,
			int routingLineID
			){
		RB_MORouting r = null;
		for (RB_MORouting routing: listRouting) {
			if (routing.getRoutingline_id() == routingLineID) {
				r = routing;
				break;
			}
		}
		return r;
	}
	
	public RB_MOSOH getSOH(
			List<RB_MOSOH> listSOH,
			int productID,
			int locatorID
			){
		RB_MOSOH soh = null;
		for (RB_MOSOH stoh: listSOH) {
			if (stoh.getProduct_id() == productID
					&& stoh.getLocator_id() == locatorID) {
				soh = stoh;
				break;
			}
		}
		return soh;
	}
	
	public BigDecimal getQtySOH(
			List<RB_MOSOH> listSOH,
			int productID,
			int locatorID
			) {
		BigDecimal qty = new BigDecimal(0);
		RB_MOSOH soh = getSOH(listSOH, productID, locatorID);
		if (soh != null) {
			qty = soh.getQty();
		}
		return qty;
	}
	
	public boolean isProductBOM(
			List<RB_MOProduct> listProduct,
			int productID
			){
		boolean isBOM = false;
		RB_MOProduct product = getProduct(listProduct, productID);
		if (product != null) {
			isBOM = product.isIs_bom();
		}
		return isBOM;
	}
	
	public int generateMove(
			List<LinkedHashMap<String, Object>> listMove,
			RB_MORouting routing,
			int productID,
			BigDecimal qty,
			boolean isFG,
			int seqMove
			) {
		if (routing.getAction().equalsIgnoreCase(SISConstants.MO_ROUTING_ACTION_PUSHTO)) {
			seqMove += 100;
		} else {
			seqMove -= 100;
		}
		LinkedHashMap<String, Object> mapMove = new LinkedHashMap<>();
		boolean moveExist = false;
		for (LinkedHashMap<String, Object> map: listMove) {
			if ((int)map.get("locatorfrom_id") == routing.getLocatorfrom_id()
					&& (int)map.get("locatorto_id") == routing.getLocatorto_id()
							&& (boolean)map.get("is_fg") == isFG
					) {
				mapMove = map;
				moveExist = true;
				break;
			}
		}
		if (mapMove.isEmpty()) {
			mapMove.put("locatorfrom_id", routing.getLocatorfrom_id());
			mapMove.put("locatorto_id", routing.getLocatorto_id());
			mapMove.put("is_fg", isFG);
			mapMove.put("seq", seqMove);
			mapMove.put("list_product", new ArrayList<>());
		}
		List<LinkedHashMap<String,Object>> listProduct = (List<LinkedHashMap<String,Object>>)mapMove.get("list_product");
		LinkedHashMap<String, Object> mapProduct = new LinkedHashMap<>();
		boolean isProductExists = false;
		for (LinkedHashMap<String, Object> mapP: listProduct) {
			if (mapP.get("product_id").equals(productID)) {
				isProductExists = true;
				mapProduct = mapP;
				break;
			}
		}
		if (mapProduct.isEmpty()) {
			mapProduct.put("product_id", productID);
			mapProduct.put("qty", new BigDecimal(0));
		}
		mapProduct.put("qty", ((BigDecimal)mapProduct.get("qty")).add(qty));
		if (!isProductExists) {
			listProduct.add(mapProduct);
		}
		if (!moveExist) {
			listMove.add(mapMove);
		}
		return seqMove;
	}
	
	public void generateReq(
			LinkedHashMap<Integer, BigDecimal> mapReqs,
			List<LinkedHashMap<String, Object>> listReq,
			List<RB_MOProduct> listProduct,
			int warehouse_id,
			int productID,
			BigDecimal qty
			) {
		if (!mapReqs.containsKey(productID)) {
			mapReqs.put(productID, new BigDecimal(0));
		}
		mapReqs.put(productID, mapReqs.get(productID).add(qty));
		
		LinkedHashMap<String, Object> mapReq = new LinkedHashMap<>();
		RB_MOProduct product = getProduct(listProduct, productID);
		boolean reqExist = false;
		for (LinkedHashMap<String, Object> map: listReq) {
			if ((int)map.get("bp_id") == product.getBp_id()
					&& (int)map.get("warehouse_id") == warehouse_id
					) {
				mapReq = map;
				reqExist = true;
				break;
			}
		}
		if (mapReq.isEmpty()) {
			mapReq.put("bp_id", product.getBp_id());
			mapReq.put("warehouse_id", warehouse_id);
			mapReq.put("list_line", new ArrayList<>());
		}
		LinkedHashMap<String, Object> mapLine = new LinkedHashMap<>();
		List<LinkedHashMap<String, Object>> listLine = (List<LinkedHashMap<String, Object>>) mapReq.get("list_line");
		boolean lineExist = false;
		for (LinkedHashMap<String, Object> map: listLine) {
			if ((int)map.get("product_id") == productID) {
				mapLine = map;
				lineExist = true;
				break;
			}
		}
		if (mapLine.isEmpty()) {
			mapLine.put("product_id", productID);
			mapLine.put("qty", new BigDecimal(0));
		}
		mapLine.put("qty", ((BigDecimal)mapLine.get("qty")).add(qty));
		if (!lineExist) {
			listLine.add(mapLine);
		}
		if (!reqExist) {
			listReq.add(mapReq);
		}
	}
	
}
