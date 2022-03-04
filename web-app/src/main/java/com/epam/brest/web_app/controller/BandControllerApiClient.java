package com.epam.brest.web_app.controller;

import com.epam.brest.ApiException;
import com.epam.brest.model.Band;
import com.epam.brest.web_app.condition.ApiClientCondition;
import com.epam.brest.web_app.validator.BandValidator;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.BandsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * MVC controller using ApiClient automatically generated by the Swagger Codegen.
 */
@Controller
@Conditional(ApiClientCondition.class)

public class BandControllerApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandControllerApiClient.class);

    private final BandValidator bandValidator;

    private final BandsApi bandsApi;
    private final BandApi bandApi;

    public BandControllerApiClient(BandValidator bandValidator, BandsApi bandsApi, BandApi bandApi) {
        this.bandValidator = bandValidator;
        this.bandsApi = bandsApi;
        this.bandApi = bandApi;
    }

    @GetMapping(value = "/bands")
    public String bands(Model model) {
        LOGGER.debug("go to page bands");
        try {
            model.addAttribute("bands", bandsApi.bandsDto());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "bands";
    }

    @GetMapping(value = "/band")
    public String gotoAddBandPage(Model model) {
        LOGGER.debug("gotoAddBandPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("band", new Band());
        return "band";
    }

    @GetMapping(value = "/band/{id}")
    public final String gotoEditBandPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditBandPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        try {
            model.addAttribute("band", bandApi.getBandById(id));
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "band";
    }

    @PostMapping(value = "/band")
    public String addBand(Band band, BindingResult result) {
        LOGGER.debug("addBand({})", band);
        bandValidator.validate(band, result);
        if (result.hasErrors()) {
            return "band";
        }
        try {
            bandApi.createBand(band);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/bands";
    }

    @PostMapping(value = "/band/{id}")
    public String updateBand(Band band, BindingResult result) {
        LOGGER.debug("updateBand({})", band);
        bandValidator.validate(band, result);
        if (result.hasErrors()) {
            return "band";
        }
        try {
            bandApi.updateBand(band);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/bands";
    }

    @GetMapping(value = "/band/{id}/delete")
    public final String deleteBandById(@PathVariable Integer id, Model model) {
        LOGGER.debug("delete({},{})", id, model);
        try {
            bandApi.deleteBand(id);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/bands";
    }
}