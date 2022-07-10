package co.deeprooted.service;

import co.deeprooted.model.InputModel;
import co.deeprooted.model.OutputModel;

import java.util.*;

public class Service implements Comparator<InputModel>{
    public Map<String, PriorityQueue<InputModel>> insertSupply(String supply, Map<String, PriorityQueue<InputModel>> supplyList, InputModel inputModel){
        if (supplyList.containsKey(supply)) {
            PriorityQueue<InputModel> list = supplyList.get(supply);
            list.add(inputModel);
            supplyList.put(supply, list);
        } else {
            PriorityQueue<InputModel> newList = new PriorityQueue<>(new Service());
            newList.add(inputModel);
            supplyList.put(supply, newList);
        }
        return supplyList;
    }

    public List<OutputModel> checkExistingDemandList(Map<String, PriorityQueue<InputModel>> supplyList,Map<String, List<InputModel>> demandList, String supply, List<OutputModel> result){
        if (demandList.containsKey(supply) && demandList.get(supply).size() > 0) {
            System.out.println("Produce already exist in demandList");
            List<InputModel> remainingDemandList = demandList.get(supply);
            int size = remainingDemandList.size();
            PriorityQueue<InputModel> list = supplyList.get(supply);
            InputModel pqModel = list.peek();
            assert pqModel != null;
            int sQuantity = pqModel.getQuantity();
            int sPrice = pqModel.getPrice();
            for (int i = 0; i < size; i++) {
                InputModel remainingDemandModel = remainingDemandList.get(i);
                int quantity;
                if (remainingDemandModel.getPrice() >= sPrice) {
                    list.poll();
                    if (sQuantity > 0) {
                        quantity = sQuantity - remainingDemandModel.getQuantity();
                        OutputModel oModel = new OutputModel();
                        if (quantity >= 0) {
                            oModel.setDemandOrderId(remainingDemandModel.getOrderId());
                            oModel.setSupplyOrderId(pqModel.getOrderId());
                            oModel.setQuantity(remainingDemandModel.getQuantity());
                            oModel.setPrice(pqModel.getPrice());
                            result.add(oModel);
                            sQuantity = sQuantity - remainingDemandModel.getQuantity();
                            if (sQuantity > 0) {
                                pqModel.setQuantity(sQuantity);
                                list.add(pqModel);
                                supplyList.put(supply, list);
                            }
                        }
                        else {
                            oModel.setDemandOrderId(remainingDemandModel.getOrderId());
                            oModel.setSupplyOrderId(pqModel.getOrderId());
                            oModel.setQuantity(sQuantity);
                            oModel.setPrice(pqModel.getPrice());
                            result.add(oModel);
                            int remainingQuantity = remainingDemandModel.getQuantity() - sQuantity;
                            remainingDemandModel.setQuantity(remainingQuantity);
                            remainingDemandList.set(i, remainingDemandModel);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
    public List<OutputModel> checkExistingSupplyList(Map<String, PriorityQueue<InputModel>> supplyList,Map<String, List<InputModel>> demandList, String demandProduce,InputModel inputModel, List<OutputModel> result){
        if(supplyList.containsKey(demandProduce) && supplyList.get(demandProduce).size() > 0) {
            PriorityQueue<InputModel> pqList = supplyList.get(demandProduce);
            InputModel pqModel = pqList.peek();
            assert pqModel != null;
            int sQuantity = pqModel.getQuantity();
            int sPrice = pqModel.getPrice();
            int quantity;
            if(inputModel.getPrice() >= sPrice){
                pqList.poll();
                if(sQuantity > 0){
                    quantity = sQuantity - inputModel.getQuantity();
                    OutputModel oModel = new OutputModel();
                    oModel.setDemandOrderId(inputModel.getOrderId());
                    oModel.setSupplyOrderId(pqModel.getOrderId());
                    if(quantity >= 0){
                        oModel.setQuantity(inputModel.getQuantity());
                        oModel.setPrice(pqModel.getPrice());
                        result.add(oModel);
                        pqModel.setQuantity(quantity);
                        if(quantity > 0) {
                            pqList.add(pqModel);
                            supplyList.put(demandProduce, pqList);
                        }
                    }
                    else{
                        oModel.setQuantity(sQuantity);
                        oModel.setPrice(pqModel.getPrice());
                        result.add(oModel);
                        int remainingQuantity = inputModel.getQuantity() - sQuantity;
                        inputModel.setQuantity((remainingQuantity));
                        while(pqList.size() > 0){
                            pqModel = pqList.peek();
                            sQuantity = pqModel.getQuantity();
                            sPrice = pqModel.getPrice();
                            pqList.poll();
                            if(inputModel.getPrice() >= sPrice){
                                if(remainingQuantity > 0){
                                    quantity = sQuantity - remainingQuantity;
                                    oModel = new OutputModel();
                                    oModel.setDemandOrderId(inputModel.getOrderId());
                                    oModel.setSupplyOrderId(pqModel.getOrderId());
                                    oModel.setQuantity(remainingQuantity);
                                    oModel.setPrice(pqModel.getPrice());
                                    result.add(oModel);
                                    if(quantity >= 0){
                                        sQuantity = sQuantity - remainingQuantity;
                                        if(sQuantity > 0){
                                            pqModel.setQuantity(sQuantity);
                                            pqList.add(pqModel);
                                            supplyList.put(demandProduce,pqList);
                                        }
                                        break;
                                    }
                                    else{
                                        remainingQuantity = remainingQuantity - sQuantity;
                                    }
                                }
                                else{
                                    pqList.add(pqModel);
                                    supplyList.put(demandProduce,pqList);
                                    break;
                                }
                            }
                            else{
                                inputModel.setQuantity(remainingQuantity);
                                List<InputModel> remainingDemand = new ArrayList<>();
                                remainingDemand.add(inputModel);
                                demandList.put(demandProduce, remainingDemand);
                                //break;
                            }
                        }
                        if(pqList.size() == 0){
                            List<InputModel> remainingDemand = new ArrayList<>();
                            remainingDemand.add(inputModel);
                            demandList.put(demandProduce, remainingDemand);
                        }
                        //break;
                    }
                }
            }

        }
        else{
            List<InputModel> newList1 = demandList.get(demandProduce);
            if (newList1 == null) {
                newList1 = new ArrayList<>();
            }
            newList1.add(inputModel);
            demandList.put(demandProduce, newList1);
        }
        return result;
    }

    @Override
    public int compare(InputModel o1, InputModel o2) {
        return o1.getPrice() - o2.getPrice();
    }
}
