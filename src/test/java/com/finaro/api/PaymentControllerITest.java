package com.finaro.api;

import com.finaro.config.AbstractIntegrationTest;
import com.finaro.utility.UtilityTestService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.finaro.utility.TestConstants.HEADER_ATTRIBUTE_ORIGIN_VALUE;
import static com.finaro.utility.TestConstants.HEADER_ATTRIBUTE_X_TRACE_ID;
import static com.finaro.utility.TestConstants.X_TRACE_ID_VALUE;
import static com.finaro.utility.UtilityTestService.getResourceResponseJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerITest extends AbstractIntegrationTest {
    private static final String REQUEST_PATH = "/transactions";
    private static final String INVOICE_PATH = "/{invoice}";

    @Autowired
    private MockMvc mockMvc;

    @AfterAll
    public static void cleanUp() throws IOException {
        removeAuditFile();
    }

    @SneakyThrows
    @Test
    void submitPayment_shouldReturnApproveResponse_whenPaymentIsSuccess() {
        var byteContent = UtilityTestService.readResourceFile("paymentRequest.json");

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(REQUEST_PATH)
                                .content(byteContent)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(HttpHeaders.ORIGIN, HEADER_ATTRIBUTE_ORIGIN_VALUE)
                                .header(HEADER_ATTRIBUTE_X_TRACE_ID, X_TRACE_ID_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().string(HEADER_ATTRIBUTE_X_TRACE_ID, X_TRACE_ID_VALUE))
                .andExpect(content().json(getResourceResponseJson("paymentApproved.json"), true));
    }

    @SneakyThrows
    @Test
    void submitPayment_shouldReturnUnApproveResponse_whenPaymentIsNotValid() {
        var byteContent = UtilityTestService.readResourceFile("invalidPaymentRequest.json");

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post(REQUEST_PATH)
                                .content(byteContent)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(HttpHeaders.ORIGIN, HEADER_ATTRIBUTE_ORIGIN_VALUE)
                                .header(HEADER_ATTRIBUTE_X_TRACE_ID, X_TRACE_ID_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HEADER_ATTRIBUTE_X_TRACE_ID, X_TRACE_ID_VALUE))
                .andExpect(content().json(getResourceResponseJson("paymentUnapprove.json"), true));
    }

    @SneakyThrows
    @Test
    void retrieveTransaction_shouldReturnTransaction_whenInvoiceExistsInDb() {
        var invoiceId = 1L;
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get(REQUEST_PATH + INVOICE_PATH, invoiceId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(getResourceResponseJson("transaction.json"), true));
    }

    @SneakyThrows
    @Test
    void retrievesTransaction_shouldReturnErrorNotFoundResponse_whenInvoiceIsNotExistInDb() {
        var invoiceId = 2L;
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get(REQUEST_PATH + INVOICE_PATH, invoiceId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().json(getResourceResponseJson("errorNotFoundResponse.json"), true));
    }

    private static void removeAuditFile() throws IOException {
        var auditFile = Paths.get("src/main/resources/audit/audit.log");
        Files.deleteIfExists(auditFile);
    }
}