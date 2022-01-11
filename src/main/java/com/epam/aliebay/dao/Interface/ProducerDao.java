package com.epam.aliebay.dao.Interface;

import com.epam.aliebay.entity.Producer;

import java.util.List;
import java.util.Optional;

public interface ProducerDao {

    Optional<Producer> getProducerById(int id);

    Optional<Producer> getProducerByName(String name);

    List<Producer> getAllProducers();

    void saveProducer(Producer producer);

    void updateProducer(Producer producer, int id);

    void deleteProducerById(int id);
}