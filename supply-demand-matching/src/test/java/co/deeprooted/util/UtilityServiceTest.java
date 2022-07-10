package co.deeprooted.util;

import co.deeprooted.model.InputModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityServiceTest {

    private InputModel inputModel;
    private UtilityService utilityService;
    String [] data;

    @BeforeEach
    void setUp() {
        utilityService = new UtilityService();
        data = new String[]{"d1", "09:45", "tomato", "24/kg", "100kg" };
        inputModel = new InputModel();
        inputModel.setOrderId(data[0]);
        inputModel.setTime(data[1]);
        inputModel.setProduce(data[2]);
        inputModel.setPrice(Integer.parseInt(data[3].replaceAll("\\D+", "")));
        inputModel.setQuantity(Integer.parseInt(data[4].replaceAll("\\D+", "")));
    }

    @Test
    @DisplayName("should return orderId")
    void shouldReturnOderId() {
        assertEquals(inputModel.getOrderId(), utilityService.setInputData(data).getOrderId());
    }

    @Test
    @DisplayName("should return Time")
    void shouldReturnTime() {
        assertEquals(inputModel.getTime(), utilityService.setInputData(data).getTime());
    }
    @Test
    @DisplayName("should return Produce")
    void shouldReturnProduce() {
        assertEquals(inputModel.getProduce(), utilityService.setInputData(data).getProduce());
    }
    @Test
    @DisplayName("should return Price")
    void shouldReturnPrice() {
        assertEquals(inputModel.getPrice(), utilityService.setInputData(data).getPrice());
    }

    @Test
    @DisplayName("should return quantity")
    void shouldReturnQuantity() {
        assertEquals(inputModel.getQuantity(), utilityService.setInputData(data).getQuantity());
    }

}
