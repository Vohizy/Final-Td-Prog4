package com.example.prog4.service;

import com.example.prog4.config.CompanyConf;
import com.example.prog4.model.Employee;
import com.example.prog4.model.exception.InternalServerErrorException;
import com.lowagie.text.DocumentException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

public class PDFUtils {
    private static String parseThymeleafTemplate(Employee employee) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/pdf/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("employee", employee);
        context.setVariable("company", new CompanyConf());

        return templateEngine.process("employee_pdf", context);
    }

    public static byte[]  pdfFromHtml(Employee employee) {
        String html = parseThymeleafTemplate(employee);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer render = new ITextRenderer();
        render.setDocumentFromString(html);
        render.layout();

        try {
            render.createPDF(outputStream);
        } catch (DocumentException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return outputStream.toByteArray();
    }
}
