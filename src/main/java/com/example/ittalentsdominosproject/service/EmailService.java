package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.dto.PizzaToCartDTO;
import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.model.entity.PizzaBread;
import com.example.ittalentsdominosproject.model.entity.PizzaSize;
import com.example.ittalentsdominosproject.repository.PizzaBreadRepository;
import com.example.ittalentsdominosproject.repository.PizzaRepository;
import com.example.ittalentsdominosproject.repository.PizzaSizeRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;
    @Autowired
    private PizzaBreadRepository pizzaBreadRepository;

    @SneakyThrows
    public void mailSender(String msg) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.socketFactory.port", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "true");

        String acc = "chavdargoranov00@gmail.com";
        String pass = "";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(acc, pass);
            }
        });

        Message message = prepareMsg(session, acc, msg);

        Transport.send(message);
    }

    @SneakyThrows
    private Message prepareMsg(Session session, String acc, String msg) {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(acc));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(acc));
        message.setSubject("Order");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        return message;
    }

    public String formatMessage(HashMap<OtherProduct, Integer> otherProductCart, HashMap<PizzaToCartDTO, Integer> pizzaCart) {
        StringBuilder text = new StringBuilder();
        text.append("Cart items").append("<br>");
        if (otherProductCart != null) {
            text.append("Products: ");
            for (Map.Entry<OtherProduct, Integer> o : otherProductCart.entrySet()) {
                text.append("<br>")
                        .append(o.getKey().getName() + " x " + o.getValue());

            }
        }
        text.append("<br>");
        if (pizzaCart != null) {
            text.append("Pizzas: ");
            for (Map.Entry<PizzaToCartDTO, Integer> p : pizzaCart.entrySet()) {
                Optional<Pizza> pizza = pizzaRepository.findById((long) p.getKey().getPizza_id());
                Optional<PizzaSize> pizzaSize = pizzaSizeRepository.findById((long) p.getKey().getPizzaSize_id());
                Optional<PizzaBread> pizzaBread = pizzaBreadRepository.findById((long) p.getKey().getPizzaBread_id());
                if (pizza.isEmpty() || pizzaSize.isEmpty() || pizzaBread.isEmpty()) {
                    throw new NotFoundException("Pizza not found");
                }
                text.append("<br>")
                        .append(pizza.get().getName() + " size " + pizzaSize.get().getSize() + " bread " + pizzaBread.get().getBread()+
                                " price "+pizza.get().getPrice()+pizzaBread.get().getPrice()+pizzaSize.get().getPrice());
            }
        }
        return text.toString();
    }
}
