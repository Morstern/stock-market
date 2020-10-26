package pl.kazet.stockmarket.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.zielinski.kamil.stockmarket.stock.StockEntity;
import pl.zielinski.kamil.stockmarket.stock.StockRepository;
import pl.zielinski.kamil.stockmarket.stockprice.StockPriceEntity;
import pl.zielinski.kamil.stockmarket.stockprice.StockPriceRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockFetcher {

    private StockRepository stockRepository;
    private StockPriceRepository stockPriceRepository;

    @Autowired
    public StockFetcher(StockRepository stockRepository, StockPriceRepository stockPriceRepository) {
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
    }


    @Scheduled(cron = "0 0 10,11,12,13,14,15 * * MON-FRI")
    public void fetchStockData() {
        List<StockEntity> stockList = stockRepository.findAll();
        if (stockList.size() > 0) {

            String concatISIN = getConcatISIN(stockList);

            Process p;
            try {
                ProcessBuilder pb = new ProcessBuilder("python", "script.py", concatISIN);
                pb.directory(getCurrentDirectory());
                p = pb.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String text = in.readLine();
                String preprocessedText = preprocessData(text);
                List<String> splitKeyValuesList = getAllSplitKeyValues(preprocessedText);


                for (String splitKeyValue : splitKeyValuesList) {
                    List<String> ISINBuyoutSellout = getISINBuyoutSellout(splitKeyValue);

                    StockEntity stock = getStockEntity(ISINBuyoutSellout.get(0));
                    BigDecimal buyout = getBuyoutValue((ISINBuyoutSellout.get(1)));
                    BigDecimal sellout = getSelloutValue((ISINBuyoutSellout.get(2)));

                    StockPriceEntity stockPriceEntity = StockPriceEntity.builder().
                            stock(stock).
                            buyout(buyout).
                            sellout(sellout).
                            date(LocalDateTime.now())
                            .build();

                    stockPriceRepository.save(stockPriceEntity);
                }
                p.destroy();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getConcatISIN(List<StockEntity> stockList) {
        return stockList.stream().map(stock -> stock.getISIN()).collect(Collectors.joining(","));
    }

    private File getCurrentDirectory() throws IOException {
        return new File(new File(".").getCanonicalPath() + "\\src\\main\\java\\pl\\kazet\\stockmarket\\datafetcher");
    }

    private String preprocessData(String data) {
        return data.replaceAll("[{} ',]", "");
    }

    private List<String> getAllSplitKeyValues(String text) {
        return Arrays.asList(text.split(";"));
    }

    private List<String> getISINBuyoutSellout(String keyValue) {
        List<String> splitKeyValue = Arrays.asList(keyValue.split(":"));
        List<String> ISINBuyoutSelloutList = new ArrayList<>(Arrays.asList(splitKeyValue.get(0), splitKeyValue.get(1), splitKeyValue.get(2)));
        return ISINBuyoutSelloutList;
    }

    private StockEntity getStockEntity(String ISIN) {
        return stockRepository.findById(ISIN).get();
    }

    private BigDecimal getBuyoutValue(String buyout) {
        return new BigDecimal(Double.parseDouble(buyout));
    }

    private BigDecimal getSelloutValue(String sellout) {
        return new BigDecimal(Double.parseDouble(sellout));
    }


}
