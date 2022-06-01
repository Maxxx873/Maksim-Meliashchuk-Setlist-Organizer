package com.epam.brest.rest;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.excel.TrackExportExcelService;
import com.epam.brest.service.excel.TrackImportExcelService;
import com.epam.brest.service.faker.TrackFakerService;
import com.epam.brest.service.xml.TrackXmlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

/**
 * REST controller.
 */
@Tag(name = "Track", description = "the Track API")
@RestController
@CrossOrigin
@SecurityRequirement(name = "keycloakOAuth")
public class TrackController {

    private final TrackService trackService;
    private final TrackFakerService trackFakerService;
    private final TrackExportExcelService trackExportExcelService;
    private final TrackImportExcelService trackImportExcelService;
    private final TrackXmlService trackXmlService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService, TrackFakerService trackFakerService,
                           TrackExportExcelService trackExportExcelService, TrackImportExcelService trackImportExcelService,
                           TrackXmlService trackXmlService) {
        this.trackService = trackService;
        this.trackFakerService = trackFakerService;
        this.trackExportExcelService = trackExportExcelService;
        this.trackImportExcelService = trackImportExcelService;
        this.trackXmlService = trackXmlService;
    }

    @Operation(summary = "Get information for all tracks based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Track.class))) })
    })
    @GetMapping(value = "/repertoire")
    public final Collection<Track> tracks() {
        logger.debug("tracks()");
        return trackService.findAllTracks();
    }

    @Operation(summary = "Fill information for fake tracks based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of fake tracks",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Track.class))) })
    })
    @GetMapping(value = "/repertoire/fill")
    public final Collection<Track> tracksFake(@RequestParam(defaultValue = "1", value = "size", required = false)
                                                          Integer size,
                                              @RequestParam(defaultValue = "EN", value = "language", required = false)
                                                          String language) {
        logger.debug("tracksFake()");
        return trackFakerService.fillFakeTracks(size, language);
    }

    @Operation(summary = "Get information for a single track identified by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A track",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Track.class)) }),
            @ApiResponse(responseCode = "404", description = "Trying to get a non-existent track",
                    content = @Content)})
    @GetMapping(value = "/repertoire/{id}")
    public final Track getTrackById(@PathVariable Integer id) {
        logger.debug("getTrackById()");
        return trackService.getTrackById(id);
    }

    @Operation(summary = "Create a new track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track have been created. Returns the ID of the new track",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "An attempt to create track with invalid fields",
                    content = @Content)})
    @PostMapping(path = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createTrack(@Valid @RequestBody Track track) {
        logger.debug("createTrack({})", track);
        Integer id = trackService.create(track);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Update a track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been updated. Returns the number of tracks affected",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "Trying to update track with invalid fields",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Trying to update a non-existent track",
                    content = @Content) })
    @PutMapping(value = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTrack(@Valid @RequestBody Track track) {
        logger.debug("updateTrack({})", track);
        int result = trackService.update(track);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Delete a track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been removed. Returns the number of tracks affected",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "404", description = "Trying to delete a non-existent track",
                    content = @Content)})
    @DeleteMapping(value = "/repertoire/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteTrack(@PathVariable Integer id) {
        logger.debug("delete({})", id);
        int result = trackService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Export information for all tracks based on their IDs to Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to Excel",
                    content = { @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary"))})
    })
    @GetMapping(value = "/repertoire/export/excel")
    public final void exportToExcelAllTracks(HttpServletResponse response) {
        logger.debug("exportToExcelAllTracks()");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Tracks.xlsx";
        response.setHeader(headerKey, headerValue);
        trackExportExcelService.exportTracksExcel(response);
    }

    @Operation(summary = "Import information in the table 'Track' from Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been imported. Returns the number of tracks imported.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) })})
    @PostMapping(value = "/repertoire/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<Integer> importTrackFromExcel(@RequestParam(value ="file") final MultipartFile files) throws IOException {
        logger.debug("importTrackFromExcel({})", files.getName());
        int result =  trackImportExcelService.importTrackExcel(files).size();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Export information for all tracks based on their IDs to XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to XML",
                    content = { @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary")) }),
    })
    @GetMapping(value = "/repertoire/export/xml")
    public final void exportToXmlAlTracks(HttpServletResponse response) throws IOException {
        logger.debug("exportToXmlAlTracks()");
        response.setContentType("application/xml");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Tracks.xml";
        response.setHeader(headerKey, headerValue);
        trackXmlService.exportTracksXml(response);
    }

}
