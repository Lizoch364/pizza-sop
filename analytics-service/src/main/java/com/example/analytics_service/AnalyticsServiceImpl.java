package com.example.analytics_service;


import com.example.AnalyticsServiceGrpc;
import com.example.TimeOrderRequest;
import com.example.TimeOrderResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AnalyticsServiceImpl extends AnalyticsServiceGrpc.AnalyticsServiceImplBase {

    @Override
    public void calculateTimeOrder(TimeOrderRequest request, StreamObserver<TimeOrderResponse> responseObserver) {
        // Имитация бурной деятельности и сложных расчетов
        int score = (int) (Math.random() * 100);

        TimeOrderResponse response = TimeOrderResponse.newBuilder()
                .setOrderId(request.getOrderId())
                .setTimeScore(score)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
