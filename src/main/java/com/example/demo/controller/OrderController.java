package com.example.demo.controller;

import com.example.demo.model.request.ChangeStatus;
import com.example.demo.model.request.NewOrderRequest;
import com.example.demo.model.response.ProcessResult;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("order/v1")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @PostMapping("create_new_order")
    public ProcessResult createNewOrder(@RequestBody @Valid NewOrderRequest request) {
        return new ProcessResult(
                HttpStatus.OK.toString(),
                "Tạo đơn thành công!",
                orderService.createOrder(request)
        );
    }

    @PutMapping("update_order_status/{orderId}")
    public ProcessResult updateOrderStatus(@PathVariable String orderId,
                                           @RequestBody ChangeStatus request) {
        return new ProcessResult(
                HttpStatus.OK.toString(),
                "Cập nhật trạng thái đơn hàng thành công!",
                orderService.updateOrderRequestStatus(orderId, request, true)
        );
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @PutMapping("update_order_transaction_status/{orderTransactionId}")
    public ProcessResult updateOrderTransactionStatus(@PathVariable String orderTransactionId,
                                                      @RequestBody ChangeStatus request) {
        return new ProcessResult(
                HttpStatus.OK.toString(),
                "Cập nhật trạng thái đơn giao dịch thành công!",
                orderService.updateOrderTransactionStatus(orderTransactionId, request)
        );
    }
}
