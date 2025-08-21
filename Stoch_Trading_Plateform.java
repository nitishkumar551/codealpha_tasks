import java.util.*;

// --- Stock class ---
class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    // Simulate price fluctuation
    void updatePrice() {
        double change = (Math.random() - 0.5) * 5; // random +/- 2.5
        price = Math.max(1, price + change); // avoid negative prices
    }

    @Override
    public String toString() {
        return symbol + " (" + name + ") : $" + String.format("%.2f", price);
    }
}

// --- Transaction class ---
class Transaction {
    String type; // BUY or SELL
    String stockSymbol;
    int quantity;
    double price;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return type + " " + quantity + " shares of " + stockSymbol + " @ $" + String.format("%.2f", price);
    }
}

// --- Portfolio class ---
class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();
    double balance;

    Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    void buyStock(Stock stock, int qty) {
        double cost = stock.price * qty;
        if (cost <= balance) {
            balance -= cost;
            holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + qty);
            transactions.add(new Transaction("BUY", stock.symbol, qty, stock.price));
            System.out.println("✅ Bought " + qty + " shares of " + stock.symbol);
        } else {
            System.out.println("❌ Not enough balance!");
        }
    }

    void sellStock(Stock stock, int qty) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (qty <= owned) {
            double revenue = stock.price * qty;
            balance += revenue;
            holdings.put(stock.symbol, owned - qty);
            transactions.add(new Transaction("SELL", stock.symbol, qty, stock.price));
            System.out.println("✅ Sold " + qty + " shares of " + stock.symbol);
        } else {
            System.out.println("❌ Not enough shares to sell!");
        }
    }

    void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio ---");
        System.out.println("Balance: $" + String.format("%.2f", balance));
        double totalValue = balance;
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            Stock stock = market.get(symbol);
            if (stock != null) {
                double value = qty * stock.price;
                System.out.println(symbol + " : " + qty + " shares (Value: $" + String.format("%.2f", value) + ")");
                totalValue += value;
            }
        }
        System.out.println("Total Portfolio Value: $" + String.format("%.2f", totalValue));
    }

    void showTransactions() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}

// --- StockMarket class ---
class StockMarket {
    Map<String, Stock> stocks = new HashMap<>();

    StockMarket() {
        stocks.put("AAPL", new Stock("AAPL", "Apple Inc.", 150));
        stocks.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 2800));
        stocks.put("TSLA", new Stock("TSLA", "Tesla Motors", 700));
        stocks.put("AMZN", new Stock("AMZN", "Amazon", 3300));
    }

    void updatePrices() {
        for (Stock s : stocks.values()) {
            s.updatePrice();
        }
    }

    void showMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock s : stocks.values()) {
            System.out.println(s);
        }
    }

    Stock getStock(String symbol) {
        return stocks.get(symbol);
    }
}

// --- Main Simulator ---
public class StockTradingSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StockMarket market = new StockMarket();
        Portfolio portfolio = new Portfolio(10000); // initial balance

        while (true) {
            market.updatePrices(); // simulate price updates
            System.out.println("\n===== STOCK TRADING SIMULATOR =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    market.showMarket();
                    break;
                case 2:
                    market.showMarket();
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        portfolio.buyStock(buyStock, buyQty);
                    } else {
                        System.out.println("❌ Invalid stock symbol!");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        portfolio.sellStock(sellStock, sellQty);
                    } else {
                        System.out.println("❌ Invalid stock symbol!");
                    }
                    break;
                case 4:
                    portfolio.showPortfolio(market.stocks);
                    break;
                case 5:
                    portfolio.showTransactions();
                    break;
                case 6:
                    System.out.println("🚪 Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("❌ Invalid option!");
            }
        }
    }
}
