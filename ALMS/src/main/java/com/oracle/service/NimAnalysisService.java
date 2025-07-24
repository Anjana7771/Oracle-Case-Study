package com.oracle.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface NimAnalysisService {
    BigDecimal calculateNIM(LocalDate startDate, LocalDate endDate);
    
    List<Object[]> computeNIM();
}
