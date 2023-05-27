import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PaymentCircuitBreaker {
  PaymentService service = new PaymentService();

  @CircuitBreaker(name = "updateOrder", fallbackMethod = "paymentAuthorizedPendingIntegration")
  public void paymentConfirm() throws IOException, InterruptedException {

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://example.com/payment-confirm"))
      .build();

    HttpResponse<String> response = client.send(request,
      HttpResponse.BodyHandlers.ofString());

    service.paymentConfirm(response);


  }

  public void paymentAuthorizedPendingIntegration(Long id, Exception e) {
    service.updateStatus();
  }
}
