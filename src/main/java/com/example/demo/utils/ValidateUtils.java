package com.example.demo.utils;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.OrderTransaction;
import com.example.demo.model.enums.StatusType;
import com.example.demo.model.request.ChangeStatus;

import java.util.Objects;

public class ValidateUtils {

    private static void validateStatus(Object data, int status, int statusOfData) {
        if (Objects.isNull(data)) {
            throw new ResourceNotFoundException("Không tìm thấy dữ liệu");
        }
        if (status == 0) {
            throw new ResourceNotFoundException("Không được cập nhật trạng thái thành: " + StatusType.getStatusDescription(status));
        }
        if (2 == statusOfData || 3 == statusOfData) {
            throw new ResourceNotFoundException(
                    "Trạng thái đơn đã: " + StatusType.getStatusDescription(statusOfData) + ", không thể thay đổi trạng thái"
            );
        }
        if (status == statusOfData) {
            throw new ResourceNotFoundException("Không được cập nhật trạng thái giống cũ");
        }
    }

    public static void validateOrderStatus(Order order, ChangeStatus request) {
        validateStatus(order, request.getStatus(), order.getStatus());
    }

    public static void validateOrderTransactionStatus(OrderTransaction order, ChangeStatus request) {
        validateStatus(order, request.getStatus(), order.getStatus());

        if (Objects.isNull(request.getUpdatedBy())) {
            throw new ResourceNotFoundException("Bạn không có quyền => không thể cập nhật phiếu");
        }
        if (3 == request.getStatus()) {
            throw new ResourceNotFoundException(
                    "Không thể cập nhật trạng thái: " + StatusType.getStatusDescription(request.getStatus())
            );
        }
    }

}
