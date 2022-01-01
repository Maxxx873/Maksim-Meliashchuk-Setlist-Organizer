package com.epam.brest.dao;

import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class BandDaoJdbcImplTest {
    private final Logger logger = LogManager.getLogger(BandDaoJdbcImplTest.class);

    @InjectMocks
    private BandDaoJdbcImpl bandDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Band>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @Captor
    private ArgumentCaptor<KeyHolder> captorKeyHolder;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void testFindAllBands() {
        logger.debug("Execute mock test: testFindAllBands()");
        String sql = "select";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlAllBands", sql);
        Band band = new Band();
        List<Band> list = Collections.singletonList(band);

        Mockito.when(namedParameterJdbcTemplate.query(any(),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(list);

        List<Band> result = bandDaoJdbc.findAll();

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());

        RowMapper<Band> mapper = captorMapper.getValue();
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertSame(band, result.get(0));
    }

    @Test
    public void testGetBandById() {
        logger.debug("Execute mock test: getBandById()");
        String sql = "get";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlBandById", sql);
        int id = 0;
        Band band = new Band();

        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(band);

        Band result = bandDaoJdbc.getBandById(id);

        Mockito.verify(namedParameterJdbcTemplate)
                .queryForObject(eq(sql), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Band> mapper = captorMapper.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertSame(band, result);
    }

    @Test
    public void testUpdateBand() {
        logger.debug("Execute mock test: testUpdateBand()");
        String sql = "update";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlUpdateBandById", sql);
        int id = 0;
        Band band = new Band("Gods Tower", "Band of metal").setBandId(id);

        Mockito.when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.<SqlParameterSource>any()))
                .thenReturn(id);

        int resultId = bandDaoJdbc.update(band);

        Mockito.verify(namedParameterJdbcTemplate)
                .update(eq(sql), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(resultId);
        Assertions.assertSame(band.getBandId(), resultId);

    }


}
