package com.plazoleta.traceability.domain.usecase;

import com.plazoleta.traceability.domain.api.IOrderLogServicePort;
import com.plazoleta.traceability.domain.model.*;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import com.plazoleta.traceability.domain.spi.IUserAuthenticationPort;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class OrderLogUseCase implements IOrderLogServicePort {
    private final IOrderLogPersistencePort orderLogPersistencePort;
    private final IUserAuthenticationPort userAuthenticationPort;

    String preparingStateName = "PREPARING";
    String readyStateName = "READY";

    @Override
    public void createLog(OrderStatusLog orderStatusLog) {
        String lastOrderStatus = "";
        Optional<OrderStatusLog> lastOrderStatusLog = orderLogPersistencePort.findLastOrderStatusLogByOrderId(orderStatusLog.getOrderId());
        if(lastOrderStatusLog.isPresent()){
            OrderStatusLog orderStatusLog1 = lastOrderStatusLog.get();
            lastOrderStatus = orderStatusLog1.getStatus();
        }

        orderStatusLog.setPreviousStatus(lastOrderStatus);
        orderStatusLog.setUpdatedAt(LocalDateTime.now());
        orderLogPersistencePort.saveOrderStatusLog(orderStatusLog);
    }


    @Override
    public OrderWithLog getOrderLogByOrderId(Long orderId) {
        List<OrderStatusLog> orderStatusLogList = orderLogPersistencePort.findAllOrderStatusLogByOrderId(orderId);
        OrderWithLog orderWithLog = new OrderWithLog();
        orderWithLog.setOrderId(orderId);
        orderWithLog.setLogs(orderStatusLogList);
        return orderWithLog;
    }

    @Override
    public List<OrderWithLog> getAllOrderLogByCustomer() {
        Long customerId = userAuthenticationPort.getAuthenticatedUserId();
        List<OrderStatusLog> orderStatusLogList = orderLogPersistencePort.findAllOrderStatusLogByCustomerId(customerId);

        Map<Long, List<OrderStatusLog>> logsByOrderId = orderStatusLogList.stream()
                .collect(Collectors.groupingBy(OrderStatusLog::getOrderId));

        return logsByOrderId.entrySet().stream()
                .map(entry -> {
                    OrderWithLog orderWithLog = new OrderWithLog();
                    orderWithLog.setOrderId(entry.getKey());
                    orderWithLog.setLogs(entry.getValue());
                    return orderWithLog;
                })
                .toList();
    }

    @Override
    public OrderRanking getOrderRankingOfRestaurant(Long restaurantId) {
        List<OrderCompletion> orderCompletionsOfRestaurant = orderCompletions(restaurantId);
        List<EmployeePerformance> employeePerformances = employeePerformances(restaurantId);

        OrderRanking orderRanking = new OrderRanking();
        orderRanking.setOrderCompletionList(orderCompletionsOfRestaurant);
        orderRanking.setEmployeePerformances(employeePerformances);
        return orderRanking;
    }



    private List<OrderStatusLog> filterByStates(List<String> states, List<OrderStatusLog> orderStatusLogs) {
        return orderStatusLogs.stream()
                .filter(orderStatusLog -> states.contains(orderStatusLog.getStatus()))
                .collect(Collectors.toList());
    }


    private List<EmployeePerformance> employeePerformances(Long restaurantId){
        List<String> states = new ArrayList<>();
        states.add(preparingStateName);
        states.add(readyStateName);

        List<Long> employeesIds = orderLogPersistencePort.findAllEmployeesByRestaurantId(restaurantId);
        List<EmployeePerfomanceWrapper> employeesWithOrders = employeesIds.stream().map(employeesId -> {
            EmployeePerfomanceWrapper employeePerfomanceWrapper = new EmployeePerfomanceWrapper();
            employeePerfomanceWrapper.setEmployeeId(employeesId);

            List<OrderStatusLog> orderStatusLog = orderLogPersistencePort.findAllOrderStatusLogByEmployeeId(employeesId);
            orderStatusLog = filterByStates(states, orderStatusLog);


            Map<Long, List<OrderStatusLog>> logsAggroupedByOrderId = orderStatusLog.stream()
                    .collect(Collectors.groupingBy(OrderStatusLog::getOrderId));

            List<OrderWrapper> orderWrapper = logsAggroupedByOrderId.entrySet().stream().map(longListEntry -> {

                List<OrderStatusLog> logs = longListEntry.getValue();

                OrderStatusLog orderStatusLogPreparing = findOrderStatusLogByState(logs, preparingStateName);
                OrderStatusLog orderStatusLogReady = findOrderStatusLogByState(logs, readyStateName);
                Double finishedInSeconds = calculeDifferenceInSeconds(orderStatusLogPreparing.getUpdatedAt(), orderStatusLogReady.getUpdatedAt());

                Long orderId = logs.get(0).getOrderId();
                OrderWrapper orderWrapperTemp = new OrderWrapper();
                orderWrapperTemp.setOrderId(orderId);
                orderWrapperTemp.setStartedAt(orderStatusLogPreparing.getUpdatedAt());
                orderWrapperTemp.setFinishedAt(orderStatusLogReady.getUpdatedAt());
                orderWrapperTemp.setFinishedInSeconds(finishedInSeconds);

                return orderWrapperTemp;
            }).toList();

            employeePerfomanceWrapper.setOrders(orderWrapper);
            return employeePerfomanceWrapper;
        }).toList();

        List<EmployeePerformance> employeePerformance = employeesWithOrders.stream().map(employeePerfomanceWrapper -> {
            Long employeeId = employeePerfomanceWrapper.getEmployeeId();
            Double timePromedius = calculatePromediusOfOrderWraper(employeePerfomanceWrapper.getOrders());
            EmployeePerformance employeePerformanceTemp = new EmployeePerformance();
            employeePerformanceTemp.setEmployeeId(employeeId);
            employeePerformanceTemp.setAverageCompletionTimeInSeconds(timePromedius);
            return employeePerformanceTemp;
        }).toList();

        return employeePerformance;
    }


    private Double calculatePromediusOfOrderWraper(List<OrderWrapper> orderWrappers) {
        if (orderWrappers.isEmpty()) {
            return 0.0;
        }

        double totalFinishedInSeconds = orderWrappers.stream()
                .filter(orderWrapper -> orderWrapper.getFinishedInSeconds() != null)
                .mapToDouble(OrderWrapper::getFinishedInSeconds)
                .sum();

        return totalFinishedInSeconds / orderWrappers.size();
    }


    private boolean hasAnyLogWithStatus(List<OrderStatusLog> orderStatusLogs, String status){
        return orderStatusLogs.stream()
                .anyMatch(orderStatusLog -> orderStatusLog.getStatus().equals(status));
    }

    private List<OrderCompletion> orderCompletions(Long restaurantId){

        List<OrderStatusLog> orderStatusLogList = orderLogPersistencePort.findAllOrderStatusLogByRestaurantId(restaurantId);

        Map<Long, List<OrderStatusLog>> logsAggroupedByOrderId = orderStatusLogList.stream()
                .collect(Collectors.groupingBy(OrderStatusLog::getOrderId));

        Map<Long, List<OrderStatusLog>> logsByOrderIdFiltered = logsAggroupedByOrderId.entrySet().stream()
                .filter(entry ->
                        hasAnyLogWithStatus(entry.getValue(), preparingStateName) &&
                                hasAnyLogWithStatus(entry.getValue(), readyStateName)
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return logsByOrderIdFiltered.values().stream().map(orderStatusLogs -> {
            OrderStatusLog preparingLog = findOrderStatusLogByState(orderStatusLogs, preparingStateName);
            OrderStatusLog readyLog = findOrderStatusLogByState(orderStatusLogs, readyStateName);
            Long orderId = preparingLog.getOrderId();

            Double finishedInSeconds = calculeDifferenceInSeconds(preparingLog.getUpdatedAt(), readyLog.getUpdatedAt());
            Long employeeId = preparingLog.getEmployeeId();

            return new OrderCompletion(orderId, preparingLog.getUpdatedAt(), readyLog.getUpdatedAt(),
                    finishedInSeconds, employeeId);
        }).toList();

    }

    private Double calculeDifferenceInSeconds(LocalDateTime startedAt, LocalDateTime finishedAt) {
        if (startedAt != null && finishedAt != null) {
            long differenceInSeconds = Duration.between(startedAt, finishedAt).getSeconds();
            return (double) differenceInSeconds;
        }

        return 0.0;
    }

    private OrderStatusLog findOrderStatusLogByState(List<OrderStatusLog> orderStatusLogs, String state){
        return orderStatusLogs.stream()
                .filter(orderStatusLog -> orderStatusLog.getStatus().equals(state))
                .findFirst()
                .orElse(null);
    }




}
