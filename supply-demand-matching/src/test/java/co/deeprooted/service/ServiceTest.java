package co.deeprooted.service;

import co.deeprooted.model.InputModel;
import co.deeprooted.model.OutputModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {
    Service service;
    Map<String, PriorityQueue<InputModel>> supplyList;
    Map<String, List<InputModel>> demandList;
    PriorityQueue<InputModel> newList;
    InputModel inputModel;
    List<OutputModel> list;
    List<InputModel> demands;
    OutputModel outputModel;

    @BeforeEach
    void setUp() {
        inputModel = new InputModel();
        inputModel.setOrderId("s1");
        inputModel.setTime("09:45");
        inputModel.setProduce("tomato");
        inputModel.setPrice(Integer.parseInt("24/kg".replaceAll("\\D+", "")));
        inputModel.setQuantity(Integer.parseInt("100kg" .replaceAll("\\D+", "")));
        service = new Service();
        newList = new PriorityQueue<>(new Service());
        supplyList = new HashMap<>();
        demandList = new HashMap<>();
        list = new ArrayList<>();
    }

    @Test
    @DisplayName("should return size of Map equals to 1")
    void shouldAddInSupplyListAndReturnSize1(){
        assertEquals(1,service.insertSupply("tomato",supplyList, inputModel).size());
    }

    @Test
    @DisplayName("should return size of Queue equals to 2")
    void shouldReturnSizeOfQueueEqualsTo2(){
        newList.add(inputModel);
        supplyList.put("tomato", newList);
        assertEquals(2,service.insertSupply("tomato",supplyList, inputModel).get("tomato").size());
    }

    @Test
    @DisplayName("should return empty list of output model ")
    void shouldReturnEmptyList(){
       assertEquals(new ArrayList<>(),service.checkExistingDemandList(supplyList,demandList,"tomato", list));
    }

    @Test
    @DisplayName("check whether supply is already in demand")
    void shouldCheckInExistingDemandList(){
        newList.add(inputModel);
        supplyList.put("tomato", newList);
        outputModel = new OutputModel();
        outputModel.setDemandOrderId("d1");
        outputModel.setSupplyOrderId("s1");
        outputModel.setPrice(24);
        outputModel.setQuantity(80);
        List<InputModel> list1 = new ArrayList<>();
        list1.add(inputModel);
        demandList.put("tomato", list1);
        assertEquals(list1.size(),service.checkExistingDemandList(supplyList,demandList,"tomato", list).size());
    }

    @Test
    @DisplayName("Add in demand list")
    void shouldAddInDemandList(){
        newList.add(inputModel);
        supplyList.put("tomato", newList);
        assertEquals(1,service.checkExistingSupplyList(supplyList,demandList,"tomato",inputModel,list).size());
    }

    @Test
    @DisplayName("check demand is already for supply")
    void shouldCheckInExistingSupplyList(){
        demands = new ArrayList<>();
       InputModel inputModel1 = new InputModel();
        inputModel1.setOrderId("d1");
        inputModel1.setTime("09:45");
        inputModel1.setProduce("tomato");
        inputModel1.setPrice(Integer.parseInt("28/kg".replaceAll("\\D+", "")));
        inputModel1.setQuantity(Integer.parseInt("100kg" .replaceAll("\\D+", "")));
        demands.add(inputModel1);
        demandList.put("tomato",demands);
        newList.add(inputModel);
        supplyList.put("tomato", newList);
        assertEquals(1, service.checkExistingSupplyList(supplyList,demandList,"tomato",inputModel,list).size());
    }
}
