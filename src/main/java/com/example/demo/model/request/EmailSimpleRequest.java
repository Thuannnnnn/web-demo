package com.example.demo.model.request;

import javax.validation.constraints.NotBlank;

public class EmailSimpleRequest {
    @NotBlank(message = "Nhập thông tin vào trường `người nhận`")
    private String recipient;
    @NotBlank(message = "Nhập thông tin vào trường `nội dung`")
    private String messageBody;
    @NotBlank(message = "Nhập thông tin vào trường `subject`")
    private String subject;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
