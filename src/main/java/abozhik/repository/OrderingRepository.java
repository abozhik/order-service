package abozhik.repository;

import abozhik.DbConnection;
import abozhik.model.Ordering;
import abozhik.model.OrderingItem;
import abozhik.util.JdbcUtils;
import lombok.SneakyThrows;

import java.sql.*;

public class OrderingRepository {

    private final OrderingItemsRepository orderingItemsRepository = new OrderingItemsRepository();

    public Long saveOrdering(Ordering ordering) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ordering (user_name) VALUES (?) RETURNING id");
            ps.setString(1, ordering.getUserName());
            Long orderingId = JdbcUtils.getInsertedId(ps);

            for (OrderingItem orderingItem : ordering.getOrderingItemList()) {
                orderingItem.setOrderingId(orderingId);
                orderingItemsRepository.saveOrderingItem(orderingItem);
            }

            connection.commit();
            return orderingId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
