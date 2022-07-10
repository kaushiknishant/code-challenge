package co.deeprooted.util;

import co.deeprooted.model.InputModel;

public class UtilityService {
    public InputModel setInputData(String []data){
        InputModel inputModel = new InputModel();
        inputModel.setOrderId(data[0]);
        inputModel.setTime(data[1]);
        inputModel.setProduce(data[2]);
        inputModel.setPrice(Integer.parseInt(data[3].replaceAll("\\D+", "")));
        inputModel.setQuantity(Integer.parseInt(data[4].replaceAll("\\D+", "")));
        return inputModel;
    }
}
