// ==========================================
// PATTERN 1: STRATEGY PATTERN
// ==========================================

// 1. The Strategy Interface
//    Testability Benefit: This allows us to use "Mock" strategies during testing.
interface PaymentStrategy {
    void pay(double amount);
}

// 2. Concrete Strategy: Credit Card
class CreditCardStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // In a real app, this would connect to Visa/Mastercard
        System.out.println("[Strategy] Processing Credit Card charge of $" + amount);
    }
}

// 3. Concrete Strategy: PayPal
class PayPalStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // In a real app, this would connect to PayPal API
        System.out.println("[Strategy] Redirecting to PayPal for $" + amount);
    }
}

// ==========================================
// PATTERN 2: FACTORY PATTERN
// ==========================================

// 4. The Factory Class
//    Testability Benefit: Handles the COMPLEXITY of creating the right object.
//    If logic changes (e.g., adding "Bitcoin"), we only fix it here, not in the tests.
class PaymentFactory {
    public static PaymentStrategy getPaymentMethod(String type) {
        if (type.equalsIgnoreCase("CREDIT_CARD")) {
            return new CreditCardStrategy();
        } else if (type.equalsIgnoreCase("PAYPAL")) {
            return new PayPalStrategy();
        } else {
            throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }
}

// ==========================================
// THE REFINED CONTEXT (Main App)
// ==========================================

public class RefinedPaymentApp {

    // This method is now VERY easy to test.
    // It is "Decoupled" from the external Bank APIs.
    public void processOrder(String paymentType, double amount) {
        System.out.println("\n--- Processing Refined Order ---");

        try {
            // Step 1: Use FACTORY to get the correct strategy
            // We don't use 'new' or 'if-else' logic here.
            PaymentStrategy strategy = PaymentFactory.getPaymentMethod(paymentType);

            // Step 2: Use STRATEGY to pay
            strategy.pay(amount);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RefinedPaymentApp app = new RefinedPaymentApp();

        // Testing the Refined Solution
        app.processOrder("CREDIT_CARD", 120.00);
        app.processOrder("PAYPAL", 45.50);

        // Testing Error Handling (Safe to test now!)
        app.processOrder("CASH", 10.00);
    }
}