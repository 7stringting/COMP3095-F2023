package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.InventoryRequest;
import ca.gbc.orderservice.dto.InventoryResponse;
import ca.gbc.orderservice.dto.OrderLineItemDto;
import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.model.OrderLineItem;
import ca.gbc.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Value("${inventory.service.url}")
    private String inventoryApiUri;
    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems=orderRequest
                .getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDo)
                .toList();
        order.setOrderLineItemList(orderLineItems);

        List<InventoryRequest> inventoryRequests = order.getOrderLineItemList()
                .stream().map(orderLineItem -> InventoryRequest.builder()
                        .skuCode(orderLineItem.getSkuCode())
                        .quantity(orderLineItem.getQuantity())
                        .build())
                .toList();

        List<InventoryResponse> inventoryResponsesList = webClient
                .post()
                .uri(inventoryApiUri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequests)
                .retrieve()
                .bodyToFlux(InventoryResponse.class).collectList()
                .block();

        assert inventoryResponsesList != null;
        boolean allProductsInStock = inventoryResponsesList
                .stream()
                .allMatch(InventoryResponse::isSufficientStock);
        if(Boolean.TRUE.equals(allProductsInStock)){
            orderRepository.save(order);
        }else {
            throw new RuntimeException("not all products are in stock");
        }




        orderRepository.save(order);

    }

    private OrderLineItem mapToDo(OrderLineItemDto orderLineItemDto){
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItem;
    }
}
