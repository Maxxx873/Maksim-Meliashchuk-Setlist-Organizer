package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TrackRepository extends CrudRepository<TrackEntity, Integer> {

    List<TrackEntity> findByBand_BandIdEquals(Integer bandId);

    List<TrackEntity> findByTrackReleaseDateBetween(LocalDate trackReleaseDateStart, LocalDate trackReleaseDateEnd);

    List<TrackEntity> findByTrackReleaseDateBefore(LocalDate trackReleaseDate);

    List<TrackEntity> findByTrackReleaseDateAfter(LocalDate trackReleaseDate);

    @Modifying
    @Transactional
    Integer deleteByTrackId(Integer trackId);

}