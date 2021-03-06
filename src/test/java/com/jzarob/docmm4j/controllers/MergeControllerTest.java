package com.jzarob.docmm4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzarob.docmm4j.models.MergeTemplateRequest;
import com.jzarob.docmm4j.services.MergeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MergeControllerTest {


    @Mock
    private MergeService mergeService;

    @InjectMocks
    private MergeController mergeController;

    @Test
    public void mergeDocumentRequestReturnsMergedDocument() throws Exception {

        ByteArrayResource resource = new ByteArrayResource(new byte[] { 0x15 });

        when(mergeService.mergeDocument(any(), any())).thenReturn(resource);

        MergeTemplateRequest templateRequest = new MergeTemplateRequest();
        templateRequest.setDocumentNumber("12345");

        ObjectMapper objectMapper = new ObjectMapper();

        templateRequest.setMergeData(objectMapper.readTree("{\"sampleNode\": \"test\"}"));

        ResponseEntity<?> responseEntity = mergeController.mergeTemplate(templateRequest);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(MediaType.APPLICATION_PDF, responseEntity.getHeaders().getContentType());
    }
}
