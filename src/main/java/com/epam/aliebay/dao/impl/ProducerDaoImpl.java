package com.epam.aliebay.dao.impl;

import com.epam.aliebay.dao.*;
import com.epam.aliebay.dao.Interface.ProducerDao;
import com.epam.aliebay.entity.Producer;

import java.util.List;
import java.util.Optional;

public class ProducerDaoImpl extends AbstractBaseDao implements ProducerDao {
    private static final ResultSetHandler<Producer> producerResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.PRODUCER_RESULT_SET_HANDLER);
    private static final ResultSetHandler<List<Producer>> producersResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.PRODUCER_RESULT_SET_HANDLER);

    private static final String SELECT_ALL_PRODUCERS_QUERY = "SELECT id AS producer_id, name AS producer_name FROM producer ORDER BY id";
    private static final String SELECT_PRODUCER_BY_ID_QUERY = "SELECT id AS producer_id, name AS producer_name FROM producer WHERE id = ?";
    private static final String SELECT_PRODUCER_BY_NAME_QUERY = "SELECT id AS producer_id, name AS producer_name FROM " +
            "producer WHERE LOWER(name) = ?";
    private static final String INSERT_PRODUCER_QUERY = "INSERT INTO producer (name) VALUES (?)";
    private static final String DELETE_PRODUCER_BY_ID_QUERY = "DELETE FROM producer WHERE id = ?";
    private static final String UPDATE_PRODUCER_QUERY = "UPDATE producer SET name = ? WHERE id = ?";

    @Override
    public Optional<Producer> getProducerById(int id) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_PRODUCER_BY_ID_QUERY, producerResultSetHandler, id));
    }

    @Override
    public Optional<Producer> getProducerByName(String name) {
        return Optional.ofNullable(JdbcTemplate.select(SELECT_PRODUCER_BY_NAME_QUERY, producerResultSetHandler, name.toLowerCase()));
    }

    @Override
    public List<Producer> getAllProducers() {
        return JdbcTemplate.select(SELECT_ALL_PRODUCERS_QUERY, producersResultSetHandler);
    }

    @Override
    public void saveProducer(Producer producer) {
        JdbcTemplate.executeUpdate(INSERT_PRODUCER_QUERY, producer.getName());
    }

    @Override
    public void updateProducer(Producer producer, int id) {
        JdbcTemplate.executeUpdate(UPDATE_PRODUCER_QUERY, producer.getName(), id);

    }

    @Override
    public void deleteProducerById(int id) {
        JdbcTemplate.executeUpdate(DELETE_PRODUCER_BY_ID_QUERY, id);
    }
}
