package com.application.piunivesp.util;

public class MailTemplate {

    public static final String SUBJECT_RESET_PASSWORD = "Recuperação de conta.";

    public static final String TEMPLATE_RESET_PASSWORD = "<!DOCTYPE html>"
            + "<html>"
            + "<style>"
            + "table, th, td {"
            + "  border:0.5px solid black;"
            + "}"
            + ".center {"
            + "  margin-left: auto;"
            + "  margin-right: auto;"
            + "}"
            + "</style>"
            + "<body>"
            + ""
            + "<h2>Olá, [USERNAME]</h2>"
            + "<h4>Recebemos uma solicitação para redefinir sua senha do sistema.</h4>"
            + "<h4>Acesse o link abaixo a fim de redefinir sua senha: </h4>"
            + "<a href=\"[LINK]\">Alterar senha</a>"
            + "<h4>Obrigado!</h4>"
            + ""
            + "</body>"
            + "</html>";
}
