package co.deeprooted;

import co.deeprooted.fileprocessor.FileProcessing;
import co.deeprooted.model.InputModel;
import co.deeprooted.model.OutputModel;
import co.deeprooted.service.Service;
import co.deeprooted.util.UtilityService;

import java.io.IOException;
import java.util.*;

public class Application {
    public static void main(String[] args) throws IOException {
        String fileForReading = "sample-input-output/input.txt";
        String fileForWriting = "sample-input-output/output.txt";
        FileProcessing file = new FileProcessing();
        UtilityService utilityService = new UtilityService();
        Service service = new Service();
        Scanner sc = file.fileReader(fileForReading);
        Map<String, PriorityQueue<InputModel>> supplyList = new HashMap<>();
        Map<String, List<InputModel>> demandList = new HashMap<>();
        List<OutputModel> result = new ArrayList<>();
        List<String> output = new ArrayList<>();
        while (sc.hasNextLine()) {
            String request = sc.nextLine();
            String[] data = request.split(" ");
            InputModel inputModel = utilityService.setInputData(data);
            System.out.println("Inserted In POJO " + inputModel.getOrderId() + " " + inputModel.getTime() + " " +
                    inputModel.getPrice() + " " + inputModel.getProduce() + " " + inputModel.getQuantity());
            if (data[0].charAt(0) == 's') {
                String supply = data[2];
                supplyList = service.insertSupply(supply,supplyList,inputModel);
                result = service.checkExistingDemandList(supplyList, demandList,supply,result);
            }
            else if(data[0].charAt(0) == 'd'){
                String demandProduce = data[2];
                result = service.checkExistingSupplyList(supplyList, demandList, demandProduce, inputModel, result);
            }
        }
        if(result.size() == 0 && demandList.size() > 0){
            output.add("No supply to fulfill the demand");
        }
        else if(result.size() == 0 && supplyList.size() >0){
            output.add("No demand to fulfill the supply");
        }
        else {
            for (OutputModel om : result) {
                output.add(om.getDemandOrderId() + " " + om.getSupplyOrderId() + " " + om.getPrice()+"/kg"
                        + " " + om.getQuantity()+"kg");
            }
        }
        file.fileWriter(fileForWriting,output);
    }
}
