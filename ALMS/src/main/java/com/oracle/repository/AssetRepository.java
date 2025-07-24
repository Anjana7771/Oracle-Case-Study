package com.oracle.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.entities.Asset;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Integer> {

    @Query("SELECT COALESCE(SUM(a.assetValue), 0) FROM Asset a WHERE a.isRateSensitive = true")
    BigDecimal getTotalRateSensitiveAssets();
    @Query("SELECT COALESCE(AVG(a.assetValue), 0) FROM Asset a WHERE a.assetValue > 0")
    BigDecimal getAverageEarningAssets();
    @Query("SELECT COALESCE(SUM(a.assetValue), 0) FROM Asset a")
    BigDecimal getTotalAssetValue();
    @Query("SELECT a FROM Asset a")
    List<Asset> getAssets();

    @Query("SELECT YEAR(a.maturityDate),COALESCE(AVG(a.assetValue), 0) FROM Asset a WHERE a.assetValue > 0 GROUP BY YEAR(a.maturityDate)")
    BigDecimal getAverageEarningAssetsByYear();
    
    @Query(value = """
    		SELECT 
			  maturity_year,
			  (
			    SELECT AVG(asset_value)
			    FROM asset b
			    WHERE YEAR(b.maturity_date) >= maturity_year
			  ) AS cumulative_avg
			FROM (
			  SELECT DISTINCT YEAR(maturity_date) AS maturity_year
			  FROM asset
			  WHERE maturity_date IS NOT NULL
			) AS years
			ORDER BY maturity_year;
    		""",nativeQuery =true)
    Map<String,BigDecimal> getAverageActiveAssetsPerYear();
}
