package com.oracle.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class NimSummaryDTO {
	@NotNull
	@Schema(description = "Year", example = "2025")
    private BigInteger year;
	
	@Schema(description = "Average Income for the Year", example = "150.00")
    private BigDecimal income;
	
	@Schema(description = "Average Expense for the Year", example = "150.00")
    private BigDecimal expense;

	@Schema(description = "NIM for the Year", example = "150.00")
    private BigDecimal nim;

	public NimSummaryDTO() {}
	
	public NimSummaryDTO(BigInteger year, BigDecimal income, BigDecimal expense, BigDecimal nim) {
		this.year=year;
		this.income=income;
		this.expense=expense;
		this.nim=nim;
	}
}
