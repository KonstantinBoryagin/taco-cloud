//package com.example.tacocloud.data;
//
//import com.example.tacocloud.tacos.Ingredient;
//import com.example.tacocloud.tacos.IngredientRef;
//import com.example.tacocloud.tacos.Taco;
//import com.example.tacocloud.tacos.TacoOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Types;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//@Repository
//public class JdbcOrderRepository implements OrderRepository {
//
//    private JdbcOperations jdbcOperations;
//
//    @Autowired
//    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
//        this.jdbcOperations = jdbcOperations;
//    }
//
//    @Override
//    @Transactional
//    public TacoOrder save(TacoOrder order) {
//        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
//                "INSERT INTO Taco_Order " +
//                        "(delivery_Name, delivery_Street, delivery_City,delivery_State, delivery_Zip, cc_number," +
//                        "cc_expiration, cc_cvv, placed_at)" +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
//                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                Types.TIMESTAMP
//        );
//        pscf.setReturnGeneratedKeys(true);
//
//        order.setPlacedAt(new Date());
//        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
//                order.getDeliveryName(),
//                order.getDeliveryStreet(),
//                order.getDeliveryCity(),
//                order.getDeliveryState(),
//                order.getDeliveryZip(),
//                order.getCcNumber(),
//                order.getCcExpiration(),
//                order.getCcCVV(),
//                order.getPlacedAt()
//        ));
//
//        GeneratedKeyHolder holder = new GeneratedKeyHolder();
//        jdbcOperations.update(psc, holder);
//        long orderId = holder.getKey().longValue();
//        order.setId(orderId);
//
//        List<Taco> tacos = order.getTacos();
//        int i = 0;
//        for(Taco taco : tacos) {
//            saveTaco(orderId, i++, taco);
//        }
//
//        return order;
//    }
//
//    private long saveTaco(Long orderId, int orderKey, Taco taco) {
//        taco.setCreateAt(new Date());
//        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
//            "INSERT INTO Taco (name, created_at, taco_order, taco_order_key) " +
//                    "VALUES(?, ?, ?, ?)",
//                Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT
//        );
//
//        pscf.setReturnGeneratedKeys(true);
//
//        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
//                taco.getName(),
//                taco.getCreateAt(),
//                orderId,
//                orderKey
//        ));
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcOperations.update(psc, keyHolder);
//
//        long tacoId = keyHolder.getKey().longValue();
//        taco.setId(tacoId);
//
//        saveIngredientsRef(tacoId, taco.getIngredients());
//        return tacoId;
//    }
//
//    private void saveIngredientsRef(long tacoId, List<IngredientRef> ingredientRefs) {
//        int key = 0;
//        for(IngredientRef ingredientRef : ingredientRefs) {
//            jdbcOperations.update("INSERT INTO Ingredient_Ref (ingredient, taco, taco_key) " +
//                    "VALUES (?, ?, ?)",
//                    ingredientRef.getIngredient(), tacoId, key++);
//        }
//    }
//}
