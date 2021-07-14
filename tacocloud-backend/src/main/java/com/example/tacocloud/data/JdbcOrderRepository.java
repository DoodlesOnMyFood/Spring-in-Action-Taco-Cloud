//package com.example.tacocloud.data;
//
//import com.example.tacocloud.Order;
//import com.example.tacocloud.Taco;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class JdbcOrderRepository implements OrderRepository{
//    private SimpleJdbcInsert orderInserter;
//    private SimpleJdbcInsert orderTacoInserter;
//
//    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
//        orderInserter = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("Taco_order")
//                .usingGeneratedKeyColumns("id");
//        orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("Taco_Order_Tacos");
//    }
//
//    @Override
//    public Order save(Order order) {
//        order.setPlacedAt(new Date());
//        long orderId = saveOrderDetails(order);
//        order.setId(orderId);
//        List<Taco> tacos = order.getTacos();
//        tacos.stream().forEach(taco ->{
//            saveTacoToOrder(taco, orderId);
//        });
//
//        return order;
//    }
//
//
//    private long saveOrderDetails(Order order) {
//        @SuppressWarnings("unchecked")
//        Map<String, Object> values = new HashMap<>();
//
//        values.put("deliveryName", order.getName());
//        values.put("deliveryStreet", order.getStreet());
//        values.put("deliveryCity", order.getCity());
//        values.put("deliveryState", order.getState());
//        values.put("deliveryZip", order.getZip());
//        values.put("ccNumber", order.getCcNumber());
//        values.put("ccExpiration", order.getCcExpiration());
//        values.put("ccCvv", order.getCcCVV());
//        values.put("placedAt", order.getPlacedAt());
//
//        return orderInserter.executeAndReturnKey(values).longValue();
//    }
//
//    private void saveTacoToOrder(Taco taco, long orderId) {
//        Map<String, Object> values = new HashMap<>();
//        values.put("TacoOrder", orderId);
//        values.put("Taco", taco);
//        orderTacoInserter.execute(values);
//    }
//
//
//}
