// These represent external systems tightly coupled.
class BankAPI {
    public void chargeCreditCard(double amount) {
        System.out.println("[BankAPI] Connecting to bank...");
        System.out.println("[BankAPI] Charged $" + amount + " via Credit Card.");
    }
}

class DuitNowAPI {
    public void sendPayment(double amount) {
        System.out.println("[DuitNowAPI] Connecting to DuitNow Gateway...");
        System.out.println("[DuitNowAPI] Charged $" + amount + " via DuitNow QR.");
    }
}

// Main Application
public class SimplePaymentApp {

    // This method is HARD to test because it creates 'new BankAPI()' inside.
    public void processOrder(String paymentType, double amount) {
        System.out.println("\nProcessing order for: " + paymentType);

        if (paymentType.equalsIgnoreCase("CREDIT_CARD")) {
            // tight coupling which has direct dependency on BankAPI
            BankAPI bank = new BankAPI();
            bank.chargeCreditCard(amount);

        } else if (paymentType.equalsIgnoreCase("DUITNOW")) {
            // tight coupling which has direct dependency on duitnowapi
            DuitNowAPI duitnow = new DuitNowAPI();
            duitnow.sendPayment(amount);

        } else {
            System.out.println("Error: Payment method not supported.");
        }
    }

    public static void main(String[] args) {
        SimplePaymentApp app = new SimplePaymentApp();
        app.processOrder("CREDIT_CARD", 100.50);
        app.processOrder("DUITNOW", 50.00);
        app.processOrder("BITCOIN", 999.99); // Error case
    }
}