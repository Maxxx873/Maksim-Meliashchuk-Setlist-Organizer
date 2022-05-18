package com.epam.brest.web_app.controller;

import com.epam.brest.ApiException;
import com.epam.brest.model.Band;
import com.epam.brest.model.BandDto;
import com.epam.brest.web_app.condition.ApiClientCondition;
import com.epam.brest.web_app.excel.BandsViewExportExcel;
import com.epam.brest.web_app.validator.BandValidator;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.BandsApi;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static com.epam.brest.web_app.security.AccessTokenValueExtractor.getAccessTokenValue;

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
    private List<BandDto> bandDtoList;

    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public BandControllerApiClient(BandValidator bandValidator, BandsApi bandsApi, BandApi bandApi, OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        this.bandValidator = bandValidator;
        this.bandsApi = bandsApi;
        this.bandApi = bandApi;
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }

    @GetMapping(value = "/bands")
    @PreAuthorize("hasRole('user')")
    public String bands(Model model) {
        LOGGER.debug("go to page bands");
        try {
            if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
                bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
            } else {
                return "login";
            }
            bandDtoList = bandsApi.bandsDto();
            model.addAttribute("bands", bandDtoList);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "bands";
    }

    @GetMapping(value = "/bands/fill")
    public String fillFakeBands(@RequestParam(value = "size", required = false)
                                   Integer size,
                                @RequestParam(value = "language", required = false)
                                    String language,
                                Model model) {
        LOGGER.debug("fillFakeBands({},{})", size, language);
        try {
            if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
                bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
            } else {
                return "login";
            }
            bandDtoList = bandsApi.fillBandsDtoFake(size,language);
            model.addAttribute("bands", bandDtoList);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("size", size);
        model.addAttribute("language", language);
        return "bands";
    }


    @GetMapping(value = "/band")
    public String gotoAddBandPage(Model model) {
        LOGGER.debug("gotoAddBandPage({})", model);
        if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
            bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
        } else {
            return "login";
        }
        model.addAttribute("isNew", true);
        model.addAttribute("band", new Band());
        return "band";
    }

    @GetMapping(value = "/band/{id}")
    public final String gotoEditBandPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditBandPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        try {
            if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
                bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
            } else {
                return "login";
            }
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
            if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
                bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
            } else {
                return "login";
            }
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
            if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
                bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
            } else {
                return "login";
            }
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
            if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
                bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
            } else {
                return "login";
            }
            bandApi.deleteBand(id);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "redirect:/bands";
    }

    @GetMapping(value = "/bands/export/excel")
    public ModelAndView exportToExcel() {
        LOGGER.debug("exportToExcel()");
        ModelAndView mav = new ModelAndView();
        mav.setView(new BandsViewExportExcel());
        mav.addObject("bands", bandDtoList);
        return mav;
    }

    @GetMapping(value = "/band/export/excel")
    public void exportFromBandTableToExcel(HttpServletResponse response) throws ApiException, IOException {
        LOGGER.debug("exportFromBandTableToExcel()");
        InputStream is = new FileInputStream(bandApi.exportToExcelAllBands());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Band.xlsx");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping(value = "/band/import/excel")
    public final String importInBandTableFromExcel(@RequestParam("uploadfile") final MultipartFile file) throws ApiException, IOException {
        LOGGER.debug("importInBandTableFromExcel()");
        File convertFile = new File ("Band.xlsx");
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        if (getAccessTokenValue(auth2AuthorizedClientService) != null) {
            bandsApi.getApiClient().setAccessToken(getAccessTokenValue(auth2AuthorizedClientService));
        } else {
            return "login";
        }
        bandApi.importBandFromExcel(convertFile);
        convertFile.delete();
        return "redirect:/bands";
    }

    @GetMapping(value = "/band/export/xml")
    public void exportFromBandTableToXml(HttpServletResponse response) throws ApiException, IOException {
        LOGGER.debug("exportFromBandTableToXml()");
        InputStream is = new FileInputStream(bandApi.exportToXmlAllBands());
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=Bands.xml");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }

}
