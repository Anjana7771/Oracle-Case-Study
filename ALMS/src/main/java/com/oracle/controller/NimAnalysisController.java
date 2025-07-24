package com.oracle.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Net Interest Margin Resource Operation")
public interface NimAnalysisController {

    @Operation(description = "Calculate NIM between two dates")
    @ApiResponses(value = {
        @ApiResponse(description = "NIM calculated successfully", responseCode = "200"),
        @ApiResponse(description = "Failed to calculate NIM", responseCode = "400")
    })
    public ResponseEntity<?> getNim(LocalDate startDate, LocalDate endDate);
    public ResponseEntity<?> getNimByYear();
}
