package tn.esprit.spring.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entity.Response;
import tn.esprit.spring.repository.ResponseRepository;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class StripeService {
    @Value("${stripe.key.secret}")
    private String API_SECET_KEY;

    @Autowired
    ResponseRepository responseRepository;
    /*
        @Autowired
        private JavaMailSender mailSender;
      */
    @Autowired
    private JavaMailSenderImpl mailSender;

    public String createCharge(String email, String token, int amount) {

        String chargeId = null;

        try {
            Stripe.apiKey = API_SECET_KEY;
            Map<String, Object> chargeParams = new HashMap<>();
            Response r = new Response();
            chargeParams.put("description", "Charge for " + email);
            r.setEmail(email);

            chargeParams.put("currency", "usd");
            chargeParams.put("amount", amount);
            int a = amount / 100;
            r.setAmount(a);
            r.setDateReponses(new Date());
            chargeParams.put("source", token);
            r.setToken(token);

            Charge charge = Charge.create(chargeParams);
            chargeId = charge.getId();

            responseRepository.save(r);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("benhassinenassim98@gmail.com", "bicctandsdxnapjg");
                }
            });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("benhassinenassim98@gmail.com", false));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            msg.setSubject("Successful payment ! ");
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            int a = amount/100;
            messageBodyPart.setContent("You payed "+a +" $",
                    "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile("C:\\Users\\nassi\\Desktop\\Nassim\\src\\main\\resources\\invoice.pdf");
            multipart.addBodyPart(attachPart);
            msg.setContent(multipart);
            Transport.send(msg);
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }
        // Generating Pdf


        try {
            // creation of the document with a certain size and certain margins
            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
            // creating table and set the column width
            PdfPTable table = new PdfPTable(2);
            float widths[] = {3, 6};
            table.setWidths(widths);
            table.setHeaderRows(1);

            // add cell of table - header cell
            PdfPCell cell1 = new PdfPCell(new Phrase("Email"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(new BaseColor(250, 253, 250));
            cell1.setPadding(20);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Amount"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(new BaseColor(250, 253, 250));
            cell2.setPadding(20);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase(email));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setPadding(20);
            table.addCell(cell3);

            int a = amount/100;
            PdfPCell cell4 = new PdfPCell(new Phrase(a + " $"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setPadding(20);
            table.addCell(cell4);

                // write the all into a file and save it.
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\nassi\\Desktop\\Nassim\\src\\main\\resources\\invoice.pdf"));

            document.open();
            //Title
            Paragraph title = new Paragraph("Payment receipt");
            Date date = new Date(System.currentTimeMillis());
            Paragraph d = new Paragraph(date.toString());
            title.setAlignment(Element.ALIGN_CENTER);
            d.setAlignment(Element.ALIGN_CENTER);

            Chunk linebreak = new Chunk(new DottedLineSeparator());
            Paragraph contact = new Paragraph("Contact Us : ");
            contact.setAlignment(Element.ALIGN_CENTER);
            //Generating QrCode
            BarcodeQRCode barcodeQRCode = new BarcodeQRCode("benhassinenassim98@gmail.com", 1000, 1000, null);
            Image codeQrImage = barcodeQRCode.getImage();
            codeQrImage.scaleAbsolute(100, 100);
            codeQrImage.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(linebreak);
            document.add(d);
            document.add(linebreak);
            document.add(table);
            document.add(linebreak);
            document.add(contact);
            document.add(codeQrImage);
            document.add(linebreak);
            document.close();

            System.out.println("Pdf Generated successfully.");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //end Generating Pdf
        return chargeId;
    }

}
