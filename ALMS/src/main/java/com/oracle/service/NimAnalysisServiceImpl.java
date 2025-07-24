package com.oracle.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.entities.InterestIncomeExpense.EntryType;
import com.oracle.repository.AssetRepository;
import com.oracle.repository.InterestIncomeExpenseRepository;

@Service
public class NimAnalysisServiceImpl implements NimAnalysisService {

    @Autowired
    private InterestIncomeExpenseRepository interestRepo;

    @Autowired
    private AssetRepository assetRepo;

    @Override
    public BigDecimal calculateNIM(LocalDate startDate, LocalDate endDate) {
        BigDecimal income = interestRepo.getTotalByTypeAndPeriod(EntryType.INCOME, startDate, endDate);
        BigDecimal expense = interestRepo.getTotalByTypeAndPeriod(EntryType.EXPENSE, startDate, endDate);
        BigDecimal avgAssets = assetRepo.getAverageEarningAssets();

        if (avgAssets == null || avgAssets.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return income.subtract(expense).divide(avgAssets, 6, BigDecimal.ROUND_HALF_UP);
    }

	@Override
	public List<Object[]> computeNIM() {
		Map<String,BigDecimal> avgAssets = assetRepo.getAverageActiveAssetsPerYear();
		List<Object[]> fetchedInterestIncomeExpense = interestRepo.getAnnualIncomeExpenseSummary();
		
		List<Object[]> returnData =new ArrayList<>();
		
		fetchedInterestIncomeExpense
		.stream()
		.forEach((data)->{
			Object[] o = new Object[data.length+1];
			o[0]=data[0];
			o[1]=(BigDecimal)data[1];
			o[2]=(BigDecimal)data[2];
			o[3]= ((BigDecimal)o[1]).subtract((BigDecimal)o[2]).divide(avgAssets.get((String)o[0]));
			returnData.add(o);
		});
		return returnData;
	}
}
