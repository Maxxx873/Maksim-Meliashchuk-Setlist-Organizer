package com.epam.brest.dao;

import com.epam.brest.model.Band;

import java.util.List;

public interface BandDao {

    List<Band> findAll();

    Integer create(Band band);

    Integer update(Band band);

    Integer delete(Integer bandId);
}